Change Log
==========

Version 4.0.1 *(2018-11-20)*
----------------------------

Add:
- consumerProguardFiles setting

Version 4.0.0 *(2018-11-16)*
----------------------------

Update:
- Migrate to AndroidX
- Remove novoda-bintray-plugin

Version 3.3.0 *(2018-04-23)*
----------------------------

Update:  
- Support Library 27.1.0 -> 27.1.1  
- Glide 4.6.1 -> 4.7.1  

Feature:  
- SupportRSBlurTransformation [#125](https://github.com/wasabeef/glide-transformations/pull/125)   
  it's using RenderScript support mode but can you choose to use or not.   
  Thank you [@fougere-mike](https://github.com/fougere-mike)  

Bug Fix: 
- Set detail of Cache key [#119](https://github.com/wasabeef/glide-transformations/issues/119)     

Version 3.2.0 *(2018-04-10)*
----------------------------

Update:  
- Support Library 27.0.2 -> 27.1.0  

Bug Fix:  
- We quit use the RenderScript [#120](https://github.com/wasabeef/glide-transformations/issues/120)     

Version 3.1.1 *(2018-02-13)*
----------------------------

Update:  
- Glide 4.5.0 -> 4.6.1  

Bug Fix:  
- Fix settings fot proguard.

Version 3.1.0 *(2018-01-26)*
----------------------------

Update:  
- Compile & Target SDK Version 25 -> 27  
- Build Tools 26.0.1 -> 27.0.3  
- Support Library 25.3.1 -> 27.0.2  
- Glide 4.0.0 -> 4.5.0  

Bug Fix:  
- [Implement equals() and hashCode() methods #105](https://github.com/wasabeef/glide-transformations/pull/105)  
- Use RenderScript#releaseAllContexts  

Version 3.0.1 *(2017-09-08)*
----------------------------

Bug Fix:
- [Deleted a setting for DexGuard #86](https://github.com/wasabeef/glide-transformations/issues/86)

Version 3.0.0 *(2017-09-06)*
----------------------------

Update:
- Build Tools 25.0.2 -> 26.0.1
- Min SDK Version 11 -> 14
- Glide 3.7.0 -> 4.0.0

Feature: 
- Supported Glide 4.0.0

Version 2.0.2 *(2017-03-17)*
----------------------------

Update:
- Compile & Target SDK Version 23 -> 25
- Build Tools 23.0.2 -> 25.0.2
- Support Library 23.2.1 -> 25.3.0
- GPUImage for Android 1.3.0 -> 1.4.1

Bug Fix:
- [Additional resource cleanup in RSBlur #45](https://github.com/wasabeef/glide-transformations/pull/45)
 
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
