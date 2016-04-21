Change Log
==========

Version 2.0.1 *(2016-04-21)*
----------------------------

Fix:
 [#35](https://github.com/wasabeef/glide-transformations/issues/35)
 RSInvalidStateException

Version 2.0.0 *(2016-03-02)*
----------------------------

Say v8.RenderScript goodbye

Version 1.4.0 *(2016-02-28)*
----------------------------

fix Issue [#29](https://github.com/wasabeef/glide-transformations/issues/29)
 Use FastBlur as a fallback upon RenderScript failure.

Version 1.3.1 *(2015-11-27)*
----------------------------

Change the renderscriptTargetApi down to 20.  
 Warning:Renderscript support mode is not currently supported with renderscript target 21+

fix: memory leak.

Version 1.3.0 *(2015-11-10)*
----------------------------

add round corner type to RoundedCornersTransformation.  
Thanks to [kaelaela](https://github.com/kaelaela)

Version 1.2.1 *(2015-09-18)*
----------------------------

Merged NinePatchMaskTransformation to MaskTransformation.

Version 1.2.0 *(2015-09-16)*
----------------------------

add new transformations MaskTransformation and NinePatchMaskTransformation.  
Thanks to [start141](https://github.com/start141)

Version 1.1.0 *(2015-09-05)*
----------------------------

Adjustment of default parameters.

Version 1.0.8 *(2015-07-24)*
----------------------------

add DownSampling to BlurTransform

Version 1.0.7 *(2015-07-18)*
----------------------------

Bug fix : Blur not working.

Version 1.0.6 *(2015-04-23)*
----------------------------

add: CropType(Top, Center, Bottom) for CropTransformation.

Version 1.0.5 *(2015-03-09)*
----------------------------

Bug fix: Transparency.

Version 1.0.4 *(2015-02-13)*
----------------------------

Bug fix : remove original bitmap resource recycling.

Version 1.0.3 *(2015-02-05)*
----------------------------

Bug fix

Version 1.0.2 *(2015-02-04)*
----------------------------

Refactor: use BimapPool

Version 1.0.1 *(2015-01-21)*
----------------------------

fix: Blur Transformation now woking at Android 4.3
add: GPUImage to Gradle dependency 

Version 1.0.0 *(2015-01-12)*
----------------------------

Initial release.
