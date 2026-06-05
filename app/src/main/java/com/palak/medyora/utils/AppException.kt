package com.palak.medyora.utils

sealed class AppException : Exception(){

    // Network errors
    object NoInternetException : AppException()
    object TimeoutException : AppException()
    data class ServerException(val code: Int) : AppException()

    // Firebase errors
    object UserNotFoundException : AppException()
    object WrongPasswordException : AppException()
    object EmailAlreadyExistsException : AppException()
    object FirebaseUnavailableException : AppException()

    // AI errors
    object AiResponseParseException : AppException()
    object AiQuotaExceededException : AppException()
    object AiUnavailableException : AppException()

    // Places errors
    object LocationPermissionDeniedException : AppException()
    object PlacesApiException : AppException()
    object NoNearbyDoctorsException : AppException()

    // Generic fallback
    data class UnknownException(val originalMessage: String?) : AppException()
}

// utils/AppException.kt
fun AppException.toUserMessage(): String {
    return when (this) {
        is AppException.NoInternetException ->
            "No internet connection. Please check your network."
        is AppException.TimeoutException ->
            "Request timed out. Please try again."
        is AppException.UserNotFoundException ->
            "No account found with this email."
        is AppException.WrongPasswordException ->
            "Incorrect password. Please try again."
        is AppException.EmailAlreadyExistsException ->
            "An account with this email already exists."
        is AppException.AiResponseParseException ->
            "Couldn't process the AI response. Please try again."
        is AppException.AiQuotaExceededException ->
            "AI service is temporarily unavailable. Try again later."
        is AppException.NoNearbyDoctorsException ->
            "No doctors found nearby. Try expanding your search area."
        is AppException.LocationPermissionDeniedException ->
            "Location permission is required to find nearby doctors."
        is AppException.ServerException ->
            "Server error (${this.code}). Please try again later."
        is AppException.UnknownException ->
            "Something went wrong. Please try again."
        is AppException.AiUnavailableException ->
            "AI service is currently unavailable. Please try again later"
        is AppException.FirebaseUnavailableException ->
            "Service is temporarily unavailable. Please try again later"
        is AppException.PlacesApiException ->
            "Couldn't fetch nearby locations. Please try again."
    }
}