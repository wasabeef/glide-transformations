package jp.wasabeef.glide.transformations.gpu;

/**
 * Copyright (C) 2018 Wasabeef
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

import java.security.MessageDigest;

import androidx.annotation.NonNull;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSepiaToneFilter;

/**
 * Applies a simple sepia effect.
 *
 * The intensity with a default of 1.0.
 */
public class SepiaFilterTransformation extends GPUFilterTransformation {

  private static final int VERSION = 1;
  private static final String ID =
      "jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation." + VERSION;

  private float intensity;

  public SepiaFilterTransformation() {
    this(1.0f);
  }

  public SepiaFilterTransformation(float intensity) {
    super(new GPUImageSepiaToneFilter());
    this.intensity = intensity;
    GPUImageSepiaToneFilter filter = getFilter();
    filter.setIntensity(this.intensity);
  }

  @Override
  public String toString() {
    return "SepiaFilterTransformation(intensity=" + intensity + ")";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof SepiaFilterTransformation;
  }

  @Override
  public int hashCode() {
    return ID.hashCode() + (int) (intensity * 10);
  }

  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    messageDigest.update((ID + intensity).getBytes(CHARSET));
  }
}
