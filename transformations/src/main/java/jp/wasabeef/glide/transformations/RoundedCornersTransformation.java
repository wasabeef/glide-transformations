package jp.wasabeef.glide.transformations;

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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

public class RoundedCornersTransformation implements Transformation<Bitmap> {

  public enum CornerType {
    ALL,
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
    TOP, BOTTOM, LEFT, RIGHT,
    OTHER_TOP_LEFT, OTHER_TOP_RIGHT, OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT,
    DIAGONAL_FROM_TOP_LEFT, DIAGONAL_FROM_TOP_RIGHT
  }

  private BitmapPool mBitmapPool;
  private int mRadius;
  private int mDiameter;
  private int mMargin;
  private CornerType mCornerType;

  public RoundedCornersTransformation(Context context, int radius, int margin) {
    this(context, radius, margin, CornerType.ALL);
  }

  public RoundedCornersTransformation(BitmapPool pool, int radius, int margin) {
    this(pool, radius, margin, CornerType.ALL);
  }

  public RoundedCornersTransformation(Context context, int radius, int margin,
      CornerType cornerType) {
    this(Glide.get(context).getBitmapPool(), radius, margin, cornerType);
  }

  public RoundedCornersTransformation(BitmapPool pool, int radius, int margin,
      CornerType cornerType) {
    mBitmapPool = pool;
    mRadius = radius;
    mDiameter = mRadius * 2;
    mMargin = margin;
    mCornerType = cornerType;
  }

  @Override
  public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
    Bitmap source = resource.get();

    int width = source.getWidth();
    int height = source.getHeight();

    Bitmap bitmap = mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
    if (bitmap == null) {
      bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
    drawRoundRect(canvas, paint, width, height);
    return BitmapResource.obtain(bitmap, mBitmapPool);
  }

  private void drawRoundRect(Canvas canvas, Paint paint, float width, float height) {
    float right = width - mMargin;
    float bottom = height - mMargin;

    switch (mCornerType) {
      case ALL:
        canvas.drawRoundRect(new RectF(mMargin, mMargin, right, bottom), mRadius, mRadius, paint);
        break;
      case TOP_LEFT:
        drawTopLeftRoundRect(canvas, paint, right, bottom);
        break;
      case TOP_RIGHT:
        drawTopRightRoundRect(canvas, paint, right, bottom);
        break;
      case BOTTOM_LEFT:
        drawBottomLeftRoundRect(canvas, paint, right, bottom);
        break;
      case BOTTOM_RIGHT:
        drawBottomRightRoundRect(canvas, paint, right, bottom);
        break;
      case TOP:
        drawTopRoundRect(canvas, paint, right, bottom);
        break;
      case BOTTOM:
        drawBottomRoundRect(canvas, paint, right, bottom);
        break;
      case LEFT:
        drawLeftRoundRect(canvas, paint, right, bottom);
        break;
      case RIGHT:
        drawRightRoundRect(canvas, paint, right, bottom);
        break;
      case OTHER_TOP_LEFT:
        drawOtherTopLeftRoundRect(canvas, paint, right, bottom);
        break;
      case OTHER_TOP_RIGHT:
        drawOtherTopRightRoundRect(canvas, paint, right, bottom);
        break;
      case OTHER_BOTTOM_LEFT:
        drawOtherBottomLeftRoundRect(canvas, paint, right, bottom);
        break;
      case OTHER_BOTTOM_RIGHT:
        drawOtherBottomRightRoundRect(canvas, paint, right, bottom);
        break;
      case DIAGONAL_FROM_TOP_LEFT:
        drawDiagonalFromTopLeftRoundRect(canvas, paint, right, bottom);
        break;
      case DIAGONAL_FROM_TOP_RIGHT:
        drawDiagonalFromTopRightRoundRect(canvas, paint, right, bottom);
        break;
      default:
        canvas.drawRoundRect(new RectF(mMargin, mMargin, right, bottom), mRadius, mRadius, paint);
        break;
    }
  }

