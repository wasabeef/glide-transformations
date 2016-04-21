package jp.wasabeef.glide.transformations;

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
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.renderscript.RSRuntimeException;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import jp.wasabeef.glide.transformations.internal.FastBlur;
import jp.wasabeef.glide.transformations.internal.RSBlur;

public class BlurTransformation implements Transformation<Bitmap> {

  private static int MAX_RADIUS = 25;
  private static int DEFAULT_DOWN_SAMPLING = 1;

  private Context mContext;
  private BitmapPool mBitmapPool;

  private int mRadius;
  private int mSampling;

  public BlurTransformation(Context context) {
    this(context, Glide.get(context).getBitmapPool(), MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
  }

  public BlurTransformation(Context context, BitmapPool pool) {
    this(context, pool, MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
  }

  public BlurTransformation(Context context, BitmapPool pool, int radius) {
    this(context, pool, radius, DEFAULT_DOWN_SAMPLING);
  }

  public BlurTransformation(Context context, int radius) {
    this(context, Glide.get(context).getBitmapPool(), radius, DEFAULT_DOWN_SAMPLING);
  }

  public BlurTransformation(Context context, int radius, int sampling) {
    this(context, Glide.get(context).getBitmapPool(), radius, sampling);
  }

  public BlurTransformation(Context context, BitmapPool pool, int radius, int sampling) {
    mContext = context.getApplicationContext();
    mBitmapPool = pool;
    mRadius = radius;
    mSampling = sampling;
  }

  @Override
  public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
    Bitmap source = resource.get();

    int width = source.getWidth();
    int height = source.getHeight();
    int scaledWidth = width / mSampling;
    int scaledHeight = height / mSampling;

    Bitmap bitmap = mBitmapPool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
    if (bitmap == null) {
      bitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(bitmap);
    canvas.scale(1 / (float) mSampling, 1 / (float) mSampling);
    Paint paint = new Paint();
    paint.setFlags(Paint.FILTER_BITMAP_FLAG);
    canvas.drawBitmap(source, 0, 0, paint);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
      try {
        bitmap = RSBlur.blur(mContext, bitmap, mRadius);
      } catch (RSRuntimeException e) {
        bitmap = FastBlur.blur(bitmap, mRadius, true);
      }
    } else {
      bitmap = FastBlur.blur(bitmap, mRadius, true);
    }

    return BitmapResource.obtain(bitmap, mBitmapPool);
  }

  @Override public String getId() {
    return "BlurTransformation(radius=" + mRadius + ", sampling=" + mSampling + ")";
  }
}
