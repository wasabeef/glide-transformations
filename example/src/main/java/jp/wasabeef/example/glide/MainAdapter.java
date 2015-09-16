package jp.wasabeef.example.glide;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import java.util.List;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
import jp.wasabeef.glide.transformations.NinePatchMaskTransformation;
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
import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

/**
 * Created by Wasabeef on 2015/01/11.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

  private Context mContext;
  private List<Type> mDataSet;

  enum Type {
    Mask,
    NinePatchMask,
    CropTop,
    CropCenter,
    CropBottom,
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
    Kuawahara,
    Vignette
  }

  public MainAdapter(Context context, List<Type> dataSet) {
    mContext = context;
    mDataSet = dataSet;
  }

  @Override public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(mContext).inflate(R.layout.layout_list_item, parent, false);
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(MainAdapter.ViewHolder holder, int position) {
    Transformation transformation = null;
    switch (mDataSet.get(position)) {
      case Mask:
        transformation = new MaskTransformation(mContext, R.drawable.mask210);
        Glide.with(mContext)
            .load(R.drawable.demo)
            .override(210, 210)
            .bitmapTransform(new CenterCrop(mContext), transformation)
            .into(holder.image);
        break;
      case NinePatchMask:
        transformation = new NinePatchMaskTransformation(mContext, R.drawable.chat_me_mask);
        Glide.with(mContext)
            .load(R.drawable.demo)
            .override(300, 300)
            .bitmapTransform(new CenterCrop(mContext), transformation)
            .into(holder.image);
        break;
      case CropTop:
        transformation =
            new CropTransformation(mContext, 300, 100, CropTransformation.CropType.TOP);
        break;
      case CropCenter:
        transformation = new CropTransformation(mContext, 300, 100);
        break;
      case CropBottom:
        transformation =
            new CropTransformation(mContext, 300, 100, CropTransformation.CropType.BOTTOM);
        break;
      case CropSquare:
        transformation = new CropSquareTransformation(mContext);
        break;
      case CropCircle:
        transformation = new CropCircleTransformation(mContext);
        break;
      case ColorFilter:
        transformation = new ColorFilterTransformation(mContext, Color.argb(80, 255, 0, 0));
        break;
      case Grayscale:
        transformation = new GrayscaleTransformation(mContext);
        break;
      case RoundedCorners:
        transformation = new RoundedCornersTransformation(mContext, 100, 0);
        break;
      case Blur:
        transformation = new BlurTransformation(mContext, 25, 1);
        break;
      case Toon:
        transformation = new ToonFilterTransformation(mContext);
        break;
      case Sepia:
        transformation = new SepiaFilterTransformation(mContext);
        break;
      case Contrast:
        transformation = new ContrastFilterTransformation(mContext, 2.0f);
        break;
      case Invert:
        transformation = new InvertFilterTransformation(mContext);
        break;
      case Pixel:
        transformation = new PixelationFilterTransformation(mContext, 20);
        break;
      case Sketch:
        transformation = new SketchFilterTransformation(mContext);
        break;
      case Swirl:
        transformation =
            new SwirlFilterTransformation(mContext, 0.5f, 1.0f, new PointF(0.5f, 0.5f));
        break;
      case Brightness:
        transformation = new BrightnessFilterTransformation(mContext, 0.5f);
        break;
      case Kuawahara:
        transformation = new KuwaharaFilterTransformation(mContext, 25);
        break;
      case Vignette:
        transformation = new VignetteFilterTransformation(mContext, new PointF(0.5f, 0.5f),
            new float[] { 0.0f, 0.0f, 0.0f }, 0f, 0.75f);
        break;
    }

    if (mDataSet.get(position) != Type.Mask && mDataSet.get(position) != Type.NinePatchMask) {
      Glide.with(mContext).load(R.drawable.demo).bitmapTransform(transformation).into(holder.image);
    }
    holder.title.setText(mDataSet.get(position).name());
  }

  @Override public int getItemCount() {
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
