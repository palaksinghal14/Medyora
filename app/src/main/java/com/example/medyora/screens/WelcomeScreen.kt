package com.example.medyora.screens

import android.graphics.drawable.Icon
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medyora.ui.theme.Blue100
import com.example.medyora.ui.theme.Blue200
import com.example.medyora.ui.theme.Blue50
import com.example.medyora.ui.theme.Blue600
import com.example.medyora.ui.theme.Gray300
import com.example.medyora.ui.theme.Gray600
import com.example.medyora.ui.theme.Gray900
import com.example.medyora.ui.theme.Purple40
import com.example.medyora.ui.theme.White

data class IntroPage( val title: String ,val description: String)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(
    OnNavToSignUpPage:()-> Unit
){

    val pages = listOf(
        IntroPage(
            title = "Welcome to Medyora",
            description = "Your comprehensive health companion that helps you track symptoms, manage nutrition, and find healthcare providers all in one place.",
        ),
        IntroPage(
            title = "Symptom Analysis",
            description = "Get AI-powered insights about your symptoms. Describe how you're feeling and receive personalized health recommendations.",
        ),
        IntroPage(
            title = "Smart Food Guide",
            description = "Receive personalized nutrition recommendations based on your health goals and dietary requirements.",
        ),
        IntroPage(
            title = "Find Doctors Nearby",
            description = "Locate healthcare professionals in your area and book appointments with just a few taps.",
        )
    )

    val pagerState= rememberPagerState(pageCount = { pages.size })
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
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement =Arrangement.SpaceEvenly
        ) {
            Text(text = "Know Medyora ",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Gray900,
                )

            Spacer(modifier = Modifier.height(20.dp))

            HorizontalPager(
               state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
            ){
                page->IntroCard(pages[page])
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row (
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 32.dp)
                ){
                    repeat(pages.size){
                            index->
                        Box(
                            modifier = Modifier
                                .size(if (index == pagerState.currentPage) 12.dp else 8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (index == pagerState.currentPage) Blue600 else Gray300
                                )
                        ){

                        }
                    }
                }
                Button(

                    onClick = {
                        if(pagerState.currentPage+1!= pages.size){

                        }else{
                            OnNavToSignUpPage()
                        }
                    }

                     ,// if we are not at last page , it should navigate to next card
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Blue600),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if(pagerState.currentPage+1== pages.size){"Get Started"}else{"Next"},
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
            }


            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier
                .padding(4.dp)
            ){
                Button(onClick = {
                    OnNavToSignUpPage()
                },
                    colors= ButtonDefaults.buttonColors(containerColor = Blue50),
                    modifier = Modifier.align ( Alignment.BottomCenter )

                ) {
                    Text(text = "Skip Introduction")
                }
            }

        }
    }
}

@Composable
fun IntroCard( page : IntroPage){
    Card(modifier=Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .height(200.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(Blue50),
        shape = RoundedCornerShape(8.dp)

    ){
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = page.title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Gray900,
                textAlign = TextAlign.Center
                )
            Spacer(modifier = Modifier.height(12.dp))

            Text(text = page.description,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Gray600,
                textAlign = TextAlign.Center
            )

        }


    }
}
