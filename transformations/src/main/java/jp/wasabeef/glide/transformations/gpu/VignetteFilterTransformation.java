package jp.wasabeef.glide.transformations.gpu;

/**
 * Copyright (C) 2020 Wasabeef
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.graphics.PointF;

import androidx.annotation.NonNull;

import java.security.MessageDigest;
import java.util.Arrays;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageVignetteFilter;

/**
 * Performs a vignetting effect, fading out the image at the edges
 * The directional intensity of the vignetting,
 * with a default of x = 0.5, y = 0.5, start = 0, end = 0.75
 */
public class VignetteFilterTransformation extends GPUFilterTransformation {

  private static final int VERSION = 1;
  private static final String ID =
    "jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation." + VERSION;

  private final PointF center;
  private final float[] vignetteColor;
  private final float vignetteStart;
  private final float vignetteEnd;

  public VignetteFilterTransformation() {
    this(new PointF(0.5f, 0.5f), new float[]{0.0f, 0.0f, 0.0f}, 0.0f, 0.75f);
  }

  public VignetteFilterTransformation(PointF center, float[] color, float start, float end) {
    super(new GPUImageVignetteFilter());
    this.center = center;
    vignetteColor = color;
    vignetteStart = start;
    vignetteEnd = end;
    GPUImageVignetteFilter filter = getFilter();
    filter.setVignetteCenter(this.center);
    filter.setVignetteColor(vignetteColor);
    filter.setVignetteStart(vignetteStart);
    filter.setVignetteEnd(vignetteEnd);
  }

  @Override
  public String toString() {
    return "VignetteFilterTransformation(center=" + center.toString() + ",color=" + Arrays.toString(
      vignetteColor) + ",start=" + vignetteStart + ",end=" + vignetteEnd + ")";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof VignetteFilterTransformation &&
      ((VignetteFilterTransformation) o).center.equals(center.x, center.y) &&
      Arrays.equals(((VignetteFilterTransformation) o).vignetteColor, vignetteColor) &&
      ((VignetteFilterTransformation) o).vignetteStart == vignetteStart &&
      ((VignetteFilterTransformation) o).vignetteEnd == vignetteEnd;
  }

  @Override
  public int hashCode() {
    return ID.hashCode() + center.hashCode() + Arrays.hashCode(vignetteColor) +
      (int) (vignetteStart * 100) + (int) (vignetteEnd * 10);
  }

  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    messageDigest.update((ID + center + Arrays.hashCode(vignetteColor) + vignetteStart + vignetteEnd).getBytes(CHARSET));
  }
}
