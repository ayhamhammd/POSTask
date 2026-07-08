package com.ayham.postask.presentation.app

import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ayham.postask.presentation.components.app_icon.AppIcon
import com.ayham.postask.presentation.components.app_text.AppText
import com.ayham.postask.presentation.components.app_text.AppTextStyle
import com.ayham.postask.presentation.navigation.CartRoute
import com.ayham.postask.presentation.navigation.CatalogRoute
import com.ayham.postask.presentation.navigation.OrdersRoute
import com.ayham.postask.presentation.theme.PosIcons
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource
import postask.shared.generated.resources.Res
import postask.shared.generated.resources.tab_cart
import postask.shared.generated.resources.tab_catalog
import postask.shared.generated.resources.tab_orders

private class BottomItem(
    val route: Any,
    val routeName: String,
    val icon: ImageVector,
    val label: StringResource,
    val showCartBadge: Boolean = false,
)

@Composable
fun PosBottomBar(
    navController: NavHostController,
    cartCount: Int,
) {
    val items = listOf(
        BottomItem(
            route = CatalogRoute,
            routeName = CatalogRoute.serializer().descriptor.serialName,
            icon = PosIcons.Catalog,
            label = Res.string.tab_catalog,
        ),
        BottomItem(
            route = CartRoute,
            routeName = CartRoute.serializer().descriptor.serialName,
            icon = PosIcons.Cart,
            label = Res.string.tab_cart,
            showCartBadge = true,
        ),
        BottomItem(
            route = OrdersRoute,
            routeName = OrdersRoute.serializer().descriptor.serialName,
            icon = PosIcons.Orders,
            label = Res.string.tab_orders,
        ),
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.routeName,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    if (item.showCartBadge && cartCount > 0) {
                        BadgedBox(badge = {
                            Badge { AppText(text = cartCount.toString(), style = AppTextStyle.Label.Small) }
                        }) {
                            AppIcon(imageVector = item.icon)
                        }
                    } else {
                        AppIcon(imageVector = item.icon)
                    }
                },
                label = { AppText(text = stringResource(item.label), style = AppTextStyle.Label.Medium) },
            )
        }
    }
}
