package com.andrew.socialactionssample.di.module

import android.support.v7.app.AppCompatActivity
import com.andrew.socialactionssample.R
import com.andrew.socialactionssample.data.social.SocialLoginManager
import com.andrew.socialactionssample.data.social.SocialType
import com.andrew.socialactionssample.data.social.base.SocialLoginAction
import com.andrew.socialactionssample.data.social.instagram.InstagramLoginActionImpl
import com.andrew.socialactionssample.data.social.twitter.TwitterLoginActionImpl
import com.andrew.socialactionssample.data.social.vk.VkLoginActionImpl
import com.andrew.socialactionssample.di.qualifier.SocialKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

/**
 * Created by Andrew on 16.06.2018.
 */

@Module
class SocialModule {

    @Provides
    @IntoMap
    @SocialKey(SocialType.VK)
    fun provideVkSocialAction(activity: AppCompatActivity): SocialLoginAction = VkLoginActionImpl(activity)

    @Provides
    @IntoMap
    @SocialKey(SocialType.TWITTER)
    fun provideTwitterSocialAction(activity: AppCompatActivity): SocialLoginAction = TwitterLoginActionImpl(activity)

    @Provides
    @IntoMap
    @SocialKey(SocialType.INSTAGRAM)
    fun provideInstagramSocialAction(activity: AppCompatActivity): SocialLoginAction
            = InstagramLoginActionImpl(activity, activity.getString(R.string.instagram_id), activity.getString(R.string.instagram_redirect_url))

    @Provides
    fun provideSocialLoginManager(socialLoginActions: Map<SocialType, @JvmSuppressWildcards SocialLoginAction>) =
            SocialLoginManager(socialLoginActions)
}