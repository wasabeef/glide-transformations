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

import jp.co.cyberagent.android.gpuimage.filter.GPUImageToonFilter;

/**
 * The threshold at which to apply the edges, default of 0.2.
 * The levels of quantization for the posterization of colors within the scene,
 * with a default of 10.0.
 */
public class ToonFilterTransformation extends GPUFilterTransformation {

  private static final int VERSION = 1;
  private static final String ID =
    "jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation." + VERSION;

  private final float threshold;
  private final float quantizationLevels;

  public ToonFilterTransformation() {
    this(.2f, 10.0f);
  }

  public ToonFilterTransformation(float threshold, float quantizationLevels) {
    super(new GPUImageToonFilter());
    this.threshold = threshold;
    this.quantizationLevels = quantizationLevels;
    GPUImageToonFilter filter = getFilter();
    filter.setThreshold(this.threshold);
    filter.setQuantizationLevels(this.quantizationLevels);
  }

  @Override
  public String toString() {
    return "ToonFilterTransformation(threshold=" + threshold + ",quantizationLevels="
      + quantizationLevels + ")";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof ToonFilterTransformation &&
      ((ToonFilterTransformation) o).threshold == threshold &&
      ((ToonFilterTransformation) o).quantizationLevels == quantizationLevels;
  }

  @Override
  public int hashCode() {
    return ID.hashCode() + (int) (threshold * 1000) + (int) (quantizationLevels * 10);
  }

  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    messageDigest.update((ID + threshold + quantizationLevels).getBytes(CHARSET));
  }
}
