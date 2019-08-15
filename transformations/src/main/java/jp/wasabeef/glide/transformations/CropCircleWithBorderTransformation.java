package jp.wasabeef.glide.transformations;

/**
 * Copyright (C) 2017 Wasabeef
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

public class CropCircleWithBorderTransformation implements Transformation<Bitmap> {

    private BitmapPool mBitmapPool;
    private int borderWidth;
    private int borderColor;

    public CropCircleWithBorderTransformation(Context context, int borderWidth,
                                              @ColorInt int borderColor) {
        this(Glide.get(context).getBitmapPool());
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
    }

    public CropCircleWithBorderTransformation(BitmapPool pool) {
        this.mBitmapPool = pool;
    }

    @Override
    public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
        Bitmap source = resource.get();
        int size = Math.min(source.getWidth(), source.getHeight());

        int width = (source.getWidth() - size) / 2;
        int height = (source.getHeight() - size) / 2;

        Bitmap bitmap = mBitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader =
                new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        if (width != 0 || height != 0) {
            // source isn't square, move viewport to center
            Matrix matrix = new Matrix();
            matrix.setTranslate(-width, -height);
            shader.setLocalMatrix(matrix);
        }
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        // add border
        int dstBitmapWidth = bitmap.getWidth() + borderWidth * 2;
        Bitmap borderedBitmap =
                Bitmap.createBitmap(dstBitmapWidth, dstBitmapWidth, Bitmap.Config.ARGB_8888);
        Canvas canvas1 = new Canvas(borderedBitmap);
        canvas1.drawBitmap(bitmap, borderWidth, borderWidth, null);

        Paint paint1 = new Paint();
        paint1.setColor(borderColor);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(borderWidth);
        paint1.setAntiAlias(true);

        canvas1.drawCircle(
                canvas1.getWidth() / 2,
                canvas1.getWidth() / 2,
                canvas1.getWidth() / 2 - borderWidth / 2,
                paint1
        );

        bitmap.recycle();

        return BitmapResource.obtain(borderedBitmap, mBitmapPool);
    }

    @Override public String getId() {
        return "CropCircleWithBorderTransformation(" +
                "width=" + borderWidth + ", color=" + borderColor + ")";
    }
}