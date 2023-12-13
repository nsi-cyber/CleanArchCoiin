package com.nsicyber.boilerplate.presenter.discover

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.nsicyber.boilerplate.common.Resource
import com.nsicyber.boilerplate.domain.useCase.auth.SignoutUseCase
import com.nsicyber.boilerplate.domain.useCase.coin.GetCoinListUseCase
import com.nsicyber.boilerplate.presenter.base.BaseViewModel
import com.nsicyber.boilerplate.presenter.base.components.login
import com.nsicyber.boilerplate.presenter.base.components.navHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val getCoinListUseCase: GetCoinListUseCase,
    private val signoutUseCase: SignoutUseCase
) : BaseViewModel() {

    private val _state = mutableStateOf(DiscoverState())
    val state: State<DiscoverState> = _state



     fun signout() {
         signoutUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    navHostController!!.navigate(login)
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


    fun getCoinList() {
        getCoinListUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = DiscoverState(coinList = result.data)
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
