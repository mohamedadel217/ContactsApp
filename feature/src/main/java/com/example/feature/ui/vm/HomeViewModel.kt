package com.example.feature.ui.vm

import androidx.lifecycle.viewModelScope
import com.example.base.BaseViewModel
import com.example.common.Mapper
import com.example.common.PagingModel
import com.example.domain.entity.ContactsItemEntity
import com.example.domain.usecase.GetContactsUseCase
import com.example.domain.usecase.GetFavoriteContactsUseCase
import com.example.domain.usecase.SaveFavoriteContactUseCase
import com.example.feature.models.ContactsItemUiModel
import com.example.feature.ui.contract.HomeContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase,
    private val getFavoriteContactsUseCase: GetFavoriteContactsUseCase,
    private val saveFavoriteContactUseCase: SaveFavoriteContactUseCase,
    private val contactsMapper: Mapper<ContactsItemEntity, ContactsItemUiModel>,
) : BaseViewModel<HomeContract.Event, HomeContract.State, HomeContract.Effect>() {

    private val _contacts = MutableStateFlow<List<ContactsItemUiModel>>(emptyList())
    var isFavoriteListShown: Boolean = false

    override fun createInitialState(): HomeContract.State {
        return HomeContract.State(
            homeState = HomeContract.HomeState.Idle
        )
    }

    override fun handleEvent(event: HomeContract.Event) {
        when (event) {
            is HomeContract.Event.OnRefresh, is HomeContract.Event.FetchData -> {
                fetchData()
            }

            HomeContract.Event.LoadMoreData -> {
                loadMore()
            }

            is HomeContract.Event.ContactSelected -> {
                setEffect { HomeContract.Effect.NavigateToContactDetails(event.contactUiModel) }
            }

            is HomeContract.Event.OnFavoriteClicked -> {
                handleFavoriteClick(event.contactUiModel)
            }

            is HomeContract.Event.GetFavoriteList -> {
                getFavoriteList()
            }
        }
    }

    private fun getFavoriteList(page: Int = 1) {
        viewModelScope.launch {
            if (!isFavoriteListShown) {
                isFavoriteListShown = true
                getFavoriteContactsUseCase.execute(page)
                    .collect { pagingModel ->
                        val favoriteList = contactsMapper.fromList(pagingModel.data)
                        setState {
                            copy(
                                homeState = HomeContract.HomeState.Success(
                                    contacts = PagingModel(
                                        favoriteList,
                                        favoriteList.size,
                                        pagingModel.currentPage
                                    )
                                )
                            )
                        }
                    }
            } else {
                isFavoriteListShown = false
                setState {
                    copy(
                        homeState = HomeContract.HomeState.Success(
                            contacts = PagingModel(
                                _contacts.value,
                                _contacts.value.size,
                                page
                            )
                        )
                    )
                }
            }

        }
    }

    private fun handleFavoriteClick(contactUiModel: ContactsItemUiModel?) {
        viewModelScope.launch {
            _contacts.update {
                it.map { contact ->
                    if (contact.login.uuid == contactUiModel?.login?.uuid) {
                        contact.copy(isFavorite = !contact.isFavorite)
                    } else contact
                }
            }
            saveFavoriteContactUseCase.execute(contactsMapper.to(contactUiModel?.copy(isFavorite = !contactUiModel.isFavorite)))
            setState {
                copy(
                    homeState = HomeContract.HomeState.Success(
                        contacts = PagingModel(
                            _contacts.value,
                            _contacts.value.size,
                            1
                        )
                    )
                )
            }

        }
    }

    private fun fetchData(page: Int = 1) {
        viewModelScope.launch {
            getContactsUseCase.execute(page)
                .onStart {
                    if (page == 1) setState { copy(homeState = HomeContract.HomeState.Loading) }
                }
                .catch {
                    // Set Effect
                    setEffect { HomeContract.Effect.ShowError(message = it.message) }
                    setState { copy(homeState = HomeContract.HomeState.Idle) }
                }
                .collect { pagingModel ->
                    val currentNewsList = contactsMapper.fromList(pagingModel.data)
                    _contacts.update {
                        currentNewsList
                    }
                    setState {
                        copy(
                            homeState = if (_contacts.value.isNotEmpty()) HomeContract.HomeState.Success(
                                contacts = PagingModel(
                                    _contacts.value,
                                    pagingModel.total,
                                    pagingModel.currentPage
                                )
                            ) else HomeContract.HomeState.Empty
                        )
                    }
                }
        }
    }

    private fun loadMore() {
        when (val state = currentState.homeState) {
            is HomeContract.HomeState.Success -> {
                if (state.contacts.total > _contacts.value.size)
                    fetchData(state.contacts.currentPage + 1)
            }

            else -> return
        }
    }

}