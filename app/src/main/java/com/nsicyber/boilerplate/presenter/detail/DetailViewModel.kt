package com.nsicyber.boilerplate.presenter.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.nsicyber.boilerplate.common.Resource
import com.nsicyber.boilerplate.data.remote.entity.CoinEntity
import com.nsicyber.boilerplate.domain.useCase.auth.GetCurrentUserUidUseCase
import com.nsicyber.boilerplate.domain.useCase.coin.GetCoinDetailUseCase
import com.nsicyber.boilerplate.domain.useCase.profile.AddToFavoriteUseCase
import com.nsicyber.boilerplate.domain.useCase.profile.GetUserFavoritesUseCase
import com.nsicyber.boilerplate.domain.useCase.profile.IsCoinFavorited
import com.nsicyber.boilerplate.domain.useCase.profile.RemoveFromFavoriteUseCase
import com.nsicyber.boilerplate.presenter.base.BaseViewModel
import com.nsicyber.boilerplate.service.BackgroundTrackerService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCoinDetailUseCase: GetCoinDetailUseCase,
    private val removeFromFavoriteUseCase: RemoveFromFavoriteUseCase,
    private val addToFavoriteUseCase: AddToFavoriteUseCase,
    private val getUserUid: GetCurrentUserUidUseCase,
    private val isCoinFavorited: IsCoinFavorited,
) : BaseViewModel() {

    private val _state = mutableStateOf(DetailState())
    val state: State<DetailState> = _state

    private val _userUid = mutableStateOf<String?>(null)

    init {
        saveUserUid()
    }

    private fun saveUserUid() {
        getUserUid().onEach { uid ->
            when (uid) {
                is Resource.Success -> {
                    _userUid.value = uid.data
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }


    fun getCoinDetail(coinId: String?) {
        getCoinDetailUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = DetailState(coinDetail = result.data)
                    isUserFavoritedCoin(coinId)
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



    private fun isUserFavoritedCoin(coinId: String?) {

        isCoinFavorited(_userUid.value!!,coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(isCoinFav = result.data!!)
                    setBusy(false)
                }

                is Resource.Error -> {
                    setErrorDialogState(true, "Firestore error")
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
                    _state.value = _state.value.copy(isCoinFav = false)
                    cancelRunnable(coinId!!)
                    setBusy(false)

                }

                is Resource.Error -> {
                    setErrorDialogState(true, "Error when removing from favorite")
                    setBusy(false)
                }

                is Resource.Loading -> {
                    setBusy(true)
                }
            }
        }.launchIn(viewModelScope)

    }
    private val backgroundTrackerService: BackgroundTrackerService by lazy {
        BackgroundTrackerService()
    }

    fun addRunnable(id: String, model: CoinEntity) {
        backgroundTrackerService.addRunnable(id, model)
    }

    fun cancelRunnable(id: String) {
        backgroundTrackerService.cancelRunnable(id)
    }

    fun addToFavorite(item: CoinEntity?) {
        addToFavoriteUseCase(_userUid.value!!,item).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    setSuccessDialogState(true, "Coin you selected is added to favorite")
                    _state.value = _state.value.copy(isCoinFav = true)
                    addRunnable(item?.id!!,item)
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