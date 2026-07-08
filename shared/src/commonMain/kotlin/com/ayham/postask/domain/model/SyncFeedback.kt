package com.ayham.postask.domain.model

sealed interface SyncFeedback {
    data object Retrying : SyncFeedback
    data class Synced(val count: Int) : SyncFeedback
}
