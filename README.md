Glide Transformations
======================
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-glide--transformations-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1363)
[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/jp.wasabeef/glide-transformations/badge.svg)](https://search.maven.org/artifact/jp.wasabeef/glide-transformations)

An Android transformation library providing a variety of image transformations for [Glide](https://github.com/bumptech/glide).

Please feel free to use this.


#### Are you using Picasso or Fresco?
[Picasso Transformations](https://github.com/wasabeef/picasso-transformations)  
[Fresco Processors](https://github.com/wasabeef/fresco-processors)

# Demo

### Original Image
<img src="art/demo-org.jpg" width="30%">

### Transformations
<img src="art/demo.gif" width="50%">

# How do I use it?

## Step 1

#### Gradle
```groovy
repositories {
  mavenCentral()
}

dependencies {
  implementation 'jp.wasabeef:glide-transformations:4.3.0'
  // If you want to use the GPU Filters
  implementation 'jp.co.cyberagent.android:gpuimage:2.1.0'
}
```

## Step 2

Set Glide Transform.

```kotlin
Glide.with(this).load(R.drawable.demo)
  .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
  .into(imageView)
```

## Advanced Step 3

You can set a multiple transformations.

```kotlin
val multi = MultiTransformation<Bitmap>(
  BlurTransformation(25),
  RoundedCornersTransformation(128, 0, CornerType.BOTTOM))))
Glide.with(this).load(R.drawable.demo)
  .apply(RequestOptions.bitmapTransform(multi))
  .into(imageView))
```

## Transformations

### Crop
- `CropTransformation`
- `CropCircleTransformation`
- `CropCircleWithBorderTransformation`
- `CropSquareTransformation`
- `RoundedCornersTransformation`

### Color
- `ColorFilterTransformation`
- `GrayscaleTransformation`

### Blur
- `BlurTransformation`

### Mask
- `MaskTransformation`

### GPU Filter (use [GPUImage](https://github.com/CyberAgent/android-gpuimage))
**Will require add dependencies for GPUImage.**  

- `ToonFilterTransformation`
- `SepiaFilterTransformation`
- `ContrastFilterTransformation`
- `InvertFilterTransformation`
- `PixelationFilterTransformation`
- `SketchFilterTransformation`
- `SwirlFilterTransformation`
- `BrightnessFilterTransformation`
- `KuwaharaFilterTransformation`
- `VignetteFilterTransformation`


Applications using Glide Transformations
---

Please [ping](mailto:dadadada.chop@gmail.com) me or send a pull request if you would like to be added here.

Icon | Application
------------ | -------------
<img src="https://lh6.ggpht.com/6zKH_uQY1bxCwXL4DLo_uoFEOXdShi3BgmN6XRHlaJ-oA1svmq6y1PZkmO50nWQn2Lg=w300-rw" width="48" height="48" /> | [Ameba Ownd](https://play.google.com/store/apps/details?id=jp.co.cyberagent.madrid)
<img src="https://lh3.googleusercontent.com/ZOrekp-ho-ecWG1TyvuOs0LoB5M4QYWCCLS5lFbAHhp_SklSd06544ENG3uC97zGWes=w300-rw" width="48" height="48" /> | [AbemaTV](https://play.google.com/store/apps/details?id=tv.abema)
<img src="https://lh3.googleusercontent.com/eikxMAKKMU2O-rV-oUg04dGp-O4Aoje91kffDlnCB_3DCIPDzzADxdBOdPCW18Lmqw=w300-rw" width="48" height="48" /> | [TV Time](https://play.google.com/store/apps/details?id=com.tozelabs.tvshowtime)
<img src="https://play-lh.googleusercontent.com/sGt1rxE4naYmO_6sCquGLa5zHeh_3AZQGtBKhU8PGuXDa-MeUK25aSI-iZJlMDUVli9c=s300-rw" width="48" height="48" /> | [Christmas Radio](https://play.google.com/store/apps/details?id=nl.netwaves.christmasradio)



Developed By
-------
Daichi Furiya (Wasabeef) - <dadadada.chop@gmail.com>

<a href="https://twitter.com/wasabeef_jp">
<img alt="Follow me on Twitter"
src="https://raw.githubusercontent.com/wasabeef/art/master/twitter.png" width="75"/>
</a>

Contributions
-------

Any contributions are welcome!

Contributors
-------

* [start141](https://github.com/start141)
* [squeeish](https://github.com/squeeish)

Thanks
-------

* Inspired by `Picasso Transformations` in [TannerPerrien](https://github.com/TannerPerrien).

License
-------

    Copyright (C) 2020 Wasabeef

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
