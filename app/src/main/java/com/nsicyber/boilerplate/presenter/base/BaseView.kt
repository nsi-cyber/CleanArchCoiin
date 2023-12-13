package com.nsicyber.boilerplate.presenter.base

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.nsicyber.boilerplate.presenter.base.components.ApiResultDialog
import com.nsicyber.boilerplate.presenter.base.components.DialogType
import com.nsicyber.boilerplate.ui.theme.BoilerplateTheme
import com.nsicyber.boilerplate.presenter.base.components.navHostController
import com.nsicyber.boilerplate.R
import com.nsicyber.boilerplate.presenter.base.components.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BaseView(
    viewModel: BaseViewModel? = null,
    useIsBusy: Boolean = true,
    canGoBack: Boolean = true,
    canScroll: Boolean = true,
    isShowBottomBar: Boolean = false,
    isVerticalPaddingEnabled: Boolean = true,
    onBottomSheetDispose: () -> Unit = {},
    isHorizontalPaddingEnabled: Boolean = true,
    dialogState : Boolean? = false,
    bottomSheetState: Boolean = false,
    bottomSheetContent: @Composable (modalState : ModalBottomSheetState) -> Unit = { ms -> Box(modifier = Modifier.defaultMinSize(minHeight = 1.dp)) {} },
    dialogContent: @Composable ((modalState : ModalBottomSheetState) -> Unit)? = {},
    content: @Composable (state : ModalBottomSheetState) -> Unit,
) {


    if(viewModel?.errorDialogState?.value == true){
        ApiResultDialog(DialogType.ERROR,viewModel.errorDialogContent.value) {
            viewModel.setErrorDialogState(false)
        }
    }


    if(viewModel?.successDialogState?.value == true){
        ApiResultDialog(DialogType.SUCCESS,viewModel.successDialogContent.value) {
            viewModel.setSuccessDialogState(false,"",viewModel.listener)
            viewModel.onSuccessCallback()
        }
    }



    BoilerplateTheme {

        val bottomSheetModalState =
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

        BackPressHandler(enabled = canGoBack, onBackPressed = {
            if (canGoBack) navHostController!!.popBackStack()
        })

        if (!bottomSheetModalState.isVisible) {
            onBottomSheetDispose()
        }
        ModalBottomSheetLayout(
            sheetState = bottomSheetModalState,
            sheetBackgroundColor = Color.White,
            sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
            modifier = Modifier.defaultMinSize(minHeight = 1.dp),
            sheetContent = {
                Column(
                    Modifier
                        .defaultMinSize(minHeight = 100.dp)
                        .fillMaxWidth()) {
                    bottomSheetContent(bottomSheetModalState)

                }
            }) {


            Box {
                Scaffold(
                    bottomBar = { if (isShowBottomBar) BottomNav() },
                    floatingActionButtonPosition = FabPosition.End,
                    isFloatingActionButtonDocked = true,
                    modifier = Modifier.defaultMinSize(minHeight = 1.dp),
                ) { p ->
                    if (viewModel != null && viewModel.isBusy.value && useIsBusy) {
                        Surface(
                            color = Color.White,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(p)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.wrapContentSize(),
                                color = Color.Black
                            )
                        }
                    } else {
                        if (canScroll) {
                            Box(Modifier.fillMaxSize()) {
                                Surface(
                                    color = Color.White,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .background(Color.White)
                                ) {
                                    Surface(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(
                                                horizontal = if (isHorizontalPaddingEnabled) 16.dp else 0.dp,
                                                vertical = if (isVerticalPaddingEnabled) 16.dp else 0.dp
                                            )
                                            .verticalScroll(rememberScrollState()),
                                        color = Color.White,
                                    ) {
                                        //TODO cutout shape padding
                                        Box(
                                            Modifier.padding(
                                                if (isShowBottomBar) p else PaddingValues(
                                                    0.dp
                                                )
                                            )
                                        ) {
                                            content(bottomSheetModalState)
                                        }
                                    }
                                }

                            }
                        } else {
                            Surface(
                                color = Color.White,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.White)
                                    .padding(p)
                            ) {
                                Surface(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(
                                            horizontal = if (isHorizontalPaddingEnabled) 16.dp else 0.dp,
                                            vertical = if (isVerticalPaddingEnabled) 16.dp else 0.dp
                                        ),
                                    color = Color.White,
                                ) {
                                    content(bottomSheetModalState)
                                }
                            }

                        }
                    }
                }
                if(dialogState == true && dialogContent != null){
                    dialogContent(bottomSheetModalState)
                }
            }
        }
    }
}

@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    enabled: Boolean = true,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}


@Composable
fun BottomNav() {
    val items = listOf(
        BottomNavItem.CoinList,
        BottomNavItem.MyCoins,


        )
    BottomAppBar(
        cutoutShape = CircleShape,
        backgroundColor = Color.White,
        contentColor = Color.Black
    ) {
        val navBackStackEntry by navHostController!!.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(

                icon = {
                    Icon(
                        painterResource(id = item.icon ?: R.drawable.ic_close_cross),
                        modifier =
                        Modifier.alpha(if (item.icon != null)
                            1f
                        else
                            0f), contentDescription = ""
                    )
                },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Gray,
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    if(item.screen_route != null){
                        navHostController!!.navigate(item.screen_route!!) {

                            navHostController!!.graph.startDestinationRoute?.let { screen_route ->
                                popUpTo(screen_route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }

                }
            )
        }

    }
}


sealed class BottomNavItem(var icon:Int?, var screen_route:String?){

    object CoinList : BottomNavItem(R.drawable.ic_list, discover)
    object MyCoins: BottomNavItem( R.drawable.ic_fav_list, profile)

}