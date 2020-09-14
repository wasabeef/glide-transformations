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
import android.graphics.Canvas;
import android.graphics.RectF;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.security.MessageDigest;

public class CropTransformation extends BitmapTransformation {

  private static final int VERSION = 1;
  private static final String ID = "jp.wasabeef.glide.transformations.CropTransformation." + VERSION;

  public enum CropType {
    TOP,
    CENTER,
    BOTTOM
  }

  private int width;
  private int height;

  private CropType cropType = CropType.CENTER;

  public CropTransformation(int width, int height) {
    this(width, height, CropType.CENTER);
  }

  public CropTransformation(int width, int height, CropType cropType) {
    this.width = width;
    this.height = height;
    this.cropType = cropType;
  }

  @Override
  protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
                             @NonNull Bitmap toTransform, int outWidth, int outHeight) {

    width = width == 0 ? toTransform.getWidth() : width;
    height = height == 0 ? toTransform.getHeight() : height;

    Bitmap.Config config =
      toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
    Bitmap bitmap = pool.get(width, height, config);

    bitmap.setHasAlpha(true);

    float scaleX = (float) width / toTransform.getWidth();
    float scaleY = (float) height / toTransform.getHeight();
    float scale = Math.max(scaleX, scaleY);

    float scaledWidth = scale * toTransform.getWidth();
    float scaledHeight = scale * toTransform.getHeight();
    float left = (width - scaledWidth) / 2;
    float top = getTop(scaledHeight);
    RectF targetRect = new RectF(left, top, left + scaledWidth, top + scaledHeight);

    setCanvasBitmapDensity(toTransform, bitmap);

    Canvas canvas = new Canvas(bitmap);
    canvas.drawBitmap(toTransform, null, targetRect, null);

    return bitmap;
  }

  private float getTop(float scaledHeight) {
    switch (cropType) {
      case TOP:
        return 0;
      case CENTER:
        return (height - scaledHeight) / 2;
      case BOTTOM:
        return height - scaledHeight;
      default:
        return 0;
    }
  }

  @Override
  public String toString() {
    return "CropTransformation(width=" + width + ", height=" + height + ", cropType=" + cropType + ")";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof CropTransformation &&
      ((CropTransformation) o).width == width &&
      ((CropTransformation) o).height == height &&
      ((CropTransformation) o).cropType == cropType;
  }

  @Override
  public int hashCode() {
    return ID.hashCode() + width * 100000 + height * 1000 + cropType.ordinal() * 10;
  }

  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    messageDigest.update((ID + width + height + cropType).getBytes(CHARSET));
  }
}
