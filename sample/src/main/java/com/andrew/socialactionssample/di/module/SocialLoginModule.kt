package com.andrew.socialactionssample.di.module

import android.app.Activity
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.manager.SocialLoginManager
import com.andrew.social.login.facebook.explicit.FacebookLoginActionImpl
import com.andrew.social.login.github.explicit.GithubLoginActionImpl
import com.andrew.social.login.google.explicit.GoogleLoginActionImpl
import com.andrew.social.login.instagram.implicit.InstagramLoginActionImpl
import com.andrew.social.login.linkedin.explicit.LinkedInLoginActionImpl
import com.andrew.social.login.twitter.implicit.TwitterLoginActionImpl
import com.andrew.social.login.vkontakte.implicit.VkontakteLoginActionImpl
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
    //fun provideVkSocialAction(activity: Activity): SocialLoginAction = VkontakteLoginActionImpl(activity, activity.getString(R.string.com_vk_sdk_AppId), "http://placeholder.com")
    fun provideVkSocialAction(activity: Activity): SocialLoginAction = VkontakteLoginActionImpl(activity)

    @Provides
    @IntoMap
    @SocialKey(SocialType.TWITTER)
    fun provideTwitterSocialAction(activity: Activity): SocialLoginAction = TwitterLoginActionImpl(activity)

    @Provides
    @IntoMap
    @SocialKey(SocialType.INSTAGRAM)
    fun provideInstagramSocialAction(activity: Activity): SocialLoginAction = InstagramLoginActionImpl(activity, activity.getString(R.string.instagram_id), activity.getString(R.string.instagram_redirect_url))

    @Provides
    @IntoMap
    @SocialKey(SocialType.FACEBOOK)
    fun provideFacebookSocialAction(activity: Activity): SocialLoginAction = FacebookLoginActionImpl(activity, activity.getString(R.string.facebook_app_id), activity.getString(R.string.facebook_redirect_url))
    //fun provideFacebookSocialAction(activity: Activity): SocialLoginAction = FacebookLoginActionImpl(activity)

    @Provides
    @IntoMap
    @SocialKey(SocialType.GOOGLE)
    fun provideGooglePlusSocialAction(activity: Activity): SocialLoginAction = GoogleLoginActionImpl(activity, activity.getString(R.string.google_client_id))

    @Provides
    @IntoMap
    @SocialKey(SocialType.LINKED_IN)
    fun provideLinkedInSocialAction(activity: Activity): SocialLoginAction = LinkedInLoginActionImpl(activity, activity.getString(R.string.linked_in_client_id), activity.getString(R.string.linked_in_redirect_url))

    @Provides
    @IntoMap
    @SocialKey(SocialType.GITHUB)
    fun provideGithubSocialAction(activity: Activity): SocialLoginAction = GithubLoginActionImpl(activity, activity.getString(R.string.github_client_id), activity.getString(R.string.github_redirect_url))

    @Provides
    fun provideSocialLoginManager(socialLoginActions: Map<SocialType, @JvmSuppressWildcards SocialLoginAction>): SocialLoginManager =
            SocialLoginManager(socialLoginActions)
}