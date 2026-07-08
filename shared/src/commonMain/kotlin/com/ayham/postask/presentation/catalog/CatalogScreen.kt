package com.ayham.postask.presentation.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ayham.postask.presentation.base.BaseScreen
import com.ayham.postask.presentation.catalog.components.ProductCard
import com.ayham.postask.presentation.theme.Dimens
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CatalogScreen(
    modifier: Modifier = Modifier,
    viewModel: CatalogViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BaseScreen(viewModel = viewModel, modifier = modifier) {
        CatalogContent(state = state, onEvent = viewModel::onEvent)
    }
}

@Composable
private fun CatalogContent(
    state: CatalogState,
    onEvent: (CatalogEvent) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(Dimens.Spacing.M),
        horizontalArrangement = Arrangement.spacedBy(Dimens.Spacing.M),
        verticalArrangement = Arrangement.spacedBy(Dimens.Spacing.M),
    ) {
        items(state.products, key = { it.id }) { product ->
            ProductCard(
                product = product,
                onAddToCart = { onEvent(CatalogEvent.OnAddToCart(product.id)) },
            )
        }
    }
}
