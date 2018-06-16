package com.andrew.socialactionssample.presentation.feature.base.presenter

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Andrew on 16.06.2018.
 */

abstract class BasePresenter<View> {

    protected var view: View? = null

    private var disposables: CompositeDisposable? = null

    protected fun addToDisposable(disposable: Disposable) {
        if (disposables == null) {
            disposables = CompositeDisposable()
        }
        disposables?.add(disposable)
    }

    fun onViewAttached(view: View) {
        this.view = view
    }

    fun onStop() {
        disposables?.clear()
    }

    fun onViewDetached() {
        view = null
    }
}