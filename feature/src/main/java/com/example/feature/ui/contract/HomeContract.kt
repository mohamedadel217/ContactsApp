package com.example.feature.ui.contract

import com.example.base.UiEffect
import com.example.base.UiEvent
import com.example.base.UiState
import com.example.common.PagingModel
import com.example.feature.models.ContactsItemUiModel

class HomeContract {

    sealed class Event : UiEvent {
        object OnRefresh : Event()
        object FetchData : Event()
        object GetFavoriteList : Event()
        object LoadMoreData: Event()
        data class ContactSelected(val contactUiModel: ContactsItemUiModel): Event()
        data class OnFavoriteClicked(val contactUiModel: ContactsItemUiModel?): Event()
    }

    data class State(
        val homeState: HomeState
    ) : UiState

    sealed class HomeState {
        object Idle : HomeState()
        object Empty: HomeState()
        object Loading : HomeState()
        data class Success(val contacts : PagingModel<List<ContactsItemUiModel>>) : HomeState()
    }

    sealed class Effect : UiEffect {

        data class ShowError(val message : String?) : Effect()
        data class NavigateToContactDetails(val contactsUiModel: ContactsItemUiModel): Effect()

    }

}