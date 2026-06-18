package com.palak.medyora.Authentication

import android.hardware.camera2.params.BlackLevelPattern
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
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

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.palak.medyora.ui.components.FullScreenError
import com.palak.medyora.ui.components.InlineError
import com.palak.medyora.ui.theme.Black
import com.palak.medyora.ui.theme.Blue100
import com.palak.medyora.ui.theme.Blue200
import com.palak.medyora.ui.theme.Blue50
import com.palak.medyora.ui.theme.Blue500
import com.palak.medyora.ui.theme.Blue600
import com.palak.medyora.ui.theme.Gray200
import com.palak.medyora.ui.theme.Gray500
import com.palak.medyora.ui.theme.Gray600
import com.palak.medyora.ui.theme.Gray700
import com.palak.medyora.ui.theme.Gray800
import com.palak.medyora.ui.theme.Gray900
import com.palak.medyora.ui.theme.White
import com.palak.medyora.utils.AppException

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
            .background(Blue50)
            .statusBarsPadding(),
   contentAlignment = Alignment.Center
    ){
        Column(
            modifier= Modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Brand mark
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Blue600, Color(0xFF5B7BF0))
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "M",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Create Account",
                fontSize =26.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Sign up to get started with Medyora",
                fontSize = 14.sp,
                color = Gray500,
                modifier = Modifier.padding(top = 4.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(value = email ,
                onValueChange ={email=it} ,
                label = { Text(text = "email" )},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Gray200,
                    focusedLabelColor = Blue600,
                    unfocusedLabelColor = Gray500,
                    cursorColor = Blue600,
                    focusedTextColor = Gray900,
                    unfocusedTextColor = Gray900,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White
                ),
                )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password ,
                onValueChange ={password=it},
                label = { Text(text = "Password")},
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Gray200,
                    focusedLabelColor = Blue600,
                    unfocusedLabelColor = Gray500,
                    cursorColor = Blue600,
                    focusedTextColor = Gray900,
                    unfocusedTextColor = Gray900,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White
                ))

            Spacer(modifier = Modifier.height(16.dp))

            // if they dont match only then we will show error
            OutlinedTextField(value = confirmpassword ,
                onValueChange ={
                    confirmpassword=it
                    passwordMismatchError= password!=it},
                label = { Text(text = "confirm password")},
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (password == confirmpassword) {
                            viewmodel.SignUp(email, password)
                        } else {
                            passwordMismatchError = true
                        }
                    }
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Gray200,
                    focusedLabelColor = Blue600,
                    unfocusedLabelColor = Gray500,
                    cursorColor = Blue600,
                    focusedTextColor = Gray900,
                    unfocusedTextColor = Gray900,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (passwordMismatchError) {
                InlineError(
                   exception = AppException.UnknownException("Passwords do not match")
                )
            }

            // Firebase auth error — uses AppException
            if (state is AuthViewModel.AuthState.error) {
                Spacer(modifier = Modifier.height(8.dp))
                InlineError(exception = state.message)
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (state is AuthViewModel.AuthState.Loading) {
                CircularProgressIndicator(color = Blue600)
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
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Blue600)
                ) {
                    Text(
                        text = "Create Account",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = White)
                }
            }




            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Already have an Account?",
                    color= Gray600,
                    fontSize = 14.sp
                )

                //navigation
                TextButton(onClick = { OnNavToSignInPage() }) {
                    Text(text = "Sign In",
                        color=Blue600,
                        fontWeight = FontWeight.SemiBold)
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
            .background(Blue50)
            .statusBarsPadding(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier= Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Brand mark
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Blue600, Color(0xFF5B7BF0))
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ),
                contentAlignment = Alignment.Center
            ){
                Text(
                    text = "M",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Welcome Back",
                fontSize =26.sp,
                fontWeight = FontWeight.Bold,
                color = Gray900
            )
            Text(
                text = "Sign in to continue to Medyora",
                fontSize = 14.sp,
                color = Gray500,
                modifier = Modifier.padding(top = 4.dp)
            )


            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = email ,
                onValueChange ={email=it} ,
                label = { Text(text = "email")},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Gray200,
                    focusedLabelColor = Blue600,
                    unfocusedLabelColor = Gray500,
                    cursorColor = Blue600,
                    focusedTextColor = Gray900,
                    unfocusedTextColor = Gray900,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White
                ))

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(value = password ,
                onValueChange ={password=it},
                label = { Text(text = "password")},
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { viewmodel.SignIn(email, password) }
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Blue600,
                    unfocusedBorderColor = Gray200,
                    focusedLabelColor = Blue600,
                    unfocusedLabelColor = Gray500,
                    cursorColor = Blue600,
                    focusedTextColor = Gray900,
                    unfocusedTextColor = Gray900,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White
                ))

            Spacer(modifier = Modifier.height(16.dp))


            // Error shown inline — only for auth errors
            if (state is AuthViewModel.AuthState.error) {
                Spacer(modifier = Modifier.height(8.dp))
                InlineError(exception = state.message)

            }

            Spacer(modifier = Modifier.height(24.dp))

            // Loading or button
            if (state is AuthViewModel.AuthState.Loading) {
                CircularProgressIndicator(color = Blue600)
            }else{
                // this will call the sign in function and will pass the input
                Button(onClick = { viewmodel.SignIn(email,password) },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Blue600)) {
                    Text(text = "Sign In",
                        color = White)
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()) {
                Text(text = "Don't have an account?",
                    color= Gray600,
                    fontSize = 14.sp
                )

                //navigation
                TextButton(onClick = { OnNavToSignUpPage() }) {
                    Text(text = "Sign Up",
                        color=Blue600,
                        fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
