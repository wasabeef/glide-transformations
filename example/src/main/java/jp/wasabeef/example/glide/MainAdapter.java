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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import java.util.List;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;
import jp.wasabeef.glide.transformations.CropTransformation;
import jp.wasabeef.glide.transformations.GrayscaleTransformation;
import jp.wasabeef.glide.transformations.MaskTransformation;
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
    switch (mDataSet.get(position)) {
      case Mask: {
        int width = Utils.dip2px(mContext, 133.33f);
        int height = Utils.dip2px(mContext, 126.33f);
        Glide.with(mContext)
            .load(R.drawable.check)
            .override(width, height)
            .bitmapTransform(new CenterCrop(mContext),
                new MaskTransformation(mContext, R.drawable.mask_starfish))
            .into(holder.image);
        break;
      }
      case NinePatchMask: {
        int width = Utils.dip2px(mContext, 150.0f);
        int height = Utils.dip2px(mContext, 100.0f);
        Glide.with(mContext)
            .load(R.drawable.check)
            .override(width, height)
            .bitmapTransform(new CenterCrop(mContext),
                new MaskTransformation(mContext, R.drawable.mask_chat_right))
            .into(holder.image);
        break;
      }
      case CropTop:
        Glide.with(mContext)
            .load(R.drawable.demo)
            .bitmapTransform(
                new CropTransformation(mContext, 300, 100, CropTransformation.CropType.TOP))
            .into(holder.image);
        break;
      case CropCenter:
        Glide.with(mContext)
            .load(R.drawable.demo)
            .bitmapTransform(new CropTransformation(mContext, 300, 100))
            .into(holder.image);
        break;
      case CropBottom:
        Glide.with(mContext)
            .load(R.drawable.demo)
            .bitmapTransform(
                new CropTransformation(mContext, 300, 100, CropTransformation.CropType.BOTTOM))
            .into(holder.image);

        break;
      case CropSquare:
        Glide.with(mContext)
            .load(R.drawable.demo)
            .bitmapTransform(new CropSquareTransformation(mContext))
            .into(holder.image);
        break;
      case CropCircle:
        Glide.with(mContext)
            .load(R.drawable.demo)
            .bitmapTransform(new CropCircleTransformation(mContext))
            .into(holder.image);
        break;
      case ColorFilter:
        Glide.with(mContext)
            .load(R.drawable.demo)
            .bitmapTransform(new ColorFilterTransformation(mContext, Color.argb(80, 255, 0, 0)))
            .into(holder.image);
        break;
      case Grayscale:
        Glide.with(mContext)
            .load(R.drawable.demo)
            .bitmapTransform(new GrayscaleTransformation(mContext))
            .into(holder.image);
        break;
      case RoundedCorners:
        Glide.with(mContext)
            .load(R.drawable.demo)
            .bitmapTransform(new RoundedCornersTransformation(mContext, 30, 0,
                RoundedCornersTransformation.CornerType.BOTTOM))
            .into(holder.image);
        break;
      case Blur:
        Glide.with(mContext)
            .load(R.drawable.check)
            .bitmapTransform(new BlurTransformation(mContext, 25))
            .into(holder.image);
        break;
      case Toon:
        Glide.with(mContext)
            .load(R.drawable.demo)
            .bitmapTransform(new ToonFilterTransformation(mContext))
            .into(holder.image);
        break;
      case Sepia:
        Glide.with(mContext)
            .load(R.drawable.check)
            .bitmapTransform(new SepiaFilterTransformation(mContext))
            .into(holder.image);
        break;
      case Contrast:
        Glide.with(mContext)
            .load(R.drawable.check)
            .bitmapTransform(new ContrastFilterTransformation(mContext, 2.0f))
            .into(holder.image);
        break;
      case Invert:
        Glide.with(mContext)
            .load(R.drawable.check)
            .bitmapTransform(new InvertFilterTransformation(mContext))
            .into(holder.image);
        break;
      case Pixel:
        Glide.with(mContext)
            .load(R.drawable.check)
            .bitmapTransform(new PixelationFilterTransformation(mContext, 20))
            .into(holder.image);
        break;
      case Sketch:
        Glide.with(mContext)
            .load(R.drawable.check)
            .bitmapTransform(new SketchFilterTransformation(mContext))
            .into(holder.image);
        break;
      case Swirl:
        Glide.with(mContext)
            .load(R.drawable.check)
            .bitmapTransform(
                new SwirlFilterTransformation(mContext, 0.5f, 1.0f, new PointF(0.5f, 0.5f)))
            .into(holder.image);
        break;
      case Brightness:
        Glide.with(mContext)
            .load(R.drawable.check)
            .bitmapTransform(new BrightnessFilterTransformation(mContext, 0.5f))
            .into(holder.image);
        break;
      case Kuawahara:
        Glide.with(mContext)
            .load(R.drawable.check)
            .bitmapTransform(new KuwaharaFilterTransformation(mContext, 25))
            .into(holder.image);
        break;
      case Vignette:
        Glide.with(mContext)
            .load(R.drawable.check)
            .bitmapTransform(new VignetteFilterTransformation(mContext, new PointF(0.5f, 0.5f),
                new float[] { 0.0f, 0.0f, 0.0f }, 0f, 0.75f))
            .into(holder.image);
        break;
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
