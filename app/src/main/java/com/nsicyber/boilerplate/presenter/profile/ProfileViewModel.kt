package com.nsicyber.boilerplate.presenter.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.nsicyber.boilerplate.common.Resource
import com.nsicyber.boilerplate.domain.useCase.auth.GetCurrentUserUidUseCase
import com.nsicyber.boilerplate.domain.useCase.auth.SignoutUseCase
import com.nsicyber.boilerplate.domain.useCase.profile.AddToFavoriteUseCase
import com.nsicyber.boilerplate.domain.useCase.profile.GetUserFavoritesUseCase
import com.nsicyber.boilerplate.domain.useCase.profile.RemoveFromFavoriteUseCase
import com.nsicyber.boilerplate.presenter.base.BaseViewModel
import com.nsicyber.boilerplate.presenter.base.components.login
import com.nsicyber.boilerplate.presenter.base.components.navHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserFavoritesUseCase: GetUserFavoritesUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val signoutUseCase: SignoutUseCase,
    private val getUserUid: GetCurrentUserUidUseCase
) : BaseViewModel() {

    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    private val _userUid = mutableStateOf<String?>(null)

    init {
        saveUserUid()
    }



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


    private fun saveUserUid() {
        getUserUid().onEach { uid ->
            when (uid) {
                is Resource.Success -> {
                    _userUid.value = uid.data
                    setBusy(false)

                }

                is Resource.Error -> {
                    setErrorDialogState(true, uid.message)
                    setBusy(false)
                }

                is Resource.Loading -> {
                    setBusy(true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun getFavoriteCoins() {

        getUserFavoritesUseCase(_userUid.value).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = ProfileState(favCoinList = result.data)
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


    fun removeFromFavorites(coinId: String?) {
        removeFromFavoriteUseCase(_userUid.value!!,coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    setSuccessDialogState(true, "Coin you selected is removed from favorite")
                    getFavoriteCoins()
                }

                is Resource.Error -> {
                   setErrorDialogState(true,"Error when removing from favorite")
                    setBusy(false)
                }

                is Resource.Loading -> {
                    setBusy(true)
                }
            }
        }.launchIn(viewModelScope)

    }

}