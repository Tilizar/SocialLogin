package com.andrew.socialactionssample.presentation.feature.base.view

import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import dagger.android.AndroidInjection

/**
 * Created by Andrew on 16.06.2018.
 */

abstract class BaseActivity : MvpAppCompatActivity(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setupContentView()
    }

    private fun setupContentView() {
        val cls = this.javaClass
        if (!cls.isAnnotationPresent(Layout::class.java)) {
            throw IllegalArgumentException("Please specify LayoutRes for activity in @Layout annotation")
        }
        val annotation = cls.getAnnotation(Layout::class.java)!!
        setContentView(annotation.layout)
    }
}