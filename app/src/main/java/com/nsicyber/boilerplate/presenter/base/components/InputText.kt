package com.nsicyber.boilerplate.presenter.base.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalMinimumTouchTargetEnforcement
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nsicyber.boilerplate.R


@Composable
fun PasswordInputTextField(
    placeholder: String = "",
    modifier: Modifier = Modifier,
    value : String? = "",
    onValueChange: (String) -> Unit,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current
) {
    var isObscure = remember { mutableStateOf(true) }
    InputTextField(
        modifier = modifier,
        isObscure = isObscure.value,
        value = value,
        placeholder = placeholder.ifEmpty { "Şifre" },
        onValueChange = onValueChange,
        color = color, style = style,
        trailingIcon = {
            IconVisibility(
                value = isObscure.value,
                onValueChange = {
                    isObscure.value = it
                }
            )
        }
    )
}

@SuppressLint("SuspiciousIndentation")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InputTextField(
    isObscure: Boolean = false,
    placeholder: String = "",
    value: String? = "",
    maxLines: Int = 1,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    focusManager: FocusManager = LocalFocusManager.current,
    focusRequester: FocusRequester = FocusRequester(),
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    paddingLeadingIconEnd: Dp = 0.dp,
    paddingTrailingIconStart: Dp = 0.dp,
    onValueChange: (String) -> Unit,
    hasError: Boolean = false,
    hasErrorText: String? = "Bu alan boş bırakılamaz",
    semanticContentDescription: String = "",
    leadingIcon: (@Composable() () -> Unit)? = null,
    trailingIcon: (@Composable() () -> Unit)? = null,
    label: (@Composable() () -> Unit)? = null,
    caption: (@Composable() () -> Unit)? = null,
    color: Color = MaterialTheme.colors.onBackground,
    backgroundColor: Color = Color.White,
    cursorColor: Color = Color.Gray,
    focusedBorderColor: Color =  Color.Black,
    unfocusedBorderColor: Color = Color.Gray,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    style: TextStyle = LocalTextStyle.current
) {

    //var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }

    CompositionLocalProvider(LocalMinimumTouchTargetEnforcement provides false) {
        Column() {
            if(caption != null ) caption()
            OutlinedTextField(
                shape = RoundedCornerShape(8.dp),
                textStyle = style,
                modifier = modifier
                    .background(color = Color.White)
                    .fillMaxWidth()
                    .focusRequester(focusRequester = focusRequester)
                    .semantics { contentDescription = semanticContentDescription },
                /*value = if (value == null) textFieldValue else TextFieldValue(
                    value,
                    selection = (TextRange(value.length))
                ),*/
                value = value ?: "",
                enabled = enabled,
                /*onValueChange = {
                    onValueChange(it.text)
                    if (value == null) textFieldValue = it
                },*/
                onValueChange = {
                    onValueChange(it)
                },

                label = label,
                interactionSource = interactionSource,
                placeholder = {

                    Text(
                        modifier = modifier ?: Modifier.fillMaxSize(),
                        text = placeholder,
                        style = style,
                        color = Color.Gray,
                        textAlign = TextAlign.Start
                    )
                },
                maxLines = maxLines,
                keyboardOptions = keyboardOptions,
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),

                leadingIcon = leadingIcon,
                singleLine = true,
                visualTransformation = if (isObscure) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = trailingIcon,
                colors = MyAppTextFieldColors(backgroundColor = backgroundColor, cursorColor = cursorColor, focusedBorderColor = focusedBorderColor, unfocusedBorderColor = unfocusedBorderColor,)
            )
            if(hasError){
                Text(text = hasErrorText?:"Hata")
            }
        }
    }
}


@Composable
fun IconVisibility(
    onValueChange: (Boolean) -> Unit,
    value : Boolean = true,
){
    var id by remember { mutableStateOf(R.drawable.ic_eye) }
    if(value) id = R.drawable.ic_eye else id = R.drawable.ic_eye
    IconButton(modifier = Modifier
        .width(24.dp)
        .height(24.dp),onClick = {
        onValueChange(!value)
    }) {
        Icon(
            painterResource(id = id),
            "",
            tint = Color.Black,
        )
    }
}

@Composable
fun MyAppTextFieldColors(
    textColor: Color = Color.Gray,
    disabledTextColor: Color = Color.Gray,
    backgroundColor: Color = Color.White,
    cursorColor: Color = Color.Gray,
    errorCursorColor: Color = Color.Red,
    unfocusedBorderColor : Color = Color.Gray,
    focusedBorderColor:Color = Color.Black,
    focusedLabelColor:Color = Color.Black,
) = TextFieldDefaults.outlinedTextFieldColors(
    textColor = textColor,
    focusedLabelColor = focusedLabelColor,
    disabledTextColor = disabledTextColor,
    backgroundColor = backgroundColor,
    cursorColor = cursorColor,
    focusedBorderColor = focusedBorderColor,
    unfocusedBorderColor = unfocusedBorderColor,
    errorCursorColor = errorCursorColor,
)