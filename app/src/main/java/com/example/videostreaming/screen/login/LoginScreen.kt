package com.example.videostreaming.screen.login


import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import com.example.videostreaming.ui.theme.LightGray
import com.example.videostreaming.ui.theme.VideoStreamingTheme

@Composable
fun LoginScreen(navController: NavController=NavController(LocalContext.current), modifier: Modifier = Modifier) {
    val context = LocalContext.current

    val loginViewModel: LoginViewModel = viewModel()
    val loginData by loginViewModel.authData.collectAsState()
    val loginResult by loginViewModel.loginResult.collectAsState()



    Scaffold(modifier = modifier) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color.Black),
            contentAlignment = Alignment.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image1), // Replace with your image resource
                    contentDescription = "Header Image",
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop// Adjust the height as needed
                )

                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.displayLarge,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(24.dp))
                CustomTextField(
                    value = loginData.email,
                    onValueChange = { loginViewModel.updateLoginData(loginData.copy(email = it)) },
                    label = "Email",
                    hint = "Email",
                    icon = Icons.Filled.Email
                )
                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    value = loginData.password,
                    onValueChange = { loginViewModel.updateLoginData(loginData.copy(password = it)) },
                    label = "Password",
                    hint = "Password",
                    icon = Icons.Filled.Lock
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "Forget Password",
                        color = LightGray,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))

                CustomButton(
                    textToDisplay = "Sign in",
                    onClick = {
                        if (loginData.email.isEmpty()) {
                            Toast.makeText(context, "Please enter email", Toast.LENGTH_SHORT).show()
                        }else if(!Patterns.EMAIL_ADDRESS.matcher(loginData.email).matches()){
                            Toast.makeText(context, "Please enter valid email", Toast.LENGTH_SHORT).show()
                        } else if (loginData.password.isEmpty()) {
                            Toast.makeText(context, "Please enter password", Toast.LENGTH_SHORT)
                                .show()
                        }else {
                            loginViewModel.login() // Trigger login process
                        }

                    }
                )
                Spacer(modifier = Modifier.height(5.dp))

                CustomTextButton(
                    firstText = "Don't you have an account?",
                    secondText = "Sign up",
                    onClick = {
                        navController.navigate(Route.Signup.name)
                    }
                )


                Spacer(modifier = Modifier.height(50.dp))
                // Display validation error only if sign-in button has been clicked

                // Success or Failure Message
                loginResult?.let {
                    when {
                        it.isSuccess -> {
                            Toast.makeText(
                                context,
                                it.getOrNull() ?: "Login Successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate(Route.Home.name)
                        }
                        it.isFailure -> {
                            Toast.makeText(
                                context,
                                it.exceptionOrNull()?.message ?: "Login Failed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LoginScreenPreview() {
   VideoStreamingTheme {
       LoginScreen()
   }
}
