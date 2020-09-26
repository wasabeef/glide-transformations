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

import jp.co.cyberagent.android.gpuimage.filter.GPUImageSwirlFilter;

/**
 * Creates a swirl distortion on the image.
 */
public class SwirlFilterTransformation extends GPUFilterTransformation {

  private static final int VERSION = 1;
  private static final String ID =
    "jp.wasabeef.glide.transformations.gpu.SwirlFilterTransformation." + VERSION;

  private final float radius;
  private final float angle;
  private final PointF center;

  public SwirlFilterTransformation() {
    this(.5f, 1.0f, new PointF(0.5f, 0.5f));
  }

  /**
   * @param radius from 0.0 to 1.0, default 0.5
   * @param angle  minimum 0.0, default 1.0
   * @param center default (0.5, 0.5)
   */
  public SwirlFilterTransformation(float radius, float angle, PointF center) {
    super(new GPUImageSwirlFilter());
    this.radius = radius;
    this.angle = angle;
    this.center = center;
    GPUImageSwirlFilter filter = getFilter();
    filter.setRadius(this.radius);
    filter.setAngle(this.angle);
    filter.setCenter(this.center);
  }

  @Override
  public String toString() {
    return "SwirlFilterTransformation(radius=" + radius + ",angle=" + angle + ",center="
      + center.toString() + ")";
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof SwirlFilterTransformation &&
      ((SwirlFilterTransformation) o).radius == radius &&
      ((SwirlFilterTransformation) o).angle == radius &&
      ((SwirlFilterTransformation) o).center.equals(center.x, center.y);
  }

  @Override
  public int hashCode() {
    return ID.hashCode() + (int) (radius * 1000) + (int) (angle * 10) + center.hashCode();
  }

  @Override
  public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    messageDigest.update((ID + radius + angle + center.hashCode()).getBytes(CHARSET));
  }
}
