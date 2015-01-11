package jp.wasabeef.example.glide;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;

import org.w3c.dom.Text;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
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
        Blur
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
                transformation = new RoundedCornersTransformation(mPool, 50, 0);
                break;
            case Blur:
                transformation = new BlurTransformation(mContext, mPool, 10);
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
