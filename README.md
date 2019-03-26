# Social Login
[![MIT license](https://img.shields.io/badge/License-MIT-blue.svg)](https://github.com/AndrewHeyO/SocialLogin#license)
[![GitHub version](https://badge.fury.io/gh/AndrewHeyO%2FSocialLogin.svg)](https://github.com/AndrewHeyO/SocialLogin)

With this library you can easily add authorization via social network in your application

Each social network and auth type provides in separate modules
There are 2 auth types: 
* Implicit - when you login inside your application and receive token
* Explicit - when you login on your server and receive server code for it

## Integration

* Add JCenter reference to your app-level build.gradle:

```gradle
buildscript {
    repositories {
        ...
        jcenter()
    }
    ...
}
```

* Add core module to your module-level build.gradle:

```gradle
dependencies {
    implementation 'com.andrew.social.login:core:{latest-version}'
}
```

* Add module for any social network you want to include:

```gradle
dependencies {
    implementation 'com.andrew.social.login:{social-name}-{auth-type}:{latest-version}'
}
```

## Usage

You can use this library with two ways:

1. If you have only one social network to integrate, you can use this social's Login Action
2. If you have two or more social networks, you can use [Social Login Manager](https://github.com/AndrewHeyO/SocialLogin/blob/master/social-login-core/src/main/java/com/andrew/social/login/core/manager/SocialLoginManager.kt) and map of [Social Login Actions](https://github.com/AndrewHeyO/SocialLogin/blob/master/social-login-core/src/main/java/com/andrew/social/login/core/action/SocialLoginAction.kt)

You need to define [Social Login Callback](https://github.com/AndrewHeyO/SocialLogin/blob/master/social-login-core/src/main/java/com/andrew/social/login/core/action/SocialLoginCallback.kt)

_Also if you use several modules that have own initializers, you can use [Social Login Initializer](https://github.com/AndrewHeyO/SocialLogin/blob/master/social-login-core/src/main/java/com/andrew/social/login/core/initializer/SocialLoginInitializer.kt)_

## Customize web login

Library provides the way to use your own Activity for web login.
To do that you need to inherite your Activity from [BaseWebViewLoginActivity](https://github.com/AndrewHeyO/SocialLogin/blob/master/social-login-core/src/main/java/com/andrew/social/login/core/web/WebViewLoginActivity.kt) and call [WebActivityStarter.setWebLoginActivity()](https://github.com/AndrewHeyO/SocialLogin/blob/master/social-login-core/src/main/java/com/andrew/social/login/core/web/WebActivityStarter.kt#L23) before you login

```kotlin
class CustomWebViewLoginActivity : BaseWebViewLoginActivity() {

    override fun layoutResId(): Int = R.layout.activity_custom_webview_login

    override fun webView(): WebView = webview

    override fun showLoading() {
        progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progress.visibility = View.INVISIBLE
    }
}
```

## Wiki

For more information check [Wiki](https://github.com/AndrewHeyO/SocialLogin/wiki)

## Progress

1) - [X] VK
2) - [X] Twitter
3) - [X] Instagram
4) - [X] Facebook
5) - [X] Google
6) - [X] LinkedIn
7) - [X] Github
8) - [X] Amazon
9) - [ ] Pinterest
10) - [ ] SoundCloud
11) - [ ] Odnoklassniki
12) - [ ] Tumblr
13) - [ ] Steam
14) - [ ] Windows Live
15) - [ ] Reddit
16) - [ ] Twitch
17) - [ ] PayPal
18) - [ ] Mail.ru
19) - [ ] Yahoo!
20) - [ ] Wordpress

## License

```
MIT License

Copyright (c) 2019 Andrey Matyushin

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
