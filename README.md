With this library you can easily add authorization via social network in your application

Each social network and auth type provides in separate modules
There are 2 auth types: 
* Implicit - when you login inside your application and get token
* Explicit - when you login on your server and get server code for it

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
    implementation 'com.andrew.social.login:social-login-core:{latest-version}'
}
```

* Add module for any social network you want to include:

```gradle
dependencies {
    implementation 'com.andrew.social.login:{social-name}-{auth-type}:{latest-version}'
}
```

## Progress

1) - [X] VKontakte
2) - [X] Twitter
3) - [X] Instagram
4) - [X] Facebook
5) - [X] Google
6) - [X] LinkedIn
7) - [X] Github
8) - [ ] Amazon
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

Other
- [ ] Populate Wiki with info about how to connect each social to your project
- [X] Make each social login module as an independent library (based on core library)
