package com.nsicyber.boilerplate.presenter.discover.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nsicyber.boilerplate.R
import com.nsicyber.boilerplate.data.remote.dto.CoinListDto


@Composable
fun CoinListItem(model: CoinListDto?, onClick: (String?) -> Unit) {
    Row(
        modifier = Modifier
            .clickable {
                onClick(model?.id)
            }
            .border(
                width = 1.dp,
                color = Color(0xFFE2E8F0),
                shape = RoundedCornerShape(size = 8.dp)
            )
            .fillMaxWidth()
            .background(color = Color(0xFFF8FAFC), shape = RoundedCornerShape(size = 8.dp))
            .padding(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Text(
            text = "${model?.name} (${model?.symbol})",

            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 19.6.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF8E9BAE),
            )
        )


        Image(
            modifier = Modifier
                .padding(1.dp)
                .width(24.dp)
                .height(24.dp),
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "image description",
            contentScale = ContentScale.Fit
        )


    }

}