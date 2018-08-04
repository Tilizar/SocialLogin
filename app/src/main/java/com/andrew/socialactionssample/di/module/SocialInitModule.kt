package com.andrew.socialactionssample.di.module

import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.initializer.Initializer
import com.andrew.social.login.core.initializer.SocialLoginInitializer
import com.andrew.social.login.twitter.TwitterInitializer
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
    @SocialKey(SocialType.TWITTER)
    fun provideTwitterInitializer(): Initializer = TwitterInitializer()

    @Provides
    fun provideSocialLoginInitializer(socialsToInit: Map<SocialType, @JvmSuppressWildcards Initializer>) =
            SocialLoginInitializer(socialsToInit)
}