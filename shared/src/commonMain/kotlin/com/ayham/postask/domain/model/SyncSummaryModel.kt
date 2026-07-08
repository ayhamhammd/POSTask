package com.ayham.postask.domain.model

data class SyncSummaryModel(
    val syncedCount: Int,
    val failedCount: Int,
) {
    val attempted: Int get() = syncedCount + failedCount
}
