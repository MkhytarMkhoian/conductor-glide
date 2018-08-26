package com.mkhytarmkhoian.conductor.glide;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bluelinelabs.conductor.Controller;
import com.bluelinelabs.conductor.ControllerChangeHandler;
import com.bluelinelabs.conductor.ControllerChangeType;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.manager.RequestManagerTreeNode;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseGlideControllerSupport<T extends RequestManager> implements RequestManagerTreeNode {

  private WeakReference<Controller> controller;
  private @Nullable ControllerLifecycle lifecycle = null;
  private @Nullable T glideRequests = null;
  private boolean hasDestroyedGlide = false;
  private boolean hasExited = false;

  protected abstract T getGlideRequest(@NonNull ControllerLifecycle lifecycle, RequestManagerTreeNode requestManagerTreeNode);

  public BaseGlideControllerSupport(Controller controller) {
    this.controller = new WeakReference<>(controller);

    controller.addLifecycleListener(new Controller.LifecycleListener() {
      @Override public void preCreateView(@NonNull Controller controller) {
        initGlide();
      }

      @Override public void postAttach(@NonNull Controller controller, @NonNull View view) {
        if (lifecycle != null) {
          lifecycle.onStart();
        }
      }

      @Override public void postDetach(@NonNull Controller controller, @NonNull View view) {
        if (lifecycle != null) {
          lifecycle.onStop();
        }
      }

      @Override public void postDestroy(@NonNull Controller controller) {
        boolean isLast = !controller.getRouter().hasRootController();
        if ((hasExited && !hasDestroyedGlide) && !isLast) {
          destroyGlide();
        }
      }

      @Override public void onChangeEnd(@NonNull Controller controller,
        @NonNull ControllerChangeHandler changeHandler, @NonNull ControllerChangeType changeType) {
        hasExited = !changeType.isEnter;
        boolean isLast = !controller.getRouter().hasRootController();
        if ((hasExited && !hasDestroyedGlide) && !isLast) {
          destroyGlide();
        }
      }

      private void destroyGlide() {
        if (lifecycle != null) {
          lifecycle.onDestroy();
        }
        lifecycle = null;
        glideRequests = null;
        hasDestroyedGlide = true;
      }
    });
  }

  public T getGlide() {
    initGlide();
    return glideRequests;
  }

  private void initGlide() {
    if (glideRequests == null) {
      lifecycle = new ControllerLifecycle();
      glideRequests = getGlideRequest(lifecycle, this);
      hasDestroyedGlide = false;
    }
  }

  @NonNull @Override public Set<RequestManager> getDescendants() {
    Set<RequestManager> collected = new HashSet<>();
    collectRequestManagers(controller.get(), collected);
    return collected;
  }

  /**
   * Recursively gathers the [RequestManager]s of a given [Controller] and all its child controllers.
   * The [Controller]s in the hierarchy must implement [GlideProvider] in order for their
   * request managers to be collected.
   */
  private void collectRequestManagers(@Nullable Controller controller, Set<RequestManager> collected) {
    if (controller != null) {
      if (!controller.isDestroyed() && !controller.isBeingDestroyed()) {
        if (controller instanceof GlideProvider) {
          collected.add(((GlideProvider) controller).getGlide());
        }

        for (Router router : controller.getChildRouters()) {
          for (RouterTransaction transaction : router.getBackstack()) {
            Controller c = transaction.controller();
            collectRequestManagers(c, collected);
          }
        }
      }
    }
  }
}
