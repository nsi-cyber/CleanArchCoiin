package com.nsicyber.boilerplate.presenter.splash

import android.os.Handler
import android.os.Looper
import com.nsicyber.boilerplate.presenter.base.BaseViewModel
import com.nsicyber.boilerplate.presenter.base.components.login
import com.nsicyber.boilerplate.presenter.base.components.navHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
) : BaseViewModel() {


    init {
        Handler(Looper.getMainLooper()).postDelayed({
            openLogin()
        }, 1000)
    }

    fun openLogin() {
        navHostController!!.navigate(login)
    }

}