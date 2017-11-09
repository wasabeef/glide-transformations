package jp.wasabeef.glide.transformations.gpu;

/**
 * Copyright (C) 2017 Wasabeef
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

import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.wasabeef.glide.transformations.BitmapTransformation;

/**
 * Applies a simple sepia effect.
 *
 * The intensity with a default of 1.0.
 */
public class SepiaFilterTransformation extends GPUFilterTransformation {

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

  @Override
  protected Class<? extends BitmapTransformation> clazz() {
    return jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation.class;
  }
}
