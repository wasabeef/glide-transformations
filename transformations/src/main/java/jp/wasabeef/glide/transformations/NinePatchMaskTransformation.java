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
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.request.target.Target;

public class NinePatchMaskTransformation implements Transformation<Bitmap> {

    private Context mContext;
    private BitmapPool mBitmapPool;
    private int mMaskId;
    private int mWidth;
    private int mHeight;

    public NinePatchMaskTransformation(Context context, int maskId, int width, int height) {
        mBitmapPool = Glide.get(context).getBitmapPool();
        mContext = context;
        mMaskId = maskId;
        mWidth = width;
        mHeight = height;
    }

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();

        int width = outWidth == Target.SIZE_ORIGINAL ? source.getWidth() : outWidth;
        int height = outHeight == Target.SIZE_ORIGINAL ? source.getHeight() : outHeight;

        boolean maskFromBitmapPool = true;
        Bitmap mask = mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        if (mask == null) {
            maskFromBitmapPool = false;
            mask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }

        Drawable drawable;
        if (Build.VERSION.SDK_INT >= 21) {
            drawable = mContext.getDrawable(mMaskId);
        } else {
            drawable = mContext.getResources().getDrawable(mMaskId);
        }
        drawable.setBounds(0, 0, width, height);
        Canvas canvas = new Canvas(mask);
        drawable.draw(canvas);

        Bitmap bitmap = mBitmapPool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        canvas = new Canvas(bitmap);
        canvas.drawBitmap(mask, new Rect(0, 0, mask.getWidth(), mask.getHeight()), new Rect(0, 0,
                source.getWidth(), source.getHeight()), null);
        canvas.drawBitmap(source, 0, 0, paint);

        if (maskFromBitmapPool) {
            mBitmapPool.put(mask);
        }

        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override
    public String getId() {
        return "NinePatchMaskTransformation(mMaskId=" + mMaskId
                + ", width=" + mWidth + ", height=" + mHeight + ")";
    }

}
