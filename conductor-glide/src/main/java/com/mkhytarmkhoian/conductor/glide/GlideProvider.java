package com.mkhytarmkhoian.conductor.glide;

import com.bumptech.glide.RequestManager;

public interface GlideProvider<T extends RequestManager> {

  T getGlide();
}
