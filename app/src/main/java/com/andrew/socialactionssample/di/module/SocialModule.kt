package com.andrew.socialactionssample.di.module

import android.support.v7.app.AppCompatActivity
import com.andrew.socialactionssample.data.social.SocialLoginManager
import com.andrew.socialactionssample.data.social.base.SocialLoginAction
import com.andrew.socialactionssample.data.social.qualifier.SocialKey
import com.andrew.socialactionssample.data.social.qualifier.SocialType
import com.andrew.socialactionssample.data.social.vk.VkLoginActionImpl
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
    fun provideSocialLoginManager(socialLoginActions: Map<SocialType, @JvmSuppressWildcards SocialLoginAction>) =
            SocialLoginManager(socialLoginActions)
}