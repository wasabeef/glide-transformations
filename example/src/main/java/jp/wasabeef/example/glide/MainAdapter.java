package jp.wasabeef.example.glide;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.InvertFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.KuwaharaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ToonFilterTransformation;

/**
 * Created by Wasabeef on 2015/01/11.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context mContext;
    private List<Type> mDataSet;
    private BitmapPool mPool;

    enum Type {
        Crop,
        CropSquare,
        CropCircle,
        ColorFilter,
        Grayscale,
        RoundedCorners,
        Blur,
        Toon,
        Sepia,
        Contrast,
        Invert,
        Pixel,
        Sketch,
        Swirl,
        Brightness,
        Kuawahara
    }

    public MainAdapter(Context context, List<Type> dataSet) {
        mContext = context;
        mDataSet = dataSet;
        mPool = Glide.get(mContext).getBitmapPool();
    }

    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
        Transformation<Bitmap> transformation = null;
        switch (mDataSet.get(position)) {
            case Crop:
                transformation = new CropTransformation(mPool, 300, 100);
                break;
            case CropSquare:
                transformation = new CropSquareTransformation(mPool);
                break;
            case CropCircle:
                transformation = new CropCircleTransformation(mPool);
                break;
            case ColorFilter:
                transformation = new ColorFilterTransformation(mPool, Color.argb(80, 255, 0, 0));
                break;
            case Grayscale:
                transformation = new GrayscaleTransformation(mPool);
                break;
            case RoundedCorners:
                transformation = new RoundedCornersTransformation(mPool, 100, 0);
                break;
            case Blur:
                transformation = new BlurTransformation(mContext, mPool, 10);
                break;
            case Toon:
                transformation = new ToonFilterTransformation(mContext, mPool);
                break;
            case Sepia:
                transformation = new SepiaFilterTransformation(mContext, mPool);
                break;
            case Contrast:
                transformation = new ContrastFilterTransformation(mContext, mPool, 2.0f);
                break;
            case Invert:
                transformation = new InvertFilterTransformation(mContext, mPool);
                break;
            case Pixel:
                transformation = new PixelationFilterTransformation(mContext, mPool, 20);
                break;
            case Sketch:
                transformation = new SketchFilterTransformation(mContext, mPool);
                break;
            case Swirl:
                transformation = new SwirlFilterTransformation(mContext, mPool,
                        0.5f, 1.0f, new PointF(0.5f, 0.5f));
                break;
            case Brightness:
                transformation = new BrightnessFilterTransformation(mContext, mPool, 0.5f);
                break;
            case Kuawahara:
                transformation = new KuwaharaFilterTransformation(mContext, mPool, 25);
                break;
        }

        Glide.with(mContext).load(R.drawable.demo)
                .bitmapTransform(transformation).into(holder.image);
        holder.title.setText(mDataSet.get(position).name());
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView title;

        ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
