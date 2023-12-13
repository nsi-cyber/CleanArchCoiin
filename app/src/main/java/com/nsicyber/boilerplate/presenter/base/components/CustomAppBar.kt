package com.nsicyber.boilerplate.presenter.base.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nsicyber.boilerplate.R
@Composable
fun CustomAppBar(
    suffixIcon: (@Composable() () -> Unit)? = null,
    isTransparent: Boolean = false,
    isBackEnable: Boolean = true,
    logo: (@Composable() () -> Unit)? = null,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        if (isBackEnable) Box(Modifier.align(Alignment.CenterStart)) {
            CustomCard(
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(size = 8.dp))
                    ,
                backgroundColor = Color(0x8064748B)
            ) {
                IconButton(modifier=Modifier.padding(start = 13.dp, top = 13.dp, end = 13.dp, bottom = 13.dp),onClick = { navHostController!!.popBackStack() }) {
                    Icon(
                        painterResource(id = R.drawable.ic_arrow_back_black),
                        "",
                        tint = Color.Black,
                    )
                }
            }
        }
        if (logo != null) Box(Modifier.align(Alignment.Center)) {
            logo()
        }
        if (suffixIcon != null) Box(Modifier.align(Alignment.CenterEnd)) {
            suffixIcon()
        }

    }
}