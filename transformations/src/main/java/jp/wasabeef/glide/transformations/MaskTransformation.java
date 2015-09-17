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
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import jp.wasabeef.glide.transformations.internal.Util;

public class MaskTransformation implements Transformation<Bitmap> {

  private static Paint mMaskingPaint = new Paint();
  private Context mContext;
  private BitmapPool mBitmapPool;
  private int mMaskId;

  static {
    mMaskingPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
  }

  /**
   * @param maskId If you change the mask file, please rename the mask file, or Glide will get the
   *               cache with the old mask. Because getId() will return the same values if using the
   *               same make file name. If you have a good idea please tell we, thanks.
   */
  public MaskTransformation(Context context, int maskId) {
    mBitmapPool = Glide.get(context).getBitmapPool();
    mContext = context;
    mMaskId = maskId;
  }

  @Override
  public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
    Bitmap source = resource.get();
    int width = source.getWidth();
    int height = source.getHeight();

    Bitmap result = mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
    if (result == null) {
      result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    Drawable mask = Util.getMaskDrawable(mContext, mMaskId);

    Canvas canvas = new Canvas(result);
    mask.setBounds(0, 0, width, height);
    mask.draw(canvas);
    canvas.drawBitmap(source, 0, 0, mMaskingPaint);

    return BitmapResource.obtain(result, mBitmapPool);
  }

  @Override public String getId() {
    return "MaskTransformation(maskId="
            + mContext.getResources().getResourceEntryName(mMaskId) + ")";
  }
}
