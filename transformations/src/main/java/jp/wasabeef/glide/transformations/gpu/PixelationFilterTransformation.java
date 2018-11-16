package jp.wasabeef.glide.transformations.gpu;

/**
 * Copyright (C) 2018 Wasabeef
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.security.MessageDigest;

import androidx.annotation.NonNull;
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePixelationFilter;

/**
 * Applies a Pixelation effect to the image.
 *
 * The pixel with a default of 10.0.
 */
public class PixelationFilterTransformation extends GPUFilterTransformation {

  private static final int VERSION = 1;
  private static final String ID =
      "jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation." + VERSION;

  private float pixel;

  public PixelationFilterTransformation() {
    this(10f);
  }

  public PixelationFilterTransformation(float pixel) {
    super(new GPUImagePixelationFilter());
    this.pixel = pixel;
    GPUImagePixelationFilter filter = getFilter();
    filter.setPixel(this.pixel);
  }

  @Override
  public String toString() {
    return "PixelationFilterTransformation(pixel=" + pixel + ")";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof PixelationFilterTransformation;
  }

  @Override
  public int hashCode() {
    return ID.hashCode() + (int) (pixel * 10);
  }

  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    messageDigest.update((ID + pixel).getBytes(CHARSET));
  }
}
