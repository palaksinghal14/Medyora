package com.palak.medyora.Repository

import androidx.compose.ui.geometry.Rect
import com.palak.medyora.model.UserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.palak.medyora.utils.AppException
import com.palak.medyora.utils.toAppException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {


    fun getUserEmail(): String? = auth.currentUser?.email

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    suspend fun saveUserProfile(profile: UserProfile): Result<Unit>{

        return try{
            val uid =auth.currentUser?.uid ?: return Result.failure(AppException.UserNotFoundException)

            firestore.collection("users")
                .document(uid)
                .set(profile.copy(uid=uid))
                .await()

            Result.success(Unit)
        }catch (e:Exception){
            Result.failure(e.toAppException())
        }

    }

    suspend fun getUserProfile(): Result<UserProfile?>{

        return try {
            val uid = auth.currentUser?.uid ?: return Result.failure(AppException.UserNotFoundException)

            val snapshot = firestore.collection("users")
                .document(uid)
                .get()
                .await()

            Result.success(snapshot.toObject(UserProfile::class.java))

        }catch (e: Exception){
            Result.failure(e.toAppException())
        }


    }

    suspend fun deleteUserCompletely():Result<Unit>{

        return try {
            val user = auth.currentUser ?: return Result.failure(AppException.UserNotFoundException)
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

            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e.toAppException())
        }

    }

    suspend fun updateUserProfile(profile: UserProfile) : Result<Unit>{

        return try {
            val uid = auth.currentUser?.uid ?: return Result.failure(AppException.UserNotFoundException)

            firestore.collection("users")
                .document(uid)
                .set(profile.copy(uid=uid))
                .await()

            Result.success(Unit)
        }catch (e: Exception){
            Result.failure(e.toAppException())
        }
    }



}
