package com.nsicyber.boilerplate.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.nsicyber.boilerplate.data.remote.entity.CoinEntity
import com.nsicyber.boilerplate.domain.repository.FirestoreRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FirestoreRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : FirestoreRepository {





    override suspend fun getUserFavorites(userUid: String): List<CoinEntity?>? {
        val user =
            firebaseFirestore.collection(userUid)
                .get().await()

        return user.documents.map { it.toObject(CoinEntity::class.java) }
    }

    override suspend fun addToFavorite(userUid: String,item: CoinEntity?) {
        firebaseFirestore.collection(userUid).document(item?.id!!)
            .set(item).await()
    }

    override suspend fun removeFromFavorite(userUid: String,id: String?) {
        firebaseFirestore.collection(userUid)
            .document(id!!).delete().await()
    }

    override suspend fun isCoinFavorited(userUid: String, id: String?): Boolean? {
        val documentSnapshot = firebaseFirestore.collection(userUid).document(id!!)
            .get().await()

        return documentSnapshot.exists()

    }


}