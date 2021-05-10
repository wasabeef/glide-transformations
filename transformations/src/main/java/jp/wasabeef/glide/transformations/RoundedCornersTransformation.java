package jp.wasabeef.glide.transformations;

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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.security.MessageDigest;

public class RoundedCornersTransformation extends BitmapTransformation {

  private static final int VERSION = 1;
  private static final String ID = "jp.wasabeef.glide.transformations.RoundedCornersTransformation." + VERSION;

  private static final int DRAW_SECTION_OFFSET = 1;

  public enum CornerType {
    ALL,
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT,
    TOP, BOTTOM, LEFT, RIGHT,
    OTHER_TOP_LEFT, OTHER_TOP_RIGHT, OTHER_BOTTOM_LEFT, OTHER_BOTTOM_RIGHT,
    DIAGONAL_FROM_TOP_LEFT, DIAGONAL_FROM_TOP_RIGHT
  }

  public static class CornerRadius {
    public int topLeft;
    public int topRight;
    public int bottomLeft;
    public int bottomRight;

    public CornerRadius(int topLeft, int topRight, int bottomLeft, int bottomRight) {
      this.topLeft = topLeft;
      this.topRight = topRight;
      this.bottomLeft = bottomLeft;
      this.bottomRight = bottomRight;
    }

    @Override
    public String toString() {
      return "CornerRadius{" +
        "topLeft=" + topLeft +
        ", topRight=" + topRight +
        ", bottomLeft=" + bottomLeft +
        ", bottomRight=" + bottomRight +
        '}';
    }
  }

  private final CornerRadius radius;
  private final int margin;
  private final CornerType cornerType;

  public RoundedCornersTransformation(int radius, int margin) {
    this(new CornerRadius(radius, radius, radius, radius), margin, CornerType.ALL);
  }

  public RoundedCornersTransformation(CornerRadius radius, int margin) {
    this(radius, margin, CornerType.ALL);
  }

  public RoundedCornersTransformation(CornerRadius radius, int margin, CornerType cornerType) {
    this.radius = radius;
    this.margin = margin;
    this.cornerType = cornerType;
  }

  public RoundedCornersTransformation(int radius, int margin, CornerType cornerType) {
    this.radius = new CornerRadius(radius, radius, radius, radius);
    this.margin = margin;
    this.cornerType = cornerType;
  }

