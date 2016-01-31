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
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import java.security.MessageDigest;

public class GrayscaleTransformation implements Transformation<Bitmap> {

  private BitmapPool mBitmapPool;

  public GrayscaleTransformation(Context context) {
    this(Glide.get(context).getBitmapPool());
  }

  public GrayscaleTransformation(BitmapPool pool) {
    mBitmapPool = pool;
  }

  @Override
  public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
    Bitmap source = resource.get();

    int width = source.getWidth();
    int height = source.getHeight();

    Bitmap.Config config =
        source.getConfig() != null ? source.getConfig() : Bitmap.Config.ARGB_8888;
    Bitmap bitmap = mBitmapPool.get(width, height, config);
    if (bitmap == null) {
      bitmap = Bitmap.createBitmap(width, height, config);
    }

    Canvas canvas = new Canvas(bitmap);
    ColorMatrix saturation = new ColorMatrix();
    saturation.setSaturation(0f);
    Paint paint = new Paint();
    paint.setColorFilter(new ColorMatrixColorFilter(saturation));
    canvas.drawBitmap(source, 0, 0, paint);

    return BitmapResource.obtain(bitmap, mBitmapPool);
  }

  public String getId() {
    return "GrayscaleTransformation()";
  }

  @Override
  public void updateDiskCacheKey(MessageDigest messageDigest) {
    messageDigest.update(getId().getBytes());
  }
}
