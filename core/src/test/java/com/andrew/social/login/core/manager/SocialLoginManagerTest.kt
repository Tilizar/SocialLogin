package com.andrew.social.login.core.manager

import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.action.SocialLoginCallback
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Andrey Matyushin on 25.08.2018
 */

@RunWith(MockitoJUnitRunner::class)
class SocialLoginManagerTest {

    private val socialLoginAction = mock(SocialLoginAction::class.java)

    private val actions: Map<SocialType, SocialLoginAction> = mapOf(
            SocialType.FACEBOOK to socialLoginAction,
            SocialType.TWITTER to socialLoginAction,
            SocialType.GOOGLE to socialLoginAction
    )

    private val socialLoginManager: SocialLoginManager = SocialLoginManager(actions)

    @Test
    fun observeLoginCallback() {
        val loginCallback = mock(SocialLoginCallback::class.java)

        socialLoginManager.observeLoginCallback(loginCallback)

        actions.forEach { assertEquals(loginCallback, it.value.callback) }
    }

    @Test
    fun disposeLoginCallback() {

        socialLoginManager.disposeLoginCallback()

        verify(socialLoginAction, times(actions.size))
                .cancelRequest()

        actions.forEach { assertEquals(null, it.value.callback) }
    }

    @Test
    fun login_haveAction() {
        socialLoginManager.login(SocialType.FACEBOOK)

        verify(socialLoginAction)
                .login()
    }

    @Test
    fun login_noAction() {
        socialLoginManager.login(SocialType.INSTAGRAM)

        verifyZeroInteractions(socialLoginAction)
    }

    @Test
    fun handleResult() {
        val requestCode = 123
        val resultCode = 321

        socialLoginManager.handleResult(requestCode, resultCode, null)

        verify(socialLoginAction, times(actions.size))
                .handleResult(eq(requestCode), eq(resultCode), isNull())
    }
}