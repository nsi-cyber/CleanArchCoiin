package com.nsicyber.boilerplate.presenter.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nsicyber.boilerplate.R
import com.nsicyber.boilerplate.presenter.base.BaseView
import com.nsicyber.boilerplate.presenter.base.components.CustomAppBar
import com.nsicyber.boilerplate.presenter.base.components.CustomCard
import com.nsicyber.boilerplate.presenter.base.components.login
import com.nsicyber.boilerplate.presenter.base.components.navHostController
import com.nsicyber.boilerplate.presenter.profile.component.FavoriteCoinListItem


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProfileScreen() {
    val viewModel = hiltViewModel<ProfileViewModel>()

    LaunchedEffect(Unit) {
        viewModel.getFavoriteCoins()
    }
    BaseView(
        useIsBusy = true, viewModel = viewModel,
        isShowBottomBar = true,
        canGoBack = false,
        canScroll = false,
        isHorizontalPaddingEnabled = true,
        isVerticalPaddingEnabled = false
    ) {
        Column(Modifier.fillMaxSize()) {


            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    CustomAppBar(isTransparent = false,
                        isBackEnable = false,
                        logo = {

                            Row() {
                                Image(
                                    contentDescription = "logo",
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(id = R.drawable.ic_logo)
                                )
                                Text(
                                    text = "CoiinTracker",

                                    // Body/Large/bold
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        lineHeight = 20.8.sp,
                                        fontWeight = FontWeight(700),
                                        color = Color(0xFF1B1725),
                                    )
                                )
                            }
                        },
                        suffixIcon = {
                            CustomCard(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(
                                        BorderStroke(0.5.dp, color = Color.Gray),
                                        RoundedCornerShape(8.dp)
                                    ),
                                backgroundColor = Color(0x8064748B)
                            ) {
                                IconButton(onClick = {                                         viewModel.signout()
                                    viewModel.signout()
                                }) {
                                    Icon(
                                        painterResource(id = R.drawable.ic_exit),
                                        "",
                                        tint = Color.Black,
                                        modifier = Modifier.padding(13.dp)
                                    )
                                }
                            }
                        }
                    )
                }

                item {
                    Text(
                        text = "My Favorite Coins",

                        // Heading / H2 / ExtraBold
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontWeight = FontWeight(800),
                            color = Color(0xFF1B1725),
                        )
                    )
                }

                if (viewModel.state.value.favCoinList?.size == 0) {
                    item {
                        Text(
                            text = "No favorited coin yet...",

                            // Heading / H2 / ExtraBold
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight(800),
                                color = Color(0xFF1B1725),
                            )
                        )
                    }

                }
                items(viewModel.state.value.favCoinList?.size ?: 0) { index ->
                    FavoriteCoinListItem(
                        model = viewModel.state.value.favCoinList?.get(index),
                        onClick = {
                            navHostController!!.navigate(
                                "detail?id=${viewModel.state.value.favCoinList?.get(index)?.id}"
                            )
                        }
                    )

                }
            }
        }


    }
}