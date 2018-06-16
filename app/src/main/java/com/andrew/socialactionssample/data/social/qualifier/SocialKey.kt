package com.andrew.socialactionssample.data.social.qualifier

import dagger.MapKey

/**
 * Created by Andrew on 16.06.2018.
 */

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class SocialKey(val type: SocialType)

enum class SocialType {
    VK
}