package com.nsicyber.boilerplate.presenter.profile.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.nsicyber.boilerplate.R
import com.nsicyber.boilerplate.common.formatDate
import com.nsicyber.boilerplate.data.remote.entity.CoinEntity

@Composable
fun FavoriteCoinListItem(model: CoinEntity?, onClick: (String?) -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick(model?.id) }
            .border(
                width = 1.dp,
                color = Color(0xFFE2E8F0),
                shape = RoundedCornerShape(size = 12.dp)
            )
            .fillMaxWidth()
            .background(color = Color(0xFFF8FAFC), shape = RoundedCornerShape(size = 12.dp))
            .padding(start = 8.dp, end = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier = Modifier
                        .width(24.dp)
                        .height(24.dp)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE2E8F0),
                            shape = RoundedCornerShape(size = 12.dp)
                        )
                        .background(color = Color.White, shape = RoundedCornerShape(size = 6.dp)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        model = model?.imageUrl,
                        modifier = Modifier
                            .padding(1.dp)
                            .width(24.dp)
                            .height(24.dp)
                            .clip(RoundedCornerShape(size = 12.dp)),

                        contentDescription = "image description",
                        contentScale = ContentScale.Fit
                    )


                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Text(
                        text = "${model?.name} (${model?.symbol}) ",

                        // Body / Medium / bold
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 19.6.sp,

                            fontWeight = FontWeight(700),
                            color = Color(0xFF1B1725),
                        )
                    )



                    Text(
                        text = "Last Price (USD): $ ${model?.currentPrice}",

                        // Body / Medium / bold
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 19.6.sp,
                            fontWeight = FontWeight(700),
                            color = Color(0xFF1B1725),
                        )
                    )


                }


            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(3.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = Modifier
                        .padding(1.dp)
                        .width(15.dp)
                        .height(15.dp),
                    painter = painterResource(id = R.drawable.ic_clock),
                    contentDescription = "image description",
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "Last Update: ${model?.lastUpdateDate?.formatDate()}",

                    // Body / XSmall / medium
                    style = TextStyle(
                        fontSize = 10.sp,
                        lineHeight = 15.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF8E9BAE),
                    )
                )
//
//                if (model?.reloadPerHour != null) {
//
//                    Text(
//                        text = ", update per ${model?.reloadPerHour} hour",
//
//                        // Body / XSmall / medium
//                        style = TextStyle(
//                            fontSize = 10.sp,
//                            lineHeight = 15.sp,
//                            fontWeight = FontWeight(500),
//                            color = Color(0xFF8E9BAE),
//                        )
//                    )
//
//                }
            }

        }





        Image(
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "image description",
            contentScale = ContentScale.None,
            modifier = Modifier
                .padding(1.dp)
                .width(24.dp)
                .height(24.dp)
        )


    }


}