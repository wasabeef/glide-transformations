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

import androidx.annotation.NonNull;

import java.security.MessageDigest;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;

/**
 * brightness value ranges from -1.0 to 1.0, with 0.0 as the normal level
 */
public class BrightnessFilterTransformation extends GPUFilterTransformation {

  private static final int VERSION = 1;
  private static final String ID =
    "jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation." + VERSION;

  private final float brightness;

  public BrightnessFilterTransformation() {
    this(0.0f);
  }

  public BrightnessFilterTransformation(float brightness) {
    super(new GPUImageBrightnessFilter());
    this.brightness = brightness;
    GPUImageBrightnessFilter filter = getFilter();
    filter.setBrightness(this.brightness);
  }

  @Override
  public String toString() {
    return "BrightnessFilterTransformation(brightness=" + brightness + ")";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof BrightnessFilterTransformation &&
      ((BrightnessFilterTransformation) o).brightness == brightness;
  }

  @Override
  public int hashCode() {
    return ID.hashCode() + (int) ((brightness + 1.0f) * 10);
  }

  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    messageDigest.update((ID + brightness).getBytes(CHARSET));
  }
}
