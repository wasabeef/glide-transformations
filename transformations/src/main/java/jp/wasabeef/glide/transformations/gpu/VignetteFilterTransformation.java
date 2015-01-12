package jp.wasabeef.glide.transformations.gpu;

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
import android.graphics.PointF;

import java.util.Arrays;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageVignetteFilter;

/**
 * Performs a vignetting effect, fading out the image at the edges
 * x:
 * y: The directional intensity of the vignetting, with a default of x = 0.75, y = 0.5
 */
public class VignetteFilterTransformation implements Transformation<Bitmap> {

    private Context mContext;
    private BitmapPool mBitmapPool;

    private GPUImageVignetteFilter mFilter = new GPUImageVignetteFilter();
    private PointF mCenter;
    private float[] mVignetteColor;
    private float mVignetteStart;
    private float mVignetteEnd;


    public VignetteFilterTransformation(Context context, BitmapPool pool) {
        mContext = context;
        mBitmapPool = pool;
        mCenter = new PointF();
    }

    public VignetteFilterTransformation(Context context, BitmapPool pool,
            PointF center, float[] color, float start, float end) {
        mContext = context;
        mBitmapPool = pool;
        mCenter = center;
        mVignetteColor = color;
        mVignetteStart = start;
        mVignetteEnd = end;
        mFilter.setVignetteCenter(mCenter);
        mFilter.setVignetteColor(mVignetteColor);
        mFilter.setVignetteStart(mVignetteStart);
        mFilter.setVignetteEnd(mVignetteEnd);
    }

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();

        GPUImage gpuImage = new GPUImage(mContext);
        gpuImage.setImage(source);
        gpuImage.setFilter(mFilter);
        Bitmap bitmap = gpuImage.getBitmapWithFilterApplied();

        source.recycle();

        return BitmapResource.obtain(bitmap, mBitmapPool);
    }

    @Override
    public String getId() {
        return "VignetteFilterTransformation(center=" + mCenter.toString() +
                ",color=" + Arrays.toString(mVignetteColor) +
                ",start=" + mVignetteStart + ",end=" + mVignetteEnd + ")";
    }
}
