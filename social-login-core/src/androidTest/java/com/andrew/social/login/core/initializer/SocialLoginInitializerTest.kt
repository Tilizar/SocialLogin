package com.andrew.social.login.core.initializer

import android.app.Application
import com.andrew.social.login.core.SocialType
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Andrey Matyushin on 25.08.2018
 */

@RunWith(MockitoJUnitRunner::class)
class SocialLoginInitializerTest {

    private val application: Application = mock(Application::class.java)

    private val initializer: Initializer = mock(Initializer::class.java)

    private val initializers = mapOf(
            SocialType.TWITTER to initializer,
            SocialType.FACEBOOK to initializer
    )

    private val socialLoginInitializer = SocialLoginInitializer(initializers)

    @Test
    fun init_haveInitializer() {
        socialLoginInitializer.init(SocialType.FACEBOOK, application)

        verify(initializer)
                .init(application)
    }

    @Test
    fun init_noInitializer() {
        socialLoginInitializer.init(SocialType.VKONTAKTE, application)

        verifyZeroInteractions(initializer)
    }

    @Test
    fun initAll() {
        socialLoginInitializer.initAll(application)

        verify(initializer, times(initializers.size))
                .init(application)
    }
}