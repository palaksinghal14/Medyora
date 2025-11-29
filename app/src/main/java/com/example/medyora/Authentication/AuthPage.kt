package com.example.medyora.Authentication

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
import com.example.medyora.ui.theme.Blue100
import com.example.medyora.ui.theme.Blue200
import com.example.medyora.ui.theme.Blue500
import com.example.medyora.ui.theme.Blue600
import com.example.medyora.ui.theme.Gray600
import com.example.medyora.ui.theme.Gray700
import com.example.medyora.ui.theme.White

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

    var errormessage by remember{mutableStateOf<String?>("")}

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
                onValueChange ={ confirmpassword=it
                    errormessage=if (password!=confirmpassword)"password not match" else null},
                label = { Text(text = "confirm password",
                    color= Gray700)},

                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Blue600,
                    focusedLabelColor = Gray700,
                    cursorColor = Blue100,
                    focusedTextColor = Blue500
                ))

            // if there is error show it
            if (errormessage != null) {
                Text(errormessage!!, color = Color.Red)
            }

            Spacer(modifier = Modifier.padding(10.dp))

            // only it both password matches , we will calll signup
            Button(onClick = {
                if(password==confirmpassword)
                    viewmodel.SignUp(email, password)
            },
                colors = ButtonDefaults.buttonColors(containerColor = Blue600)
                ) {
                Text(text = "Sign Up",
                    color = White
                )
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


    when (state) {
        is AuthViewModel.AuthState.Idle -> {  }
        is AuthViewModel.AuthState.Loading -> {
            CircularProgressIndicator()
        }
        is AuthViewModel.AuthState.Success -> {
            Text("Welcome ${state.user.email}")

            OnNavToUserDetailsPage()
        }
        is AuthViewModel.AuthState.error -> {
            Text(text = "error", color = Color.Red)
        }


    }

}




@Composable
fun SignInScreen  (
    viewmodel:AuthViewModel=hiltViewModel(),
    OnNavToMainPage:() -> Unit

){
    val state= viewmodel.state.value

    var email by remember{mutableStateOf("")}

    var password by remember{mutableStateOf("")}

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

            // this will call the sign in function and will pass the input
            Button(onClick = { viewmodel.SignIn(email,password) },
                colors = ButtonDefaults.buttonColors(containerColor = Blue600)) {
                Text(text = "Sign In",
                    color = White)
            }

            Spacer(modifier = Modifier.padding(10.dp))


        }

    }


    when (state) {
        is AuthViewModel.AuthState.Idle -> { /* do nothing */ }
        is AuthViewModel.AuthState.Loading -> {
            CircularProgressIndicator()
        }
        is AuthViewModel.AuthState.Success -> {
            Text("Welcome ${state.user.email}")
            // or navigate to HomeScreen
            OnNavToMainPage()
        }
        is AuthViewModel.AuthState.error -> {
            Text(text = "error", color = Color.Red)
        }
    }
}
