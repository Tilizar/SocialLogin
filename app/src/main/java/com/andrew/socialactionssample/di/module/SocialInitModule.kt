package com.andrew.socialactionssample.di.module

import com.andrew.social.login.base.SocialType
import com.andrew.social.login.base.initializer.Initializer
import com.andrew.social.login.base.initializer.SocialLoginInitializer
import com.andrew.social.login.facebook.initializer.FacebookInitializer
import com.andrew.social.login.twitter.initializer.TwitterInitializer
import com.andrew.social.login.vk.initializer.VkInitializer
import com.andrew.socialactionssample.di.qualifier.SocialKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

/**
 * Created by Andrew on 01.07.2018
 */

@Module
class SocialInitModule {

    @Provides
    @IntoMap
    @SocialKey(SocialType.FACEBOOK)
    fun provideFacebookInitializer(): Initializer = FacebookInitializer()

    @Provides
    @IntoMap
    @SocialKey(SocialType.VK)
    fun provideVkInitializer(): Initializer = VkInitializer()

    @Provides
    @IntoMap
    @SocialKey(SocialType.TWITTER)
    fun provideTwitterInitializer(): Initializer = TwitterInitializer()

    @Provides
    fun provideSocialLoginInitializer(socialsToInit: Map<SocialType, @JvmSuppressWildcards Initializer>) =
            SocialLoginInitializer(socialsToInit)
}