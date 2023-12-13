package com.nsicyber.boilerplate.domain.repository

import com.nsicyber.boilerplate.data.remote.entity.CoinEntity


interface FirestoreRepository {


    suspend fun getUserFavorites(userUid:String): List<CoinEntity?>?

    suspend fun addToFavorite(userUid: String,item:CoinEntity?)

    suspend fun removeFromFavorite(userUid: String,id:String?)

    suspend fun isCoinFavorited(userUid: String,id:String?):Boolean?


}