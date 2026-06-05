package com.palak.medyora.Authentication

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
class AuthRepo @Inject constructor(
    val auth: FirebaseAuth
){
    fun user () = auth.currentUser

    suspend fun SignUp( email : String , password : String) : FirebaseUser?{

            var result= auth.createUserWithEmailAndPassword(email,password).await()
            return result.user

    }

    suspend fun SignIn( email : String , password : String) : FirebaseUser?{

            var result= auth.signInWithEmailAndPassword(email,password).await()
            return result.user

    }

    fun logout() = auth.signOut()
}

