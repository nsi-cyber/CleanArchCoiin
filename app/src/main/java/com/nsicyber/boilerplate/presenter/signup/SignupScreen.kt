package com.nsicyber.boilerplate.presenter.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nsicyber.boilerplate.presenter.base.components.InputTextField
import com.nsicyber.boilerplate.presenter.base.components.PasswordInputTextField
import com.nsicyber.boilerplate.presenter.base.BaseView
import com.nsicyber.boilerplate.R


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SignupScreen() {
    val viewModel = hiltViewModel<SignupViewModel>();

var textInputMail by remember { mutableStateOf<String>("") }
var textInputPassword by remember { mutableStateOf<String>("") }
var textInputRePassword by remember { mutableStateOf<String>("") }

    BaseView(canGoBack = true, viewModel = viewModel, useIsBusy = true, isVerticalPaddingEnabled = false) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


            Image(
                modifier = Modifier.size(246.dp),
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = ""
            )


//Texts
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "Sign Up",

                    // Heading / H2 / ExtraBold
                    style = TextStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight(800),
                        color = Color(0xFF1B1725),
                    )
                )
                Text(
                    text = "To enter the beauty of cryptoworld, sign up.",

                    // Body/Small/semibold
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        fontWeight = FontWeight(600),
                        color = Color(0x8064748B),
                    )
                )


            }

//Input
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                InputTextField(placeholder = "Enter your e-Mail",
                    value = textInputMail,
                    onValueChange = {
                        textInputMail = it
                    })


                PasswordInputTextField(
                    placeholder = "Enter your Password",
                    onValueChange = {
                        textInputPassword = it
                    },
                    value = textInputPassword,
                )

                PasswordInputTextField(
                    placeholder = "Enter your Password Again",
                    onValueChange = {
                        textInputRePassword = it
                    },
                    value = textInputRePassword,
                )
            }


//Buttons
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {


                Row(
                    Modifier
                        .clickable {
                            viewModel.createUser(textInputMail,textInputPassword,textInputRePassword)
                        }
                        .fillMaxWidth()
                        .background(
                            color = Color(0xFF1B1725),
                            shape = RoundedCornerShape(size = 8.dp)
                        )
                        .padding(start = 36.dp, top = 15.dp, end = 36.dp, bottom = 15.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Sign Up",

                        // Body/Large/bold
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 20.8.sp,
                            fontWeight = FontWeight(700),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center,
                        )
                    )

                }


            }

        }
    }
}