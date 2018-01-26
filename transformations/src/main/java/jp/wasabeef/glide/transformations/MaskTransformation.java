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
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import java.security.MessageDigest;
import jp.wasabeef.glide.transformations.internal.Utils;

public class MaskTransformation extends BitmapTransformation {

  private static final int VERSION = 1;
  private static final String ID =
      "jp.wasabeef.glide.transformations.MaskTransformation." + VERSION;
  private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

  private static Paint paint = new Paint();
  private int maskId;

  static {
    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
  }

  /**
   * @param maskId If you change the mask file, please also rename the mask file, or Glide will get
   * the cache with the old mask. Because key() return the same values if using the
   * same make file name. If you have a good idea please tell us, thanks.
   */
  public MaskTransformation(int maskId) {
    this.maskId = maskId;
  }

  @Override protected Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
      @NonNull Bitmap toTransform, int outWidth, int outHeight) {
    int width = toTransform.getWidth();
    int height = toTransform.getHeight();

    Bitmap bitmap = pool.get(width, height, Bitmap.Config.ARGB_8888);
    bitmap.setHasAlpha(true);

    Drawable mask = Utils.getMaskDrawable(context.getApplicationContext(), maskId);

    Canvas canvas = new Canvas(bitmap);
    mask.setBounds(0, 0, width, height);
    mask.draw(canvas);
    canvas.drawBitmap(toTransform, 0, 0, paint);

    return bitmap;
  }

  @Override public String toString() {
    return "MaskTransformation(maskId=" + maskId + ")";
  }

  @Override public boolean equals(Object o) {
    return o instanceof MaskTransformation;
  }

  @Override public int hashCode() {
    return ID.hashCode();
  }

  @Override public void updateDiskCacheKey(MessageDigest messageDigest) {
    messageDigest.update(ID_BYTES);
  }
}
