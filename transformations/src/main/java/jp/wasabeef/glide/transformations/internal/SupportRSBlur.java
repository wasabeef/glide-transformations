package jp.wasabeef.glide.transformations.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.renderscript.Allocation;
import androidx.renderscript.Element;
import androidx.renderscript.RSRuntimeException;
import androidx.renderscript.RenderScript;
import androidx.renderscript.ScriptIntrinsicBlur;

/**
 * Copyright (C) 2018 Wasabeef
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

public class SupportRSBlur {

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
  public static Bitmap blur(Context context, Bitmap bitmap, int radius) throws RSRuntimeException {
    RenderScript rs = null;
    Allocation input = null;
    Allocation output = null;
    ScriptIntrinsicBlur blur = null;
    try {
      rs = RenderScript.create(context);
      rs.setMessageHandler(new RenderScript.RSMessageHandler());
      input = Allocation.createFromBitmap(rs, bitmap, Allocation.MipmapControl.MIPMAP_NONE,
          Allocation.USAGE_SCRIPT);
      output = Allocation.createTyped(rs, input.getType());
      blur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

      blur.setInput(input);
      blur.setRadius(radius);
      blur.forEach(output);
      output.copyTo(bitmap);
    } finally {
      if (rs != null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
          RenderScript.releaseAllContexts();
        } else {
          rs.destroy();
        }
      }
      if (input != null) {
        input.destroy();
      }
      if (output != null) {
        output.destroy();
      }
      if (blur != null) {
        blur.destroy();
      }
    }

    return bitmap;
  }
}
