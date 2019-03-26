package com.andrew.socialactionssample.di.module

import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.initializer.Initializer
import com.andrew.social.login.core.initializer.SocialLoginInitializer
import com.andrew.social.login.facebook.implicit.FacebookInitializer
import com.andrew.social.login.twitter.implicit.TwitterInitializer
import com.andrew.social.login.vkontakte.implicit.VkontakteInitializer
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
    @SocialKey(SocialType.VKONTAKTE)
    fun provideVkInitializer(): Initializer = VkontakteInitializer()

    @Provides
    @IntoMap
    @SocialKey(SocialType.TWITTER)
    fun provideTwitterInitializer(): Initializer = TwitterInitializer()

    @Provides
    fun provideSocialLoginInitializer(socialsToInit: Map<SocialType, @JvmSuppressWildcards Initializer>): SocialLoginInitializer =
            SocialLoginInitializer(socialsToInit)
}