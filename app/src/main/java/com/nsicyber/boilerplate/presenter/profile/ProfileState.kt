package com.nsicyber.boilerplate.presenter.profile

import com.nsicyber.boilerplate.data.remote.entity.CoinEntity


data class ProfileState(
    val favCoinList: List<CoinEntity?>?=null,
)
