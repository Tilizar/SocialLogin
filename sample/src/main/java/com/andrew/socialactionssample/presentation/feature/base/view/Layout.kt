package com.andrew.socialactionssample.presentation.feature.base.view

import androidx.annotation.LayoutRes

/**
 * Created by Andrew on 16.06.2018.
 */

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Layout(@LayoutRes val layout: Int)