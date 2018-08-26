package com.mkhytarmkhoian.sample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;


@GlideModule
public class LGlideModule extends AppGlideModule {

  @Override public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
    MemorySizeCalculator memory = new MemorySizeCalculator.Builder(context).build();
    builder.setDefaultRequestOptions(RequestOptions.formatOf(DecodeFormat.PREFER_ARGB_8888));
    builder.setMemoryCache(new LruResourceCache((memory.getMemoryCacheSize() / 2L)));
    builder.setBitmapPool(new LruBitmapPool((memory.getBitmapPoolSize() / 2L)));
    if (BuildConfig.DEBUG) {
      builder.setLogLevel(Log.VERBOSE);
    }

    super.applyOptions(context, builder);
  }

  @Override public boolean isManifestParsingEnabled() {
    return false;
  }
}
