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

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageSwirlFilter;

public class SwirlFilterTransformation implements Transformation<Bitmap> {

    private Context mContext;
    private BitmapPool mBitmapPool;

    private GPUImageSwirlFilter mFilter = new GPUImageSwirlFilter();
    private float mRadius;
    private float mAngle;
    private PointF mCenter;

    public SwirlFilterTransformation(Context context, BitmapPool pool) {
        mContext = context;
        mBitmapPool = pool;
    }

    /**
     * @param radius from 0.0 to 1.0, default 0.5
     * @param angle  minimum 0.0, default 1.0
     * @param center default (0.5, 0.5)
     */
    public SwirlFilterTransformation(Context context, BitmapPool pool,
            float radius, float angle, PointF center) {
        mContext = context;
        mBitmapPool = pool;
        mRadius = radius;
        mAngle = angle;
        mCenter = center;
        mFilter.setRadius(mRadius);
        mFilter.setAngle(mAngle);
        mFilter.setCenter(mCenter);
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
        if (mCenter == null) {
            mCenter = new PointF();
        }
        return "SwirlFilterTransformation(radius=" + mRadius +
                ",angle=" + mAngle + ",center=" + mCenter + ")";
    }
}
