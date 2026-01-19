package com.example.medyora.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.outlined.ContactSupport
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.medyora.ui.theme.Blue100
import com.example.medyora.ui.theme.Blue200
import com.example.medyora.ui.theme.Blue50
import com.example.medyora.ui.theme.Gray500
import com.example.medyora.ui.theme.Gray600
import com.example.medyora.ui.theme.Gray700
import com.example.medyora.ui.theme.Gray900
import com.example.medyora.ui.theme.Orange500
import com.example.medyora.ui.theme.Red50
import com.example.medyora.ui.theme.Red600
import com.example.medyora.ui.theme.White
import com.example.medyora.viewmodels.SettingsViewModel


data class SettingsGroup(
    val title: String,
    val icon: ImageVector,
    val items: List<SettingsItem>
)

data class SettingsItem(
    val title: String,
    val iconStart: ImageVector,
    val onClick: () -> Unit = {}
)

@Composable
fun SettingsScreen(
    onNavToSignUpPage:() -> Unit
) {

    val settingsViewModel: SettingsViewModel= hiltViewModel()

    var showDialogLogOut by remember { mutableStateOf(false) }
    var showDialogDelete by remember { mutableStateOf(false) }

    val settingsGroups = listOf(
        SettingsGroup(
            title = "Account",
            icon=Icons.Outlined.Person,
            items = listOf(
                SettingsItem(
                    title = "Manage Account Details " ,
                    iconStart = Icons.Default.Person,
                    onClick = {}

                ),
                SettingsItem(
                    title = "Change Password" ,
                    iconStart = Icons.Default.Shield,
                    onClick = {}

                )
            )
        ),
        SettingsGroup(
            title="Appearance",
            icon=Icons.Outlined.DarkMode,
            items = listOf(
                SettingsItem(
                    title = "Dark Mode " ,
                    iconStart = Icons.Default.DarkMode,
                    onClick = {}
                ),
                SettingsItem(
                    title = "language" ,
                    iconStart = Icons.Default.Language,
                    onClick = {}

                )
            )

        ),

        SettingsGroup(
            title="About",
            icon=Icons.Outlined.ContactSupport,
            items = listOf(
                SettingsItem(
                    title = "Privacy policy " ,
                    iconStart = Icons.Default.PrivacyTip,
                    onClick = {}
                ),
                SettingsItem(
                    title = "terms of Service " ,
                    iconStart = Icons.Default.LibraryBooks,
                    onClick = {}

                )
            )

        )
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
                        text = "Settings",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Gray900
                    )
                    Text(
                        text = "Customize your app experience",
                        fontSize = 16.sp,
                        color = Gray600,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            //content
            items(settingsGroups){ group->
                SettingsGroupCard(group)
                }



            // delete account button
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = White
                    ),
                    border = BorderStroke(
                        width = 1.dp,
                       color = Red600)
                ) {
                    Column(
                        modifier=Modifier.padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.DeleteOutline ,
                                contentDescription = "",
                                tint = Red600,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(text = "Danger Zone",
                                color =Red,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )

                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedButton(
                            onClick = {
                                showDialogDelete=true
                            },
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
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.DeleteOutline ,
                                    contentDescription = "",
                                    tint = Red600,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(text = "Delete All Data",
                                    color =Red600,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Medium
                                )

                            }

                        }

                    }
                }
            }

            // signout button
            item {
                OutlinedButton(
                    onClick = {
                        showDialogLogOut=true
                    },
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
                           imageVector = Icons.Default.Logout ,
                           contentDescription = "",
                           tint = Red600,
                           modifier = Modifier.size(16.dp)

                       )
                       Text(
                           text = "Sign Out",
                           fontSize = 12.sp,
                           color =Red600
                       )

                    }
                }
            }


            //details
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                  Text(text = "Medyora v1.0.0",
                      color = Gray600,
                      fontSize = 12.sp
                  )
                    Text(text = "Made with ❤ for your health",
                        color = Gray600,
                        fontSize = 12.sp
                    )

                }
            }


        }
    }

    if (showDialogLogOut==true){
        showDiaglog(
            onDismiss = {showDialogLogOut=false },
            onConfirm = {
                settingsViewModel.signOut()
                onNavToSignUpPage
            },
            description = "Are you sure you want to sign out",
            title = "SIGN OUT"


        )
    }
    if (showDialogDelete==true){
        showDiaglog(
            onDismiss = {showDialogDelete=false},
            onConfirm = {
                settingsViewModel.deleteData()
                onNavToSignUpPage
            },
            description = "Are you sure you want to delete your data",
            title = "DELETE ALL DATA"

        )
    }

}


@Composable
fun SettingsGroupCard(group: SettingsGroup) {
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
            color = Black)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 12.dp, bottom = 12.dp)
                ,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = group.icon,
                    contentDescription = "",
                    modifier = Modifier.size(20.dp),
                    tint = Gray700
                )
                Text(
                    text = group.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    color = Gray900
                )
            }

            Divider()

            group.items.forEachIndexed { index, item ->
                SettingsItemRow(item)
                if (index != group.items.lastIndex) {
                    Divider(modifier = Modifier.padding(start = 56.dp))
                }

            }
        }
    }
}

@Composable
fun SettingsItemRow(item: SettingsItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { item.onClick() }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = item.iconStart,
            contentDescription = null,
            tint = Gray700
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = item.title,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            color = Gray900
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            tint = Gray500
        )
    }
}


@Composable
fun showDiaglog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    description: String,
    title: String
){
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            OutlinedButton(
                onClick = onConfirm,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Gray900),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = White,
                    contentColor = Gray900
                )
            ) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, Gray900),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = White,
                    contentColor = Gray900
                )
            ) {
                Text(text ="Cancel" )
            }
        },
        text = {Text(
            text = description,
            fontSize = 14.sp,
            color = Gray900
        )},
        title = {Text(
            text = title,
            fontSize = 16.sp,
            color = Gray900
        )},
        containerColor = White,

    )
}






