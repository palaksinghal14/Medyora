package com.palak.medyora.utils


import android.util.Log
import com.google.ai.client.generativeai.type.GoogleGenerativeAIException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.gson.JsonParseException
import com.palak.medyora.BuildConfig
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.google.ai.client.generativeai.type.ServerException
// utils/ExceptionMapper.kt
fun Throwable.toAppException(): AppException {
    return when (this) {

        // --- Network ---
        is UnknownHostException -> AppException.NoInternetException
        is SocketTimeoutException -> AppException.TimeoutException

        // --- Retrofit / HTTP ---
        is HttpException -> AppException.ServerException(this.code())

        // --- JSON / AI parsing ---
        is JsonParseException -> AppException.AiResponseParseException

        //--- Firebase---

        // --- Firebase Auth ---
        is FirebaseAuthInvalidUserException -> AppException.UserNotFoundException
        is FirebaseAuthInvalidCredentialsException -> AppException.WrongPasswordException
        is FirebaseAuthUserCollisionException -> AppException.EmailAlreadyExistsException
        // --- Firebase Network ---
        is FirebaseNetworkException -> AppException.FirebaseUnavailableException
        // --- Firestore specific ---
        is FirebaseFirestoreException -> when (this.code) {
            FirebaseFirestoreException.Code.UNAVAILABLE ->
                AppException.FirebaseUnavailableException
            FirebaseFirestoreException.Code.NOT_FOUND ->
                AppException.UserNotFoundException
            FirebaseFirestoreException.Code.PERMISSION_DENIED ->
                AppException.UnknownException("Permission denied. Check Firestore rules.")
            else ->
                AppException.UnknownException(this.message)
        }

        // --- Gemini SDK ---

        // GoogleGenerativeAIException is the base exception from Gemini SDK
        // It carries a message describing what went wrong
        is GoogleGenerativeAIException -> AppException.AiUnavailableException


       is ServerException ->
           when {
               this.message?.contains("429", ignoreCase = true) == true ||
                       this.message?.contains("RESOURCE_EXHAUSTED", ignoreCase = true) == true ->
                   AppException.AiQuotaExceededException
               this.message?.contains("503", ignoreCase = true) == true ||
                       this.message?.contains("unavailable", ignoreCase = true) == true ->
                   AppException.AiUnavailableException
               else ->
                   AppException.AiUnavailableException

           }

        else -> AppException.UnknownException(this.message)
    }
}
