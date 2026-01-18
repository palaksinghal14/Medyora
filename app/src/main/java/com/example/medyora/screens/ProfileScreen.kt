package com.example.medyora.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.medyora.ui.theme.Gray100
import com.example.medyora.ui.theme.Gray200
import com.example.medyora.ui.theme.Gray300
import com.example.medyora.ui.theme.Gray400
import com.example.medyora.ui.theme.Gray500
import com.example.medyora.ui.theme.Gray600
import com.example.medyora.ui.theme.Gray700
import com.example.medyora.ui.theme.Gray900
import com.example.medyora.ui.theme.Green600
import com.example.medyora.ui.theme.Red600
import com.example.medyora.ui.theme.White

data class ProfileInfo(
    val title: String,
    val icon: ImageVector,
    val items: List<ProfileInfoDetail>
)

data class ProfileInfoDetail(
    val title: String,
    val detail: Any
)
@Composable
fun ProfileScreen(){
       val profileInfo=listOf(
           ProfileInfo(
               title="Personal Details",
               icon = Icons.Filled.Person,
               items=listOf(
                   ProfileInfoDetail(
                       title = "Age",
                       detail=""
                   ),
                   ProfileInfoDetail(
                       title = "Gender",
                       detail=""
                   ),
                   ProfileInfoDetail(
                       title = "Contact",
                       detail=""
                   )
               )
           ),
           ProfileInfo(
               title="Physical Details",
               icon=Icons.Filled.Psychology,
               items=listOf(
                   ProfileInfoDetail(
                       title = "Height",
                       detail=""
                   ),
                   ProfileInfoDetail(
                       title = "Weight",
                       detail=""
                   ),
                   ProfileInfoDetail(
                       title = "Activity Level",
                       detail=""
                   )
               )
           ),

       )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            item {
                Column {
                    Text(
                        text = "Profile",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Gray900
                    )
                    Text(
                        text = "Review and Edit your Profile Details",
                        fontSize = 16.sp,
                        color = Gray600,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            //name and email card
            item{
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = White
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                        color = Gray900
                    )
                ){
                    Column(
                        modifier=Modifier.padding(32.dp)
                    ) {
                        Text(
                            text = "name",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Gray900
                        )
                        Spacer(modifier=Modifier.padding(16.dp) )
                        Text(
                            text = "email",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Gray600,
                        )
                    }
                }
            }


            //content
            items(profileInfo){ profile->
                ProfileInfoCard(profile)
            }

            //medical details
            item {
                MedicalDetailsCard(
                    conditions = emptyList() // later: userProfile.conditions
                )
            }


            // Edit profile button
            item {
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, Red),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = White,
                        contentColor = Red600
                    )

                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit ,
                            contentDescription = "",
                            tint = Red600,
                            modifier = Modifier.size(22.dp)

                        )
                        Text(
                            text = "Edit Profile",
                            fontSize = 16.sp,
                            color =Red600
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun  ProfileInfoCard(
    profile: ProfileInfo
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = Gray900)
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 12.dp, bottom = 12.dp)
                ,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = profile.icon,
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    tint = Gray700
                )

                Spacer(modifier=Modifier.width(16.dp))

                Text(
                    text = profile.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Gray900
                )
            }

            Divider()

            Spacer(modifier=Modifier.padding(8.dp))

            profile.items.forEachIndexed { index, item ->
                ProfileItemRow(item)
                if (index != profile.items.lastIndex) {
                    Spacer(modifier=Modifier.padding(8.dp))
                }


            }
        }
    }
}

@Composable
fun ProfileItemRow(
    item: ProfileInfoDetail
){
       Column(
           modifier=Modifier.padding(start = 20.dp)
       ) {
           Text(
               text = item.title,
               fontSize = 16.sp,
               fontWeight = FontWeight.Bold,
               color = Gray700
           )
           Text(
               text = item.detail.toString(),
               fontSize = 14.sp,
               color = Gray500
               )

       }
}

@Composable
fun MedicalDetailsCard(
    conditions: List<String>
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(1.dp, Gray900)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            // Header
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.MedicalServices,
                    contentDescription = null,
                    tint = Gray700,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Medical Details",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Gray900
                )
            }

            Divider(modifier = Modifier.padding(vertical = 12.dp))

            if (conditions.isEmpty()) {
                Text(
                    text = "No medical conditions added",
                    fontSize = 14.sp,
                    color = Gray500
                )
            } else {
                conditions.forEach { condition ->
                    Text(
                        text = "• $condition",
                        fontSize = 14.sp,
                        color = Gray700,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}
