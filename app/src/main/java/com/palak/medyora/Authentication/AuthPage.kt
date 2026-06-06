package com.palak.medyora.Authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.palak.medyora.ui.components.FullScreenError
import com.palak.medyora.ui.components.InlineError
import com.palak.medyora.ui.theme.Blue100
import com.palak.medyora.ui.theme.Blue200
import com.palak.medyora.ui.theme.Blue500
import com.palak.medyora.ui.theme.Blue600
import com.palak.medyora.ui.theme.Gray600
import com.palak.medyora.ui.theme.Gray700
import com.palak.medyora.ui.theme.White

@Composable
fun SignUpScreen(
    viewmodel:AuthViewModel=hiltViewModel(),
    OnNavToSignInPage:() -> Unit,
    OnNavToUserDetailsPage:() -> Unit
){
    val state= viewmodel.state.value

    var email by remember{mutableStateOf("")}

    var password by remember{mutableStateOf("")}

    var confirmpassword by remember{mutableStateOf("")}

    var passwordMismatchError by remember { mutableStateOf(false) }

    // Navigation — runs once when Success
    LaunchedEffect(state) {
        if (state is AuthViewModel.AuthState.Success) {
            OnNavToUserDetailsPage()
            viewmodel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Blue200, Blue100)
                )
            ),
        contentAlignment = Alignment.Center
    ){
        Column( modifier= Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = "Sign Up",
                fontSize =40.sp,
                color = Blue600
            )

            Spacer(modifier = Modifier.padding(16.dp))

            OutlinedTextField(value = email ,
                onValueChange ={email=it} ,
                label = { Text(text = "email",
                    color= Gray700
                )},

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Blue600,
                    focusedLabelColor = Blue100,
                    cursorColor = Blue100,
                    focusedTextColor = Blue500
                ))

            Spacer(modifier = Modifier.padding(16.dp))

            OutlinedTextField(value = password ,
                onValueChange ={password=it},
                label = { Text(text = "password",
                    color= Gray700)},
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Blue600,
                    focusedLabelColor = Gray700,
                    cursorColor = Blue100,
                    focusedTextColor = Blue500
                ))

            Spacer(modifier = Modifier.padding(16.dp))

            // if they dont match only then we will show error
            OutlinedTextField(value = confirmpassword ,
                onValueChange ={
                    confirmpassword=it
                    passwordMismatchError= password!=it},
                label = { Text(text = "confirm password",
                    color= Gray700)},

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Blue600,
                    focusedLabelColor = Gray700,
                    cursorColor = Blue100,
                    focusedTextColor = Blue500
                ))

            if (passwordMismatchError) {
                Text(
                    text = "Passwords do not match",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Firebase auth error — uses AppException
            if (state is AuthViewModel.AuthState.error) {
                InlineError(exception = state.message)
            }

            Spacer(modifier = Modifier.padding(10.dp))

            if (state is AuthViewModel.AuthState.Loading) {
                CircularProgressIndicator()
            } else {
                // only it both password matches , we will calll signup
                Button(
                    onClick = {
                        if (password == confirmpassword) {
                            viewmodel.SignUp(email, password)
                        } else {
                            passwordMismatchError = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Blue600)
                ) {
                    Text(text = "Sign Up", color = White)
                }
            }


            Spacer(modifier = Modifier.padding(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Already have an Account?",
                    color= Gray600
                )

                //navigation
                TextButton(onClick = { OnNavToSignInPage() }) {
                    Text(text = "Sign In",
                        color=Blue600)
                }
            }
        }
    }

}




@Composable
fun SignInScreen  (
    viewmodel:AuthViewModel=hiltViewModel(),
    OnNavToMainPage:() -> Unit,
    OnNavToSignUpPage :()-> Unit

){
    val state= viewmodel.state.value

    var email by remember{mutableStateOf("")}

    var password by remember{mutableStateOf("")}

    // Navigation — runs once when Success, not on every recomposition
    LaunchedEffect(state) {
        if (state is AuthViewModel.AuthState.Success) {
            OnNavToMainPage()
            viewmodel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Blue200, Blue100)
                )
            ),
        contentAlignment = Alignment.Center
    ){
        Column( modifier= Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(text = "Login",
                fontSize =40.sp,
                color = Blue600
            )

            Spacer(modifier = Modifier.padding(16.dp))

            OutlinedTextField(value = email ,
                onValueChange ={email=it} ,
                label = { Text(text = "email",
                    color= Gray700)},

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Blue600,
                    focusedLabelColor = Gray700,
                    cursorColor = Blue100,
                    focusedTextColor = Blue500
                ))

            Spacer(modifier = Modifier.padding(16.dp))

            OutlinedTextField(value = password ,
                onValueChange ={password=it},
                label = { Text(text = "password",
                    color= Gray700)},

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Blue600,
                    focusedLabelColor = Gray700,
                    cursorColor = Blue100,
                    focusedTextColor = Blue500
                ))

            Spacer(modifier = Modifier.padding(16.dp))


            // Error shown inline — only for auth errors
            if (state is AuthViewModel.AuthState.error) {
                InlineError(exception = state.message)
                Spacer(modifier = Modifier.padding(4.dp))
            }

            Spacer(modifier = Modifier.padding(8.dp))

            // Loading or button
            if (state is AuthViewModel.AuthState.Loading) {
                CircularProgressIndicator()
            }else{
                // this will call the sign in function and will pass the input
                Button(onClick = { viewmodel.SignIn(email,password) },
                    colors = ButtonDefaults.buttonColors(containerColor = Blue600)) {
                    Text(text = "Sign In",
                        color = White)
                }
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Create new Account ?",
                    color= Gray600
                )

                //navigation
                TextButton(onClick = { OnNavToSignUpPage() }) {
                    Text(text = "Sign Up",
                        color=Blue600)
                }
            }
        }
    }
}
