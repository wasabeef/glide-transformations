package jp.wasabeef.glide.transformations;

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

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.security.MessageDigest;

import androidx.annotation.NonNull;
import jp.wasabeef.glide.transformations.internal.FastBlur;

public class BlurTransformation extends BitmapTransformation {

  private static final int VERSION = 1;
  private static final String ID =
      "jp.wasabeef.glide.transformations.BlurTransformation." + VERSION;

  private static int MAX_RADIUS = 25;
  private static int DEFAULT_DOWN_SAMPLING = 1;

  private int radius;
  private int sampling;

  public BlurTransformation() {
    this(MAX_RADIUS, DEFAULT_DOWN_SAMPLING);
  }

  public BlurTransformation(int radius) {
    this(radius, DEFAULT_DOWN_SAMPLING);
  }

  public BlurTransformation(int radius, int sampling) {
    this.radius = radius;
    this.sampling = sampling;
  }

  @Override
  protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
                             @NonNull Bitmap toTransform, int outWidth, int outHeight) {

    int width = toTransform.getWidth();
    int height = toTransform.getHeight();
    int scaledWidth = width / sampling;
    int scaledHeight = height / sampling;

    Bitmap bitmap = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);

    Canvas canvas = new Canvas(bitmap);
    canvas.scale(1 / (float) sampling, 1 / (float) sampling);
    Paint paint = new Paint();
    paint.setFlags(Paint.FILTER_BITMAP_FLAG);
    canvas.drawBitmap(toTransform, 0, 0, paint);

    bitmap = FastBlur.blur(bitmap, radius, true);

    return bitmap;
  }

  @Override
  public String toString() {
    return "BlurTransformation(radius=" + radius + ", sampling=" + sampling + ")";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof BlurTransformation &&
        ((BlurTransformation) o).radius == radius &&
        ((BlurTransformation) o).sampling == sampling;
  }

  @Override
  public int hashCode() {
    return ID.hashCode() + radius * 1000 + sampling * 10;
  }

  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    messageDigest.update((ID + radius + sampling).getBytes(CHARSET));
  }
}
