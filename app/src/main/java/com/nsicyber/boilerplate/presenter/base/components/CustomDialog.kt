package com.nsicyber.boilerplate.presenter.base.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.nsicyber.boilerplate.R


@Composable
fun CustomDialogPopUp(
    modifier: Modifier = Modifier,
    positiveButtonText: String = "",
    negativeButtonText: String = "",
    isPositiveButtonEnabled: Boolean = false,
    isNegativeButtonEnabled: Boolean = false,
    onDialogPositiveButtonClicked: (() -> Unit)? = null,
    onDialogNegativeButtonClicked: (() -> Unit)? = null,
    onDismissRequest: (() -> Unit)? = null,
    content: @Composable () -> Unit,
    dialogProperties: DialogProperties = DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = false
    ),
    title: @Composable () -> Unit = { null },
) {
    AlertDialog(
        onDismissRequest = {
            onDismissRequest?.invoke()
        },
        buttons = {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .clip(RoundedCornerShape(24.dp)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                if (title != null) Box(modifier = Modifier.wrapContentHeight()) { title() }

                content()

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    if (negativeButtonText.isNotEmpty()) {
                        CustomButtonWide(
                            modifier = Modifier.weight(1f),
                            text = negativeButtonText,
                            elevation = 0.dp,
                            color = Color.Gray,
                            border = BorderStroke(1.dp, Color.Gray),
                            backgroundColor = Color.White,
                            onClick = {
                                onDialogNegativeButtonClicked?.let { it() }
                            },
                        )
                    }
                    if (positiveButtonText.isNotEmpty() && negativeButtonText.isNotEmpty())
                        Spacer(modifier = Modifier.width(8.dp))
                    if (positiveButtonText.isNotEmpty())
                        CustomButtonWide(
                            modifier = Modifier.weight(1f),
                            text = positiveButtonText,
                            elevation = 0.dp,
                            color = Color.White,
                            border = BorderStroke(0.dp, Color.Transparent),
                            backgroundColor = if (isPositiveButtonEnabled) Color.Black else Color.Gray,
                            onClick = {
                                onDialogPositiveButtonClicked?.let { it() }
                            },
                        )
                }
            }
        },
        properties = dialogProperties,
        modifier = modifier
    )
}

@Composable
fun PreviewDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: (() -> Unit)? = null,
    content: @Composable () -> Unit,
    dialogProperties: DialogProperties = DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = true,
        usePlatformDefaultWidth = false,
        decorFitsSystemWindows = true
    ),
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        Dialog(
            onDismissRequest = {
                onDismissRequest?.invoke()
            },
            content = {
                Box(
                    Modifier
                    .fillMaxSize()
                    .noRippleClickable { onDismissRequest?.invoke() }
                    .background(Color.Transparent)) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close_cross),
                        contentDescription = "avatar",
                        tint = Color.White,
                        modifier = Modifier
                            .noRippleClickable {
                                onDismissRequest?.invoke()
                            }
                            .align(Alignment.TopEnd)
                            .padding(16.dp)
                            .size(24.dp)
                    )
                    Box(
                        Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                    ) {
                        content()
                    }
                }
            },
            properties = dialogProperties
        )
    }
}

@Composable
fun CustomButtonWide(text : String = "",
                     modifier: Modifier = Modifier
                         .fillMaxWidth()
                         .wrapContentHeight(),
                     onClick: () -> Unit,
                     padding : Dp = 15.dp,
                     leadingIcon : (@Composable () -> Unit)? = null,
                     suffixIcon : (@Composable () -> Unit)? = null,
                     isBusy : Boolean = false,
                     enabled : Boolean = true,
                     color: Color = Color.Unspecified,
                     elevation: Dp = 4.dp,
                     border : BorderStroke? = null,
                     withSpace : Boolean = false,
                     backgroundColor: Color = Color.Black,
                     style: TextStyle = LocalTextStyle.current){
    Row(modifier = modifier.noRippleClickable {
        if(!isBusy && enabled){
            onClick()
        }
    },){
        val localDensity = LocalDensity.current
        var heightIs by remember {
            mutableStateOf(0.dp)
        }
        CustomCard(
            border = border ?: BorderStroke(1.dp,Color.Black),
            shape = RoundedCornerShape(0.dp),
            elevation = elevation,
            backgroundColor = backgroundColor){
            Row(
                modifier
                    .fillMaxWidth()
                    .height(intrinsicSize = IntrinsicSize.Min)
                    .padding(padding),verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,) {
                if(leadingIcon != null) leadingIcon()
                if(withSpace) Spacer(modifier = Modifier.weight(1f))
                if(isBusy){
                    CircularProgressIndicator(color = color, modifier = Modifier
                        .height(heightIs)
                        .aspectRatio(1f))
                }
                else{
                    Text(text,color = color,style = style,textAlign = TextAlign.Center, modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = 5.dp)
                        .onGloballyPositioned { coordinates ->
                            heightIs = with(localDensity) { coordinates.size.height.toDp() }
                        })
                }
                if(withSpace) Spacer(modifier = Modifier.weight(1f))
                if(suffixIcon != null) suffixIcon()
            }
        }
    }
}



fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null, interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    backgroundColor: Color = Color.Gray,
    contentColor: Color = contentColorFor(backgroundColor),
    border: BorderStroke? = null,
    elevation: Dp? = 0.dp,
    content: @Composable () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        border = border,
        elevation = elevation ?: 0.dp
    ){ content() }
}