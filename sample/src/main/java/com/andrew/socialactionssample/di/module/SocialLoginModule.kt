package com.andrew.socialactionssample.di.module

import androidx.fragment.app.FragmentActivity
import com.andrew.social.login.amazon.implicit.AmazonLoginActionImpl
import com.andrew.social.login.core.SocialType
import com.andrew.social.login.core.action.SocialLoginAction
import com.andrew.social.login.core.manager.SocialLoginManager
import com.andrew.social.login.facebook.implicit.FacebookLoginActionImpl
import com.andrew.social.login.github.explicit.GithubLoginActionImpl
import com.andrew.social.login.google.explicit.GoogleLoginActionImpl
import com.andrew.social.login.instagram.explicit.InstagramLoginActionImpl
import com.andrew.social.login.linkedin.explicit.LinkedInLoginActionImpl
import com.andrew.social.login.twitter.implicit.TwitterLoginActionImpl
import com.andrew.social.login.vk.implicit.VkLoginActionImpl
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
    @SocialKey(SocialType.VK)
    //fun provideVkSocialAction(activity: FragmentActivity): SocialLoginAction = VkLoginActionImpl(activity, activity.getString(R.string.vk_client_id), "http://placeholder.com")
    fun provideVkSocialAction(activity: FragmentActivity): SocialLoginAction = VkLoginActionImpl(activity)

    @Provides
    @IntoMap
    @SocialKey(SocialType.TWITTER)
    fun provideTwitterSocialAction(activity: FragmentActivity): SocialLoginAction = TwitterLoginActionImpl(activity)

    @Provides
    @IntoMap
    @SocialKey(SocialType.INSTAGRAM)
    fun provideInstagramSocialAction(activity: FragmentActivity): SocialLoginAction = InstagramLoginActionImpl(activity, activity.getString(R.string.instagram_id), activity.getString(R.string.instagram_redirect_url))

    @Provides
    @IntoMap
    @SocialKey(SocialType.FACEBOOK)
    //fun provideFacebookSocialAction(activity: FragmentActivity): SocialLoginAction = FacebookLoginActionImpl(activity, activity.getString(R.string.facebook_app_id), activity.getString(R.string.facebook_redirect_url))
    fun provideFacebookSocialAction(activity: FragmentActivity): SocialLoginAction = FacebookLoginActionImpl(activity)

    @Provides
    @IntoMap
    @SocialKey(SocialType.GOOGLE)
    fun provideGooglePlusSocialAction(activity: FragmentActivity): SocialLoginAction = GoogleLoginActionImpl(activity, activity.getString(R.string.google_client_id))

    @Provides
    @IntoMap
    @SocialKey(SocialType.LINKED_IN)
    fun provideLinkedInSocialAction(activity: FragmentActivity): SocialLoginAction = LinkedInLoginActionImpl(activity, activity.getString(R.string.linked_in_client_id), activity.getString(R.string.linked_in_redirect_url))

    @Provides
    @IntoMap
    @SocialKey(SocialType.GITHUB)
    fun provideGithubSocialAction(activity: FragmentActivity): SocialLoginAction = GithubLoginActionImpl(activity, activity.getString(R.string.github_client_id), activity.getString(R.string.github_redirect_url))

    @Provides
    @IntoMap
    @SocialKey(SocialType.AMAZON)
    //fun provideAmazonSocialAction(activity: FragmentActivity): SocialLoginAction = AmazonLoginActionImpl(activity, activity.getString(R.string.amazon_app_id), activity.getString(R.string.amazon_redirect_url))
    fun provideAmazonSocialAction(activity: FragmentActivity): SocialLoginAction = AmazonLoginActionImpl(activity)

    @Provides
    fun provideSocialLoginManager(socialLoginActions: Map<SocialType, @JvmSuppressWildcards SocialLoginAction>): SocialLoginManager =
            SocialLoginManager(socialLoginActions)
}