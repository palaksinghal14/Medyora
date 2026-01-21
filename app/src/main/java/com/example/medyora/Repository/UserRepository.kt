package com.example.medyora.Repository

import com.example.medyora.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun saveUserProfile(profile: UserProfile){

        val uid =auth.currentUser?.uid ?: throw IllegalStateException("user not authenticated ")

        firestore.collection("users")
            .document(uid)
            .set(profile.copy(uid=uid))
            .await()

    }

    suspend fun getUserProfile(): UserProfile?{
        val uid = auth.currentUser?.uid ?: return null

        val snapshot = firestore.collection("users")
            .document(uid)
            .get()
            .await()

        return snapshot.toObject(UserProfile::class.java)

    }

    suspend fun deleteUserCompletely(){
        val user = auth.currentUser ?: throw IllegalStateException("user not authenticated")
        val uid = user.uid

        // 1. Delete Firestore doc
        firestore.collection("users")
            .document(uid)
            .delete()
            .await()

        // 2. Delete Firebase Auth user
        user.delete().await()

        // 3. Sign out locally
        auth.signOut()
    }

    suspend fun updateUserProfile(profile: UserProfile){
        val uid = auth.currentUser?.uid ?: throw IllegalStateException("user not authenticated ")

        firestore.collection("users")
            .document(uid)
            .set(profile.copy(uid=uid))
            .await()

    }

}
