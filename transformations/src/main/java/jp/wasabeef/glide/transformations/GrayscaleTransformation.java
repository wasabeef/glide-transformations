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
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import java.security.MessageDigest;

public class GrayscaleTransformation extends BitmapTransformation {

  private static final int VERSION = 1;
  private static final String ID =
    "jp.wasabeef.glide.transformations.GrayscaleTransformation." + VERSION;

  @Override
  protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
                             @NonNull Bitmap toTransform, int outWidth, int outHeight) {
    int width = toTransform.getWidth();
    int height = toTransform.getHeight();

    Bitmap.Config config =
      toTransform.getConfig() != null ? toTransform.getConfig() : Bitmap.Config.ARGB_8888;
    Bitmap bitmap = pool.get(width, height, config);

    setCanvasBitmapDensity(toTransform, bitmap);

    Canvas canvas = new Canvas(bitmap);
    ColorMatrix saturation = new ColorMatrix();
    saturation.setSaturation(0f);
    Paint paint = new Paint();
    paint.setColorFilter(new ColorMatrixColorFilter(saturation));
    canvas.drawBitmap(toTransform, 0, 0, paint);

    return bitmap;
  }

  @Override
  public String toString() {
    return "GrayscaleTransformation()";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof GrayscaleTransformation;
  }

  @Override
  public int hashCode() {
    return ID.hashCode();
  }

  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    messageDigest.update((ID).getBytes(CHARSET));
  }
}
