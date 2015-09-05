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
import android.graphics.RectF;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

public class CropTransformation implements Transformation<Bitmap> {

  public enum CropType {
    TOP,
    CENTER,
    BOTTOM
  }

  private BitmapPool mBitmapPool;
  private int mWidth;
  private int mHeight;

  private CropType mCropType = CropType.CENTER;

  public CropTransformation(Context context) {
    this(Glide.get(context).getBitmapPool());
  }

  public CropTransformation(BitmapPool pool) {
    this(pool, 0, 0);
  }

  public CropTransformation(Context context, int width, int height) {
    this(Glide.get(context).getBitmapPool(), width, height);
  }

  public CropTransformation(BitmapPool pool, int width, int height) {
    this(pool, width, height, CropType.CENTER);
  }

  public CropTransformation(Context context, int width, int height, CropType cropType) {
    this(Glide.get(context).getBitmapPool(), width, height, cropType);
  }

  public CropTransformation(BitmapPool pool, int width, int height, CropType cropType) {
    mBitmapPool = pool;
    mWidth = width;
    mHeight = height;
    mCropType = cropType;
  }

  @Override
  public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
    Bitmap source = resource.get();
    mWidth = mWidth == 0 ? source.getWidth() : mWidth;
    mHeight = mHeight == 0 ? source.getHeight() : mHeight;

    Bitmap.Config config =
        source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
    Bitmap bitmap = mBitmapPool.get(mWidth, mHeight, config);
    if (bitmap == null) {
      bitmap = Bitmap.createBitmap(mWidth, mHeight, config);
    }

    float scaleX = (float) mWidth / source.getWidth();
    float scaleY = (float) mHeight / source.getHeight();
    float scale = Math.max(scaleX, scaleY);

    float scaledWidth = scale * source.getWidth();
    float scaledHeight = scale * source.getHeight();
    float left = (mWidth - scaledWidth) / 2;
    float top = getTop(scaledHeight);
    RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

    Canvas canvas = new Canvas(bitmap);
    canvas.drawBitmap(source, null, targetRect, null);

    return BitmapResource.obtain(bitmap, mBitmapPool);
  }

  @Override public String getId() {
    return "CropTransformation(width=" + mWidth + ", height=" + mHeight + ", cropType=" + mCropType
        + ")";
  }

  private float getTop(float scaledHeight) {
    switch (mCropType) {
      case TOP:
        return 0;
      case CENTER:
        return (mHeight - scaledHeight) / 2;
      case BOTTOM:
        return mHeight - scaledHeight;
      default:
        return 0;
    }
  }
}
