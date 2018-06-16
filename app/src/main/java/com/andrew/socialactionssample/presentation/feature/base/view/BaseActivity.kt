package com.andrew.socialactionssample.presentation.feature.base.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.andrew.socialactionssample.presentation.feature.base.presenter.BasePresenter
import dagger.android.AndroidInjection
import javax.inject.Inject

/**
 * Created by Andrew on 16.06.2018.
 */

abstract class BaseActivity<Presenter : BasePresenter<View>, View : BaseView> : AppCompatActivity(), BaseView {

    @Inject
    lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setupContentView()
    }

    override fun onStart() {
        super.onStart()
        presenter.onViewAttached(this as View)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
        presenter.onViewDetached()
    }

    private fun setupContentView() {
        val cls = this.javaClass
        if (!cls.isAnnotationPresent(Layout::class.java)) {
            throw IllegalArgumentException("Please specify LayoutRes for activity in @Layout annotation")
        }
        val annotation = cls.getAnnotation(Layout::class.java)
        setContentView(annotation.layout)
    }
}