package com.example.mspp_cogassessapp.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mspp_cogassessapp.R
import com.example.mspp_cogassessapp.firebase.User
import com.example.mspp_cogassessapp.firebase.UserDataDto
import firebase.ErrorManager

@Composable
fun ProfilePage() {
    val context = LocalContext.current
    val errorManager = ErrorManager(context)
    val user = User(errorManager)

    var userData by remember { mutableStateOf<UserDataDto?>(null) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        user.getUserData { data ->
            userData = data
            loading = false
        }
    }

    if (loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .background(colorResource(id = R.color.mantle)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = Color.White)
        }
    } else {
        userData?.let { data ->
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(colorResource(id = R.color.mantle), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Welcome to the Profile Page!",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ProfileItem(label = "Name", value = data.name)
                    ProfileItem(label = "Email", value = data.email)
                    ProfileItem(label = "Sex", value = data.sex)
                    ProfileItem(label = "Age", value = data.age.toString())
                }
            }
        } ?: run {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .background(colorResource(id = R.color.mantle), shape = RoundedCornerShape(8.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("Failed to load user data", color = Color.White)
            }
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = "$label: ",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun PreviewProfilePage() {
    ProfilePage()
}
