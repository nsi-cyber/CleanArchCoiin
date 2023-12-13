package com.nsicyber.boilerplate.presenter.signup

import androidx.lifecycle.viewModelScope
import com.nsicyber.boilerplate.common.Resource
import com.nsicyber.boilerplate.common.isEmailValid
import com.nsicyber.boilerplate.domain.useCase.auth.SignupUseCase
import com.nsicyber.boilerplate.presenter.base.BaseViewModel
import com.nsicyber.boilerplate.presenter.base.components.navHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase
) : BaseViewModel() {


    fun createUser(email: String, password1: String,password2:String) {
        var errorText = ""
        if (!email.isEmailValid()) {
            errorText += "Enter correct mail address \n"
        }
        if (password1.isEmpty()) {
            errorText += "Please enter password \n"
        }
        if (password1 !=password2) {
            errorText += "Please enter same password \n"
        }

        if (errorText.length > 0)
            setErrorDialogState(true, errorText)
        else
            signup(email, password1)
    }


    private fun signup(email: String, password: String) {
        signupUseCase(email, password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    setSuccessDialogState(true, "Account sucesfully registered")
                    navHostController!!.popBackStack()
                    setBusy(false)
                }

                is Resource.Error -> {
                    setErrorDialogState(true, result.message)
                    setBusy(false)

                }

                is Resource.Loading -> {
                    setBusy(true)
                }
            }
        }.launchIn(viewModelScope)
    }

}