  private void drawTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(mMargin, mMargin, mMargin + mDiameter, mMargin + mDiameter),
        mRadius, mRadius, paint);
    canvas.drawRect(new RectF(mMargin, mMargin + mRadius, mMargin + mRadius, bottom), paint);
    canvas.drawRect(new RectF(mMargin + mRadius, mMargin, right, bottom), paint);
  }

  private void drawTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(right - mDiameter, mMargin, right, mMargin + mDiameter), mRadius,
        mRadius, paint);
    canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom), paint);
    canvas.drawRect(new RectF(right - mRadius, mMargin + mRadius, right, bottom), paint);
  }

  private void drawBottomLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, mMargin + mDiameter, bottom),
        mRadius, mRadius, paint);
    canvas.drawRect(new RectF(mMargin, mMargin, mMargin + mDiameter, bottom - mRadius), paint);
    canvas.drawRect(new RectF(mMargin + mRadius, mMargin, right, bottom), paint);
  }

  private void drawBottomRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(right - mDiameter, bottom - mDiameter, right, bottom), mRadius,
        mRadius, paint);
    canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom), paint);
    canvas.drawRect(new RectF(right - mRadius, mMargin, right, bottom - mRadius), paint);
  }

  private void drawTopRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(mMargin, mMargin, right, mMargin + mDiameter), mRadius, mRadius,
        paint);
    canvas.drawRect(new RectF(mMargin, mMargin + mRadius, right, bottom), paint);
  }

  private void drawBottomRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, right, bottom), mRadius, mRadius,
        paint);
    canvas.drawRect(new RectF(mMargin, mMargin, right, bottom - mRadius), paint);
  }

  private void drawLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(mMargin, mMargin, mMargin + mDiameter, bottom), mRadius, mRadius,
        paint);
    canvas.drawRect(new RectF(mMargin + mRadius, mMargin, right, bottom), paint);
  }

  private void drawRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(right - mDiameter, mMargin, right, bottom), mRadius, mRadius,
        paint);
    canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom), paint);
  }

  private void drawOtherTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, right, bottom), mRadius, mRadius,
        paint);
    canvas.drawRoundRect(new RectF(right - mDiameter, mMargin, right, bottom), mRadius, mRadius,
        paint);
    canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom - mRadius), paint);
  }

  private void drawOtherTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(mMargin, mMargin, mMargin + mDiameter, bottom), mRadius, mRadius,
        paint);
    canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, right, bottom), mRadius, mRadius,
        paint);
    canvas.drawRect(new RectF(mMargin + mRadius, mMargin, right, bottom - mRadius), paint);
  }

  private void drawOtherBottomLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(mMargin, mMargin, right, mMargin + mDiameter), mRadius, mRadius,
        paint);
    canvas.drawRoundRect(new RectF(right - mDiameter, mMargin, right, bottom), mRadius, mRadius,
        paint);
    canvas.drawRect(new RectF(mMargin, mMargin + mRadius, right - mRadius, bottom), paint);
  }

  private void drawOtherBottomRightRoundRect(Canvas canvas, Paint paint, float right,
      float bottom) {
    canvas.drawRoundRect(new RectF(mMargin, mMargin, right, mMargin + mDiameter), mRadius, mRadius,
        paint);
    canvas.drawRoundRect(new RectF(mMargin, mMargin, mMargin + mDiameter, bottom), mRadius, mRadius,
        paint);
    canvas.drawRect(new RectF(mMargin + mRadius, mMargin + mRadius, right, bottom), paint);
  }

  private void drawDiagonalFromTopLeftRoundRect(Canvas canvas, Paint paint, float right,
      float bottom) {
    canvas.drawRoundRect(new RectF(mMargin, mMargin, mMargin + mDiameter, mMargin + mDiameter),
        mRadius, mRadius, paint);
    canvas.drawRoundRect(new RectF(right - mDiameter, bottom - mDiameter, right, bottom), mRadius,
        mRadius, paint);
    canvas.drawRect(new RectF(mMargin, mMargin + mRadius, right - mDiameter, bottom), paint);
    canvas.drawRect(new RectF(mMargin + mDiameter, mMargin, right, bottom - mRadius), paint);
  }

  private void drawDiagonalFromTopRightRoundRect(Canvas canvas, Paint paint, float right,
      float bottom) {
    canvas.drawRoundRect(new RectF(right - mDiameter, mMargin, right, mMargin + mDiameter), mRadius,
        mRadius, paint);
    canvas.drawRoundRect(new RectF(mMargin, bottom - mDiameter, mMargin + mDiameter, bottom),
        mRadius, mRadius, paint);
    canvas.drawRect(new RectF(mMargin, mMargin, right - mRadius, bottom - mRadius), paint);
    canvas.drawRect(new RectF(mMargin + mRadius, mMargin + mRadius, right, bottom), paint);
  }

  @Override public String getId() {
    return "RoundedTransformation(radius=" + mRadius + ", margin=" + mMargin + ", diameter="
        + mDiameter + ", cornerType=" + mCornerType.name() + ")";
  }
}
