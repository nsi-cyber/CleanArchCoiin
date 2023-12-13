package com.nsicyber.boilerplate.presenter.detail

import com.nsicyber.boilerplate.domain.model.CoinDetailModel


data class DetailState(
    val isLoading: Boolean = false,
    val coinDetail: CoinDetailModel?=null,
    val showDialog:Boolean=false,
    val dialogText:String="",
    val error: String = "",
    val isCoinFav:Boolean=false
)
