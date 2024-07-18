package com.example.videostreaming.screen.signup

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.videostreaming.R
import com.example.videostreaming.route.Route
import com.example.videostreaming.screen.component.CustomButton
import com.example.videostreaming.screen.component.CustomTextButton
import com.example.videostreaming.screen.component.CustomTextField
import com.example.videostreaming.ui.theme.Black
import com.example.videostreaming.ui.theme.VideoStreamingTheme

@Composable
fun SignupScreen(
    navController: NavController = NavController(LocalContext.current),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val signupViewModel: SignupViewModel = viewModel()
    val signupData by signupViewModel.authData.collectAsState()

    val signupResult by signupViewModel.signupResult.collectAsState()


    Scaffold(modifier = modifier) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(color = Black),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image2),
                    contentDescription = "Header Image",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "Sign up",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(24.dp))
                CustomTextField(
                    value = signupData.name.orEmpty(),
                    onValueChange = { signupViewModel.updateSignupData(signupData.copy(name = it)) },
                    label = "Name",
                    hint = "Name",
                    icon = Icons.Filled.Person
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    value = signupData.email,
                    onValueChange = { signupViewModel.updateSignupData(signupData.copy(email = it)) },
                    label = "Email",
                    hint = "Email",
                    icon = Icons.Filled.Email
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    value = signupData.password,
                    onValueChange = { signupViewModel.updateSignupData(signupData.copy(password = it)) },
                    label = "Password",
                    hint = "Password",
                    icon = Icons.Filled.Lock
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    value = signupData.confirmPassword.orEmpty(),
                    onValueChange = {
                        signupViewModel.updateSignupData(
                            signupData.copy(
                                confirmPassword = it
                            )
                        )
                    },
                    label = "Confirm Password",
                    hint = "Confirm Password",
                    icon = Icons.Filled.Lock
                )
                Spacer(modifier = Modifier.height(24.dp))

                CustomButton(
                    textToDisplay = "Sign up",
                    onClick = {
                        if (signupData.email.isEmpty()) {
                            Toast.makeText(context, "Please enter email", Toast.LENGTH_SHORT).show()
                        } else if (Patterns.EMAIL_ADDRESS.matcher(signupData.email).matches()) {
                            Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT)
                                .show()
                        } else if (signupData.name.isNullOrEmpty()) {
                            Toast.makeText(context, "Please enter name", Toast.LENGTH_SHORT).show()
                        } else if (signupData.password.isEmpty()) {
                            Toast.makeText(context, "Please enter password", Toast.LENGTH_SHORT)
                                .show()
                        } else if (signupData.confirmPassword.isNullOrEmpty()) {
                            Toast.makeText(
                                context,
                                "Please enter confirm password",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (signupData.password != signupData.confirmPassword) {
                            Toast.makeText(
                                context,
                                "Password and confirm password does not match",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            signupViewModel.signup()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))

                CustomTextButton(firstText = "Already have an account?",
                    secondText = "Log in",
                    onClick = {
                        navController.navigate(Route.Login.name)
                    }
                )


            }
            signupResult?.let { result ->
                when {
                    result.isSuccess -> {
                        Toast.makeText(context, result.getOrNull(), Toast.LENGTH_SHORT).show()
                        navController.navigate(Route.Login.name)
                    }

                    result.isFailure -> {
                        Toast.makeText(context, result.getOrNull(), Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
    }
}

@Preview(
    showBackground = true, showSystemUi = true
)
@Composable
fun SignupScreenPreview() {
    VideoStreamingTheme {
        SignupScreen()
    }

}