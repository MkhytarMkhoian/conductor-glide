package com.mkhytarmkhoian.sample;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mkhytarmkhoian.conductor.glide.GlideProvider;

public class HomeController extends Controller implements GlideProvider<GlideRequests>{

  private GlideControllerSupport glideControllerSupport = new GlideControllerSupport(this);

  @Override public GlideRequests getGlide() {
    return glideControllerSupport.getGlide();
  }

  @NonNull @Override protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

    getGlide().load("url")
      .centerCrop()
      .into(new SimpleTarget<Drawable>() {
        @Override public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

        }
      });

    return null;
  }
}
