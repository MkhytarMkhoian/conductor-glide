package com.mkhytarmkhoian.conductor.glide;

import android.support.annotation.NonNull;

import com.bumptech.glide.manager.Lifecycle;
import com.bumptech.glide.manager.LifecycleListener;
import com.bumptech.glide.util.Util;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

/**
 * A [com.bumptech.glide.manager.Lifecycle] implementation for tracking and notifying
 * listeners of [com.bluelinelabs.conductor.Controller] lifecycle events.
 */
public class ControllerLifecycle implements Lifecycle {

  private Set<LifecycleListener> lifecycleListeners = Collections.newSetFromMap(new WeakHashMap<LifecycleListener, Boolean>());
  private boolean isStarted = false;
  private boolean isDestroyed = false;

  /**
   * Adds the given listener to the list of listeners to be notified on each lifecycle event.
   * <p>
   * The latest lifecycle event will be called on the given listener synchronously in this
   * method. If the activity or fragment is stopped, [LifecycleListener.onStop]} will be
   * called, and same for onStart and onDestroy.
   * <p>
   * Note - [com.bumptech.glide.manager.LifecycleListener]s that are added more than once
   * will have their lifecycle methods called more than once. It is the caller's responsibility to
   * avoid adding listeners multiple times.
   */
  @Override public void addListener(@NonNull LifecycleListener listener) {
    lifecycleListeners.add(listener);

    if (isDestroyed) {
      listener.onDestroy();
    } else if (isStarted) {
      listener.onStart();
    } else {
      listener.onStop();
    }
  }

  @Override public void removeListener(@NonNull LifecycleListener listener) {
    lifecycleListeners.remove(listener);
  }

  public void onStart() {
    isStarted = true;

    for (LifecycleListener lifecycleListener : Util.getSnapshot(lifecycleListeners)) {
      lifecycleListener.onStart();
    }
  }

  public void onStop() {
    isStarted = false;
    for (LifecycleListener lifecycleListener : Util.getSnapshot(lifecycleListeners)) {
      lifecycleListener.onStop();
    }
  }

  public void onDestroy() {
    isDestroyed = true;
    for (LifecycleListener lifecycleListener : Util.getSnapshot(lifecycleListeners)) {
      lifecycleListener.onDestroy();
    }
  }
}
