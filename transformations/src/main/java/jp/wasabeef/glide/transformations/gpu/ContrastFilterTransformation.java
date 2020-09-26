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

import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter;

/**
 * contrast value ranges from 0.0 to 4.0, with 1.0 as the normal level
 */
public class ContrastFilterTransformation extends GPUFilterTransformation {

  private static final int VERSION = 1;
  private static final String ID =
    "jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation." + VERSION;

  private final float contrast;

  public ContrastFilterTransformation() {
    this(1.0f);
  }

  public ContrastFilterTransformation(float contrast) {
    super(new GPUImageContrastFilter());
    this.contrast = contrast;
    GPUImageContrastFilter filter = getFilter();
    filter.setContrast(this.contrast);
  }

  @Override
  public String toString() {
    return "ContrastFilterTransformation(contrast=" + contrast + ")";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ContrastFilterTransformation;
  }

  @Override
  public int hashCode() {
    return ID.hashCode() + (int) (contrast * 10);
  }

  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    messageDigest.update((ID + contrast).getBytes(CHARSET));
  }
}
