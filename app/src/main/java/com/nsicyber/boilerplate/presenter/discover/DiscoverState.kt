package com.nsicyber.boilerplate.presenter.discover

import com.nsicyber.boilerplate.data.remote.dto.CoinListDto


data class DiscoverState(
    val coinList: List<CoinListDto?>?= null,
)
