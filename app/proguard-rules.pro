# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\wubin\AppData\Local\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontoptimize
-dontpreverify
#极光推送
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *; }
-dontwarn cn.jiguang.**
-keep class cn.jiguang.** { *; }
-dontwarn com.google.**
-keep class com.google.gson.** {*;}
-keep class com.google.protobuf.** {*;}

#百度地图
-keep class com.baidu.mapapi.** {*; }
-keep class com.baidu.** { *; }
-keep class vi.com.gdi.bgl.android.**{*;}

#环信
-keep class com.hyphenate.** {*;}
-dontwarn  com.hyphenate.**

#微信开放平台
-keep class com.tencent.mm.opensdk.** {*;}
-keep class com.tencent.wxop.** { *;}
-keep class com.tencent.mm.sdk.** {  *;}