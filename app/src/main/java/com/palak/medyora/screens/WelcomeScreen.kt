package com.palak.medyora.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HealthAndSafety
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.rememberCoroutineScope
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
import com.palak.medyora.ui.theme.Blue100
import com.palak.medyora.ui.theme.Blue200
import com.palak.medyora.ui.theme.Blue50
import com.palak.medyora.ui.theme.Blue600
import com.palak.medyora.ui.theme.Gray300
import com.palak.medyora.ui.theme.Gray500
import com.palak.medyora.ui.theme.Gray600
import com.palak.medyora.ui.theme.Gray900
import com.palak.medyora.ui.theme.White
import kotlinx.coroutines.launch

   data class IntroPage(
       val icon : ImageVector,
       val title: String ,
       val description: String,
       val iconBackgroundColor: Color
    )

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WelcomeScreen(
    OnNavToSignUpPage:()-> Unit
){

    val pages = listOf(

        IntroPage(
            icon = Icons.Default.Info,
            title = "Welcome to Medyora",
            description = "Your comprehensive health companion that helps you track symptoms, manage nutrition, and find healthcare providers in one place.",
            iconBackgroundColor = Blue600
        ),
        IntroPage(
            icon = Icons.Default.HealthAndSafety,
            title = "Smart Symptom Analysis",
            description = "Describe how you feel. Get AI-powered insights and personalized health recommendations instantly.",
            iconBackgroundColor = Blue600
        ),
        IntroPage(
            icon = Icons.Default.Restaurant,
            title = "Your Personal Food Guide",
            description = "Know what to eat based on your health conditions. Get safe alternatives and portion guidance.",
            iconBackgroundColor = Blue600
        ),
        IntroPage(
            icon = Icons.Default.LocationOn,
            title = "Find Doctors Near You",
            description = "Locate trusted healthcare professionals nearby. Your health support is always close.",
            iconBackgroundColor = Blue600
        ),
        IntroPage(
            icon = Icons.Default.Person,
            title = "Built Around You",
            description = "Your health profile personalizes every recommendation. Medyora knows you, not just your symptoms.",
            iconBackgroundColor = Blue600
        )
    )

    val pagerState= rememberPagerState(pageCount = { pages.size })
    val scope= rememberCoroutineScope()
    val isLastPage = pagerState.currentPage + 1 == pages.size

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Blue50)
            .statusBarsPadding()

       // contentAlignment = Alignment.Center
    ){
        CompositionLocalProvider(LocalContentColor provides Gray900) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                //verticalArrangement =Arrangement.SpaceEvenly
            ) {

                Spacer(modifier = Modifier.height(32.dp))

                //Small brand mark
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Blue600, Color(0xFF5B7BF0))
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "M",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = White
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Medyora",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0E1421)
                )

                Text(
                    text = "Your personal health companion",
                    fontSize = 13.sp,
                    color = Gray500,
                    modifier = Modifier.padding(top = 2.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    key = { it }
                ) { page ->
                    IntroCard(pages[page])
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Dot indicators — pill style
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(bottom = 24.dp)
                ) {
                    repeat(pages.size) { index ->

                        val isSelected = index == pagerState.currentPage

                        Box(
                            modifier = Modifier
                                .height(8.dp)
                                .width(if (isSelected) 24.dp else 8.dp)
                                //.size(if (index == pagerState.currentPage) 12.dp else 8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (isSelected) Blue600
                                    else Blue200
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Bottom row — Skip left, Next right
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick = { OnNavToSignUpPage() }
                    ) {
                        Text(
                            text = "Skip",
                            fontSize = 15.sp,
                            color = Gray500
                        )
                    }

                    Button(
                        onClick = {
                            if (!isLastPage) {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            } else {
                                OnNavToSignUpPage()
                            }
                        },// if we are not at last page , it should navigate to next card
                        //  modifier = Modifier
                        //      .height(44.dp)
                        //      .width(100.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Blue600),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = if (isLastPage) {
                                "Get Started"
                            } else {
                                "Next"
                            },
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun IntroCard( page : IntroPage){

    Card(
        modifier=Modifier
        .fillMaxWidth()
        .padding(24.dp)
        .height(320.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = White,
            disabledContainerColor = White,
            contentColor = Gray900,
            disabledContentColor = Gray900 ) ,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 2.dp,
            focusedElevation = 2.dp,
            hoveredElevation = 2.dp),
        border = BorderStroke(1.dp, Blue100)


    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Single icon container
        Box(
            modifier = Modifier
                .size(72.dp)
                .background(
                    color = page.iconBackgroundColor.copy(alpha = 0.12f),
                    shape =  RoundedCornerShape(20.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

                Icon(
                    imageVector = page.icon,
                    contentDescription = null,
                    tint = page.iconBackgroundColor,
                    modifier = Modifier.size(36.dp)
                )

        }

        Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = page.title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Gray900,
                textAlign = TextAlign.Center,
                lineHeight=30.sp
                )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = page.description,
                fontSize = 15.sp,
               // fontWeight = FontWeight.SemiBold,
                color = Gray500,
                textAlign = TextAlign.Center,
                lineHeight = 23.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

        }
    }
}
