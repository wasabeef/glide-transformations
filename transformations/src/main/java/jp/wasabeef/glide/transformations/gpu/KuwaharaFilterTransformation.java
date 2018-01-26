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
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;

/**
 * Kuwahara all the colors in the image.
 *
 * The radius to sample from when creating the brush-stroke effect, with a default of 25.
 * The larger the radius, the slower the filter.
 */
public class KuwaharaFilterTransformation extends GPUFilterTransformation {

  private static final int VERSION = 1;
  private static final String ID =
      "jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation." + VERSION;
  private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

  private int radius;

  public KuwaharaFilterTransformation() {
    this(25);
  }

  public KuwaharaFilterTransformation(int radius) {
    super(new GPUImageKuwaharaFilter());
    this.radius = radius;
    GPUImageKuwaharaFilter filter = getFilter();
    filter.setRadius(this.radius);
  }

  @Override public String toString() {
    return "KuwaharaFilterTransformation(radius=" + radius + ")";
  }

  @Override public boolean equals(Object o) {
    return o instanceof KuwaharaFilterTransformation;
  }

  @Override public int hashCode() {
    return ID.hashCode();
  }

  @Override public void updateDiskCacheKey(MessageDigest messageDigest) {
    messageDigest.update(ID_BYTES);
  }
}
