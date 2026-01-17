package com.example.medyora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.medyora.navigation.App
import com.example.medyora.ui.theme.MedyoraTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MedyoraTheme {
                // A surface container using the 'background' color from the theme
                App()
            }
        }
    }
}
