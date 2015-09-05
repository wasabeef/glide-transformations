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
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;

/**
 * Kuwahara image abstraction, drawn from the work of Kyprianidis, et. al. in their publication
 * "Anisotropic Kuwahara Filtering on the GPU" within the GPU Pro collection. This produces an
 * oil-painting-like
 * image, but it is extremely computationally expensive, so it can take seconds to render a frame
 * on
 * an iPad 2.
 * This might be best used for still images.
 *
 * The radius to sample from when creating the brush-stroke effect, with a default of 25.
 * The larger the radius, the slower the filter.
 */
public class KuwaharaFilterTransformation implements Transformation<Bitmap> {

  private Context mContext;
  private BitmapPool mBitmapPool;

  private GPUImageKuwaharaFilter mFilter = new GPUImageKuwaharaFilter();
  private int mRadius;

  public KuwaharaFilterTransformation(Context context) {
    this(context, Glide.get(context).getBitmapPool());
  }

  public KuwaharaFilterTransformation(Context context, BitmapPool pool) {
    this(context, pool, 25);
  }

  public KuwaharaFilterTransformation(Context context, int radius) {
    this(context, Glide.get(context).getBitmapPool(), radius);
  }

  public KuwaharaFilterTransformation(Context context, BitmapPool pool, int radius) {
    mContext = context;
    mBitmapPool = pool;
    mRadius = radius;
    mFilter.setRadius(mRadius);
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
    return "KuwaharaFilterTransformation(radius=" + mRadius + ")";
  }
}
