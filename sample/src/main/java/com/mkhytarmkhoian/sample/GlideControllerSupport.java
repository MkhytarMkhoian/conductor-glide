package com.mkhytarmkhoian.sample;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bluelinelabs.conductor.Controller;
import com.bumptech.glide.Glide;
import com.bumptech.glide.manager.RequestManagerTreeNode;
import com.mkhytarmkhoian.conductor.glide.BaseGlideControllerSupport;
import com.mkhytarmkhoian.conductor.glide.ControllerLifecycle;

public class GlideControllerSupport extends BaseGlideControllerSupport<GlideRequests> {

  public GlideControllerSupport(Controller controller) {
    super(controller);
  }

  @Override protected GlideRequests getGlideRequest(@NonNull ControllerLifecycle lifecycle, RequestManagerTreeNode requestManagerTreeNode) {
    Context context = App.getInstance().getApplicationContext();
    return new GlideRequests(Glide.get(context), lifecycle, requestManagerTreeNode, context);
  }
}
