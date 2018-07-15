package com.andrew.socialactionssample.di.module

import android.support.v7.app.AppCompatActivity
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.action.SocialLoginManager
import com.andrew.social.login.facebook.FacebookLoginActionImpl
import com.andrew.social.login.google.GoogleLoginActionImpl
import com.andrew.social.login.instagram.InstagramLoginActionImpl
import com.andrew.social.login.twitter.TwitterLoginActionImpl
import com.andrew.social.login.vkontakte.VkontakteLoginActionImpl
import com.andrew.socialactionssample.R
import com.andrew.socialactionssample.di.qualifier.SocialKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

/**
 * Created by Andrew on 16.06.2018.
 */

@Module
class SocialLoginModule {

    @Provides
    @IntoMap
    @SocialKey(SocialType.VKONTAKTE)
    fun provideVkSocialAction(activity: AppCompatActivity): SocialLoginAction = VkontakteLoginActionImpl(activity)

    @Provides
    @IntoMap
    @SocialKey(SocialType.TWITTER)
    fun provideTwitterSocialAction(activity: AppCompatActivity): SocialLoginAction = TwitterLoginActionImpl(activity)

    @Provides
    @IntoMap
    @SocialKey(SocialType.INSTAGRAM)
    fun provideInstagramSocialAction(activity: AppCompatActivity): SocialLoginAction = InstagramLoginActionImpl(activity, activity.getString(R.string.instagram_id),
            activity.getString(R.string.instagram_redirect_url))

    @Provides
    @IntoMap
    @SocialKey(SocialType.FACEBOOK)
    fun provideFacebookSocialAction(activity: AppCompatActivity): SocialLoginAction = FacebookLoginActionImpl(activity, arrayListOf("public_profile"))

    @Provides
    @IntoMap
    @SocialKey(SocialType.GOOGLE)
    fun provideGooglePlusSocialAction(activity: AppCompatActivity): SocialLoginAction = GoogleLoginActionImpl(activity)

    @Provides
    fun provideSocialLoginManager(socialLoginActions: Map<SocialType, @JvmSuppressWildcards SocialLoginAction>) =
            SocialLoginManager(socialLoginActions)
}