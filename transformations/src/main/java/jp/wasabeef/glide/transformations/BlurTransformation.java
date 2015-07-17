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

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v8.renderscript.Allocation;
import android.support.v8.renderscript.RenderScript;
import android.support.v8.renderscript.ScriptIntrinsicBlur;

public class BlurTransformation implements Transformation<Bitmap> {

    private static int MAX_RADIUS = 25;

    private Context mContext;
    private BitmapPool mBitmapPool;

    private int mRadius;

    public BlurTransformation(Context context, BitmapPool pool) {
        this(context, pool, MAX_RADIUS);
    }

    public BlurTransformation(Context context, BitmapPool pool, int radius) {
        mContext = context;
        mBitmapPool = pool;
        mRadius = radius;
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
        canvas.drawBitmap(source, 0, 0, null);

        RenderScript rs = RenderScript.create(mContext);
        Allocation overlayAlloc = Allocation.createFromBitmap(rs, bitmap);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(mRadius);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(bitmap);

        rs.destroy();

        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override
    public String getId() {
        return "BlurTransformation(radius=" + mRadius + ")";
    }
}
