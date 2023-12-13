package com.nsicyber.boilerplate.presenter.login




import androidx.lifecycle.viewModelScope
import com.nsicyber.boilerplate.common.Resource
import com.nsicyber.boilerplate.common.isEmailValid
import com.nsicyber.boilerplate.domain.useCase.auth.LoginUseCase
import com.nsicyber.boilerplate.presenter.base.BaseViewModel
import com.nsicyber.boilerplate.presenter.base.components.discover
import com.nsicyber.boilerplate.presenter.base.components.navHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {




    fun loginUser(email: String, password: String) {
        var errorText = ""
        if (!email.isEmailValid()) {
            errorText += "Enter correct mail address \n"
        }
        if (password.isEmpty()) {
            errorText += "Please enter password \n"
        }

        if (errorText.length > 0)
            setErrorDialogState(true, errorText)
        else
            login(email, password)
    }



   private fun login(username:String,password:String) {
        loginUseCase(username,password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    navHostController!!.navigate(discover)
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
