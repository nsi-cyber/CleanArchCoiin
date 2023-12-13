package com.nsicyber.boilerplate.presenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.nsicyber.boilerplate.presenter.base.components.NavigationController
import com.nsicyber.boilerplate.ui.theme.BoilerplateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BoilerplateTheme {
                MainApp()
            }
        }
    }
}


@Composable
fun MainApp() {
    NavigationController()
}