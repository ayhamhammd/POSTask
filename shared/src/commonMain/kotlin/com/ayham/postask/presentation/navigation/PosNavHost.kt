package com.ayham.postask.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ayham.postask.presentation.cart.CartScreen
import com.ayham.postask.presentation.catalog.CatalogScreen
import com.ayham.postask.presentation.orders.OrdersScreen

@Composable
fun PosNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = CatalogRoute,
        modifier = modifier,
    ) {
        composable<CatalogRoute> { CatalogScreen() }
        composable<CartRoute> { CartScreen() }
        composable<OrdersRoute> { OrdersScreen() }
    }
}
