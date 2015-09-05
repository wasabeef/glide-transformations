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
import android.graphics.Bitmap;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter;

/**
 * The threshold at which to apply the edges, default of 0.2.
 * The levels of quantization for the posterization of colors within the scene,
 * with a default of 10.0.
 */
public class ToonFilterTransformation implements Transformation<Bitmap> {

  private Context mContext;
  private BitmapPool mBitmapPool;

  private GPUImageToonFilter mFilter = new GPUImageToonFilter();
  private float mThreshold;
  private float mQuantizationLevels;

  public ToonFilterTransformation(Context context) {
    this(context, Glide.get(context).getBitmapPool());
  }

  public ToonFilterTransformation(Context context, BitmapPool pool) {
    this(context, pool, .2f, 10.0f);
  }

  public ToonFilterTransformation(Context context, float threshold, float quantizationLevels) {
    this(context, Glide.get(context).getBitmapPool(), threshold, quantizationLevels);
  }

  public ToonFilterTransformation(Context context, BitmapPool pool, float threshold,
      float quantizationLevels) {
    mContext = context;
    mBitmapPool = pool;
    mThreshold = threshold;
    mQuantizationLevels = quantizationLevels;
    mFilter.setThreshold(mThreshold);
    mFilter.setQuantizationLevels(mQuantizationLevels);
  }

  @Override
  public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
    Bitmap source = resource.get();

    GPUImage gpuImage = new GPUImage(mContext);
    gpuImage.setImage(source);
    gpuImage.setFilter(mFilter);
    Bitmap bitmap = gpuImage.getBitmapWithFilterApplied();

    source.recycle();

    return BitmapResource.obtain(bitmap, mBitmapPool);
  }

  @Override public String getId() {
    return "ToonFilterTransformation(threshold=" + mThreshold +
        ",quantizationLevels=" + mQuantizationLevels + ")";
  }
}
