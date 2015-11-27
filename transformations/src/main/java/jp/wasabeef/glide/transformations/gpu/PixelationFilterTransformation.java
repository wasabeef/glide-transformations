package jp.wasabeef.glide.transformations.gpu;

/**
 * Copyright (C) 2015 Wasabeef
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

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import jp.co.cyberagent.android.gpuimage.GPUImagePixelationFilter;

/**
 * Applies a Pixelation effect to the image.
 *
 * The pixel with a default of 10.0.
 */
public class PixelationFilterTransformation extends GPUFilterTransformation {

  private float mPixel;

  public PixelationFilterTransformation(Context context) {
    this(context, Glide.get(context).getBitmapPool());
  }

  public PixelationFilterTransformation(Context context, BitmapPool pool) {
    this(context, pool, 10f);
  }

  public PixelationFilterTransformation(Context context, float pixel) {
    this(context, Glide.get(context).getBitmapPool(), pixel);
  }

  public PixelationFilterTransformation(Context context, BitmapPool pool, float pixel) {
    super(context, pool, new GPUImagePixelationFilter());
    mPixel = pixel;
    GPUImagePixelationFilter filter = getFilter();
    filter.setPixel(mPixel);
  }

  @Override public String getId() {
    return "PixelationFilterTransformation(pixel=" + mPixel + ")";
  }
}