  @Override
  protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
                             @NonNull Bitmap toTransform, int outWidth, int outHeight) {
    int width = toTransform.getWidth();
    int height = toTransform.getHeight();

    Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
    bitmap.setHasAlpha(true);

    setCanvasBitmapDensity(toTransform, bitmap);

    Canvas canvas = new Canvas(bitmap);
    Paint paint = new Paint();
    paint.setAntiAlias(true);
    paint.setShader(new BitmapShader(toTransform, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
    drawRoundRect(canvas, paint, width, height);
    return bitmap;
  }

  private void drawRoundRect(Canvas canvas, Paint paint, float width, float height) {
    float right = width - margin;
    float bottom = height - margin;

    switch (cornerType) {
      case ALL:
        drawAllRoundRect(canvas, paint, right, bottom);
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
        canvas.drawRoundRect(new RectF(margin, margin, right, bottom), radius.topLeft, radius.topLeft, paint);
        break;
    }
  }

  private void drawAllRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    if (radius.topLeft == radius.topRight &&
      radius.topLeft == radius.bottomLeft &&
      radius.topLeft == radius.bottomRight) {
      drawAllRoundRectEquals(canvas, paint, right, bottom);
    } else {
      drawAllRoundRectNonEquals(canvas, paint, right, bottom);
    }
  }

  private void drawAllRoundRectEquals(Canvas canvas, Paint paint, float right, float bottom) {
    canvas.drawRoundRect(new RectF(margin, margin, right, bottom), radius.topLeft, radius.topLeft, paint);
  }

  private void drawAllRoundRectNonEquals(Canvas canvas, Paint paint, float right, float bottom) {
    // top left
    canvas.drawRoundRect(
      new RectF(margin, margin, margin + radius.topLeft * 2, margin + radius.topLeft * 2),
      radius.topLeft,
      radius.topLeft,
      paint);
    canvas.drawRect(new RectF(margin + radius.topLeft, margin, margin + right / 2 + DRAW_SECTION_OFFSET, bottom / 2 + DRAW_SECTION_OFFSET), paint);

    // top right
    canvas.drawRoundRect(new RectF(right - radius.topRight * 2, margin, right, margin + radius.topRight * 2),
      radius.topRight,
      radius.topRight,
      paint);
    canvas.drawRect(new RectF(margin + right / 2, margin, right - radius.topRight, bottom / 2 + DRAW_SECTION_OFFSET), paint);

    // bottom left
    canvas.drawRoundRect(new RectF(margin, bottom - radius.bottomLeft * 2, margin + radius.bottomLeft * 2, bottom),
      radius.bottomLeft,
      radius.bottomLeft,
      paint);
    canvas.drawRect(new RectF(margin + radius.bottomLeft, bottom / 2 - DRAW_SECTION_OFFSET, margin + right / 2 + DRAW_SECTION_OFFSET, bottom), paint);

    // bottom right
    canvas.drawRoundRect(new RectF(right - radius.bottomRight * 2, bottom - radius.bottomRight * 2, right, bottom),
      radius.bottomRight,
      radius.bottomRight,
      paint);
    canvas.drawRect(new RectF(margin + right / 2 - DRAW_SECTION_OFFSET, bottom / 2 - DRAW_SECTION_OFFSET, right - radius.bottomRight, bottom), paint);

    // left vertical
    canvas.drawRect(new RectF(margin, margin + radius.topLeft, margin + Math.max(radius.topLeft, radius.bottomLeft), bottom - radius.bottomLeft), paint);

    // right vertical
    canvas.drawRect(new RectF(right - Math.max(radius.topRight, radius.bottomRight), margin + radius.topRight, right, bottom - radius.bottomRight), paint);
  }

  private void drawTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    int diameter = radius.topLeft * 2;
    canvas.drawRoundRect(new RectF(margin, margin, margin + diameter, margin + diameter), radius.topLeft,
      radius.topLeft, paint);
    canvas.drawRect(new RectF(margin, margin + radius.topLeft, margin + radius.topLeft, bottom), paint);
    canvas.drawRect(new RectF(margin + radius.topLeft, margin, right, bottom), paint);
  }

  private void drawTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    int diameter = radius.topRight * 2;
    canvas.drawRoundRect(new RectF(right - diameter, margin, right, margin + diameter), radius.topRight,
      radius.topRight, paint);
    canvas.drawRect(new RectF(margin, margin, right - radius.topRight, bottom), paint);
    canvas.drawRect(new RectF(right - radius.topRight, margin + radius.topRight, right, bottom), paint);
  }

  private void drawBottomLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    int diameter = radius.bottomLeft * 2;
    canvas.drawRoundRect(new RectF(margin, bottom - diameter, margin + diameter, bottom), radius.bottomLeft,
      radius.bottomLeft, paint);
    canvas.drawRect(new RectF(margin, margin, margin + diameter, bottom - radius.bottomLeft), paint);
    canvas.drawRect(new RectF(margin + radius.bottomLeft, margin, right, bottom), paint);
  }

  private void drawBottomRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    int diameter = radius.bottomRight * 2;
    canvas.drawRoundRect(new RectF(right - diameter, bottom - diameter, right, bottom), radius.bottomRight,
      radius.bottomRight, paint);
    canvas.drawRect(new RectF(margin, margin, right - radius.bottomRight, bottom), paint);
    canvas.drawRect(new RectF(right - radius.bottomRight, margin, right, bottom - radius.bottomRight), paint);
  }

  private void drawTopRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    int diameter = radius.topLeft * 2;
    canvas.drawRoundRect(new RectF(margin, margin, right, margin + diameter), radius.topLeft, radius.topLeft,
      paint);
    canvas.drawRect(new RectF(margin, margin + radius.topLeft, right, bottom), paint);
  }

  private void drawBottomRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    int diameter = radius.bottomLeft * 2;
    canvas.drawRoundRect(new RectF(margin, bottom - diameter, right, bottom), radius.bottomLeft, radius.bottomLeft,
      paint);
    canvas.drawRect(new RectF(margin, margin, right, bottom - radius.bottomLeft), paint);
  }

  private void drawLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    int diameter = radius.topLeft * 2;
    canvas.drawRoundRect(new RectF(margin, margin, margin + diameter, bottom), radius.topLeft, radius.topLeft,
      paint);
    canvas.drawRect(new RectF(margin + radius.topLeft, margin, right, bottom), paint);
  }

  private void drawRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    int diameter = radius.topRight * 2;
    canvas.drawRoundRect(new RectF(right - diameter, margin, right, bottom), radius.topRight, radius.topRight, paint);
    canvas.drawRect(new RectF(margin, margin, right - radius.topRight, bottom), paint);
  }

  private void drawOtherTopLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    int diameter = radius.topLeft * 2;
    canvas.drawRoundRect(new RectF(margin, bottom - diameter, right, bottom), radius.topLeft, radius.topLeft,
      paint);
    canvas.drawRoundRect(new RectF(right - diameter, margin, right, bottom), radius.topLeft, radius.topLeft, paint);
    canvas.drawRect(new RectF(margin, margin, right - radius.topLeft, bottom - radius.topLeft), paint);
  }

  private void drawOtherTopRightRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    int diameter = radius.topRight * 2;
    canvas.drawRoundRect(new RectF(margin, margin, margin + diameter, bottom), radius.topRight, radius.topRight,
      paint);
    canvas.drawRoundRect(new RectF(margin, bottom - diameter, right, bottom), radius.topRight, radius.topRight,
      paint);
    canvas.drawRect(new RectF(margin + radius.topRight, margin, right, bottom - radius.topRight), paint);
  }

  private void drawOtherBottomLeftRoundRect(Canvas canvas, Paint paint, float right, float bottom) {
    int diameter = radius.bottomLeft * 2;
    canvas.drawRoundRect(new RectF(margin, margin, right, margin + diameter), radius.bottomLeft, radius.bottomLeft,
      paint);
    canvas.drawRoundRect(new RectF(right - diameter, margin, right, bottom), radius.bottomLeft, radius.bottomLeft, paint);
    canvas.drawRect(new RectF(margin, margin + radius.bottomLeft, right - radius.bottomLeft, bottom), paint);
  }

  private void drawOtherBottomRightRoundRect(Canvas canvas, Paint paint, float right,
                                             float bottom) {
    int diameter = radius.bottomRight * 2;
    canvas.drawRoundRect(new RectF(margin, margin, right, margin + diameter), radius.bottomRight, radius.bottomRight,
      paint);
    canvas.drawRoundRect(new RectF(margin, margin, margin + diameter, bottom), radius.bottomRight, radius.bottomRight,
      paint);
    canvas.drawRect(new RectF(margin + radius.bottomRight, margin + radius.bottomRight, right, bottom), paint);
  }

  private void drawDiagonalFromTopLeftRoundRect(Canvas canvas, Paint paint, float right,
                                                float bottom) {
    int diameterTopLeft = radius.topLeft * 2;
    int diameterBottomRight = radius.bottomRight * 2;
    canvas.drawRoundRect(new RectF(margin, margin, margin + diameterTopLeft, margin + diameterTopLeft), radius.topLeft,
      radius.topLeft, paint);
    canvas.drawRoundRect(new RectF(right - diameterBottomRight, bottom - diameterBottomRight, right, bottom), radius.topRight,
      radius.topRight, paint);
    canvas.drawRect(new RectF(margin, margin + radius.topLeft, right - radius.bottomRight, bottom), paint);
    canvas.drawRect(new RectF(margin + radius.topLeft, margin, right, bottom - radius.topRight), paint);
  }

  private void drawDiagonalFromTopRightRoundRect(Canvas canvas, Paint paint, float right,
                                                 float bottom) {
    int diameterTopRight = radius.topRight * 2;
    int diameterBottomLeft = radius.bottomLeft * 2;
    canvas.drawRoundRect(new RectF(right - diameterTopRight, margin, right, margin + diameterTopRight), radius.topRight,
      radius.topRight, paint);
    canvas.drawRoundRect(new RectF(margin, bottom - diameterBottomLeft, margin + diameterBottomLeft, bottom), radius.bottomLeft,
      radius.bottomLeft, paint);
    canvas.drawRect(new RectF(margin, margin, right - radius.topRight, bottom - radius.bottomLeft), paint);
    canvas.drawRect(new RectF(margin + radius.bottomLeft, margin + radius.topRight, right, bottom), paint);
  }

  @Override
  public String toString() {
    return "RoundedTransformation(radius=" + radius + ", margin=" + margin + ", cornerType=" + cornerType.name() + ")";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof RoundedCornersTransformation &&
      ((RoundedCornersTransformation) o).radius == radius &&
      ((RoundedCornersTransformation) o).margin == margin &&
      ((RoundedCornersTransformation) o).cornerType == cornerType;
  }

  @Override
  public int hashCode() {
    return ID.hashCode() + (radius.topLeft + radius.topRight + radius.bottomLeft + radius.bottomRight) * 10000 + margin * 100 + cornerType.ordinal() * 10;
  }

  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    messageDigest.update((ID + radius + margin + cornerType).getBytes(CHARSET));
  }
}
