package jp.wasabeef.glide.transformations.gpu;

/**
 * Copyright (C) 2020 Wasabeef
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.security.MessageDigest;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.wasabeef.glide.transformations.BitmapTransformation;

public class GPUFilterTransformation extends BitmapTransformation {

  private static final int VERSION = 1;
  private static final String ID =
    "jp.wasabeef.glide.transformations.gpu.GPUFilterTransformation." + VERSION;
  private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

  private final GPUImageFilter gpuImageFilter;

  public GPUFilterTransformation(GPUImageFilter filter) {
    this.gpuImageFilter = filter;
  }

  @Override
  protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
                             @NonNull Bitmap toTransform, int outWidth, int outHeight) {
    GPUImage gpuImage = new GPUImage(context);
    gpuImage.setImage(toTransform);
    gpuImage.setFilter(gpuImageFilter);

    return gpuImage.getBitmapWithFilterApplied();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName();
  }

  @SuppressWarnings("unchecked")
  public <T> T getFilter() {
    return (T) gpuImageFilter;
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof GPUFilterTransformation;
  }

  @Override
  public int hashCode() {
    return ID.hashCode();
  }

  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    messageDigest.update(ID_BYTES);
  }
}
