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
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;

/**
 * Applies a simple sepia effect.
 *
 * The intensity with a default of 1.0.
 */
public class SepiaFilterTransformation extends GPUFilterTransformation {

  private static final int VERSION = 1;
  private static final String ID =
      "jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation." + VERSION;
  private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

  private float intensity;

  public SepiaFilterTransformation() {
    this(1.0f);
  }

  public SepiaFilterTransformation(float intensity) {
    super(new GPUImageSepiaFilter());
    this.intensity = intensity;
    GPUImageSepiaFilter filter = getFilter();
    filter.setIntensity(this.intensity);
  }

  @Override public String toString() {
    return "SepiaFilterTransformation(intensity=" + intensity + ")";
  }

  @Override public boolean equals(Object o) {
    return o instanceof SepiaFilterTransformation;
  }

  @Override public int hashCode() {
    return ID.hashCode();
  }

  @Override public void updateDiskCacheKey(MessageDigest messageDigest) {
    messageDigest.update(ID_BYTES);
  }
}
