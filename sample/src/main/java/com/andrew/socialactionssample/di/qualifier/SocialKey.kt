package com.andrew.socialactionssample.di.qualifier

import com.andrew.social.login.core.SocialType
import dagger.MapKey

/**
 * Created by Andrew on 16.06.2018.
 */

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SocialKey(val type: SocialType)