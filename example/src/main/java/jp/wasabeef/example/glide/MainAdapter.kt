package jp.wasabeef.example.glide

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PointF
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.bumptech.glide.request.RequestOptions.overrideOf
import jp.wasabeef.example.glide.MainAdapter.Type.*
import jp.wasabeef.glide.transformations.*
import jp.wasabeef.glide.transformations.CropTransformation.CropType
import jp.wasabeef.glide.transformations.gpu.*
import jp.wasabeef.glide.transformations.internal.Utils

/**
 * Created by Wasabeef on 2015/01/11.
 */
class MainAdapter(
  private val context: Context,
  private val dataSet: MutableList<Type>
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

  enum class Type {
    Mask,
    NinePatchMask,
    CropTop,
    CropCenter,
    CropBottom,
    CropSquare,
    CropCircle,
    CropCircleWithBorder,
    ColorFilter,
    Grayscale,
    RoundedCorners,
    BlurLight,
    BlurDeep,
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

  override fun getItemCount(): Int {
    return dataSet.size
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val v = LayoutInflater.from(context).inflate(R.layout.layout_list_item, parent, false)
    return ViewHolder(v)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    when (dataSet[position]) {
      Mask -> {
        Glide.with(context)
          .load(R.drawable.check)
          .apply(overrideOf(266.px, 252.px))
          .apply(bitmapTransform(MultiTransformation<Bitmap>(CenterCrop(),
            MaskTransformation(R.drawable.mask_starfish))))
          .into(holder.image)
      }
      NinePatchMask -> {
        Glide.with(context)
          .load(R.drawable.check)
          .apply(overrideOf(300.px, 200.px))
          .apply(bitmapTransform(MultiTransformation<Bitmap>(CenterCrop(),
            MaskTransformation(R.drawable.mask_chat_right))))
          .into(holder.image)
      }

      CropTop -> Glide.with(context)
        .load(R.drawable.demo)
        .apply(bitmapTransform(CropTransformation(300.px, 100.px, CropType.TOP)))
        .into(holder.image)

      CropCenter -> Glide.with(context)
        .load(R.drawable.demo)
        .apply(bitmapTransform(CropTransformation(300.px, 100.px, CropType.CENTER)))
        .into(holder.image)

      CropBottom -> Glide.with(context)
        .load(R.drawable.demo)
        .apply(bitmapTransform(CropTransformation(300.px, 100.px, CropType.BOTTOM)))
        .into(holder.image)

      CropSquare -> Glide.with(context)
        .load(R.drawable.demo)
        .apply(bitmapTransform(CropSquareTransformation()))
        .into(holder.image)

      CropCircle -> Glide.with(context)
        .load(R.drawable.demo)
        .apply(bitmapTransform(CropCircleTransformation()))
        .into(holder.image)

      CropCircleWithBorder -> Glide.with(context)
        .load(R.drawable.demo)
        .apply(bitmapTransform(
          CropCircleWithBorderTransformation(Utils.toDp(4), Color.rgb(0, 145, 86))))
        .into(holder.image)

      ColorFilter -> Glide.with(context)
        .load(R.drawable.demo)
        .apply(bitmapTransform(ColorFilterTransformation(Color.argb(80, 255, 0, 0))))
        .into(holder.image)

      Grayscale -> Glide.with(context)
        .load(R.drawable.demo)
        .apply(bitmapTransform(GrayscaleTransformation()))
        .into(holder.image)

      RoundedCorners -> Glide.with(context)
        .load(R.drawable.demo)
        .apply(bitmapTransform(RoundedCornersTransformation(120, 0,
          RoundedCornersTransformation.CornerType.DIAGONAL_FROM_TOP_LEFT)))
        .into(holder.image)

      BlurLight -> Glide.with(context)
        .load(R.drawable.check)
        .apply(bitmapTransform(BlurTransformation(25)))
        .into(holder.image)

      BlurDeep -> Glide.with(context)
        .load(R.drawable.check)
        .apply(bitmapTransform(BlurTransformation(25, 8)))
        .into(holder.image)

      Toon -> Glide.with(context)
        .load(R.drawable.demo)
        .apply(bitmapTransform(ToonFilterTransformation()))
        .into(holder.image)

      Sepia -> Glide.with(context)
        .load(R.drawable.check)
        .apply(bitmapTransform(SepiaFilterTransformation()))
        .into(holder.image)

      Contrast -> Glide.with(context)
        .load(R.drawable.check)
        .apply(bitmapTransform(ContrastFilterTransformation(2.0f)))
        .into(holder.image)

      Invert -> Glide.with(context)
        .load(R.drawable.check)
        .apply(bitmapTransform(InvertFilterTransformation()))
        .into(holder.image)

      Pixel -> Glide.with(context)
        .load(R.drawable.check)
        .apply(bitmapTransform(PixelationFilterTransformation(20f)))
        .into(holder.image)

      Sketch -> Glide.with(context)
        .load(R.drawable.check)
        .apply(bitmapTransform(SketchFilterTransformation()))
        .into(holder.image)

      Swirl -> Glide.with(context)
        .load(R.drawable.check)
        .apply(bitmapTransform(
          SwirlFilterTransformation(0.5f, 1.0f, PointF(0.5f, 0.5f))).dontAnimate())
        .into(holder.image)

      Brightness -> Glide.with(context)
        .load(R.drawable.check)
        .apply(bitmapTransform(BrightnessFilterTransformation(0.5f)).dontAnimate())
        .into(holder.image)

      Kuawahara -> Glide.with(context)
        .load(R.drawable.check)
        .apply(bitmapTransform(KuwaharaFilterTransformation(25)).dontAnimate())
        .into(holder.image)

      Vignette -> Glide.with(context)
        .load(R.drawable.check)
        .apply(bitmapTransform(VignetteFilterTransformation(PointF(0.5f, 0.5f),
          floatArrayOf(0.0f, 0.0f, 0.0f), 0f, 0.75f)).dontAnimate())
        .into(holder.image)
    }
    holder.title.text = dataSet[position].name
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var image: ImageView = itemView.findViewById(R.id.image)
    var title: TextView = itemView.findViewById(R.id.title)
  }
}
