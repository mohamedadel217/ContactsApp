package com.example.feature

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.common.PagingModel
import com.example.domain.entity.ContactsItemEntity
import com.example.domain.usecase.GetContactsUseCase
import com.example.domain.usecase.GetFavoriteContactsUseCase
import com.example.domain.usecase.SaveFavoriteContactUseCase
import com.example.feature.mapper.ContactsDomainUiMapper
import com.example.feature.ui.contract.HomeContract
import com.example.feature.ui.vm.HomeViewModel
import com.example.feature.utils.TestDataGenerator
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class HomeViewModelTest {

    private val dispatcher = StandardTestDispatcher()

    @MockK
    private lateinit var getContactsUseCase: GetContactsUseCase

    @MockK
    private lateinit var getFavoriteContactsUseCase: GetFavoriteContactsUseCase

    @MockK
    private lateinit var saveFavoriteContactUseCase: SaveFavoriteContactUseCase

    private val contactsMapper = ContactsDomainUiMapper()

    private lateinit var homeViewModel: HomeViewModel


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        Dispatchers.setMain(dispatcher)
        // Create HomeViewModel before every test
        homeViewModel = HomeViewModel(
            getContactsUseCase = getContactsUseCase,
            contactsMapper = contactsMapper,
            getFavoriteContactsUseCase = getFavoriteContactsUseCase,
            saveFavoriteContactUseCase = saveFavoriteContactUseCase
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test fetch data success`() = runTest {

        val contactsItems = TestDataGenerator.generateContacts()
        val contactsFlow = flowOf(contactsItems)

        // Given
        coEvery { getContactsUseCase.execute(1) } returns contactsFlow

        // When && Assertions
        homeViewModel.uiState.test {
            // Expect Idle from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                HomeContract.State(
                    homeState = HomeContract.HomeState.Idle
                )
            )
            homeViewModel.setEvent(HomeContract.Event.FetchData)
            // Expect Loading
            Truth.assertThat(expectItem()).isEqualTo(
                HomeContract.State(
                    homeState = HomeContract.HomeState.Loading
                )
            )
            // Expect Success
            val expected = expectItem()
            val uiModels = contactsMapper.fromList(contactsItems.data)
            Truth.assertThat(expected).isEqualTo(
                HomeContract.State(
                    homeState = HomeContract.HomeState.Success(
                        contacts = PagingModel(
                            uiModels,
                            contactsItems.total,
                            contactsItems.currentPage
                        ),
                    )
                )
            )

            val expectedData =
                (expected.homeState as HomeContract.HomeState.Success).contacts
            Truth.assertThat(expectedData)
                .isEqualTo(PagingModel(uiModels, contactsItems.total, contactsItems.currentPage))

            val models = (expected.homeState as HomeContract.HomeState.Success).contacts.data
            Truth.assertThat(
                models
            ).containsExactlyElementsIn(uiModels)


            //Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }


        // Then
        coVerify { getContactsUseCase.execute(1) }
    }

    @Test
    fun `test fetch data fail`() = runTest {
        val contactsFlow = flow<PagingModel<List<ContactsItemEntity>>> {
            throw Exception("error string")
        }

        // Given
        coEvery { getContactsUseCase.execute(1) } returns contactsFlow

        // When && Assertions
        homeViewModel.uiState.test {
            // Expect Idle from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                HomeContract.State(
                    homeState = HomeContract.HomeState.Idle
                )
            )
            homeViewModel.setEvent(HomeContract.Event.FetchData)
            // Expect Loading
            Truth.assertThat(expectItem()).isEqualTo(
                HomeContract.State(
                    homeState = HomeContract.HomeState.Loading
                )
            )
            //Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }

        // When && Assertions (UiEffect)
        homeViewModel.effect.test {
            // Expect ShowError Effect
            val expected = expectItem()
            val expectedData = (expected as HomeContract.Effect.ShowError).message
            Truth.assertThat(expected).isEqualTo(
                HomeContract.Effect.ShowError("error string")
            )
            Truth.assertThat(expectedData).isEqualTo("error string")
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }


        // Then
        coVerify { getContactsUseCase.execute(1) }
    }

    @Test
    fun `test fetch data success using pull to refresh`() = runTest {

        val contactsItems = TestDataGenerator.generateContacts()
        val contactsFlow = flowOf(contactsItems)

        // Given
        coEvery { getContactsUseCase.execute(1) } returns contactsFlow

        // When && Assertions
        homeViewModel.uiState.test {
            // Expect Idle from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                HomeContract.State(
                    homeState = HomeContract.HomeState.Idle
                )
            )
            homeViewModel.setEvent(HomeContract.Event.OnRefresh)
            // Expect Loading
            Truth.assertThat(expectItem()).isEqualTo(
                HomeContract.State(
                    homeState = HomeContract.HomeState.Loading
                )
            )
            // Expect Success
            val expected = expectItem()
            val uiModels = contactsMapper.fromList(contactsItems.data)
            Truth.assertThat(expected).isEqualTo(
                HomeContract.State(
                    homeState = HomeContract.HomeState.Success(
                        contacts = PagingModel(
                            uiModels,
                            contactsItems.total,
                            contactsItems.currentPage
                        ),
                    )
                )
            )

            val expectedData =
                (expected.homeState as HomeContract.HomeState.Success).contacts
            Truth.assertThat(expectedData)
                .isEqualTo(PagingModel(uiModels, contactsItems.total, contactsItems.currentPage))

            val models = (expected.homeState as HomeContract.HomeState.Success).contacts.data
            Truth.assertThat(
                models
            ).containsExactlyElementsIn(uiModels)


            //Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }


        // Then
        coVerify { getContactsUseCase.execute(1) }
    }

    @Test
    fun `test fetch data success using load more`() = runTest {
        val contactsItems = TestDataGenerator.generateContacts()
        val contactsFlow = flowOf(contactsItems)
        val contactsItemsPage2 = TestDataGenerator.generateContacts(2)

        // Given
        coEvery { getContactsUseCase.execute(1) } returns contactsFlow
        coEvery { getContactsUseCase.execute(2) } returns flowOf(contactsItemsPage2)

        // When && Assertions
        homeViewModel.uiState.test {
            // Expect Idle from initial state
            Truth.assertThat(expectItem()).isEqualTo(
                HomeContract.State(
                    homeState = HomeContract.HomeState.Idle
                )
            )
            homeViewModel.setEvent(HomeContract.Event.FetchData)
            // Expect Loading
            Truth.assertThat(expectItem()).isEqualTo(
                HomeContract.State(
                    homeState = HomeContract.HomeState.Loading
                )
            )
            // Expect Success
            val expected = expectItem()
            val uiModels = contactsMapper.fromList(contactsItems.data)
            Truth.assertThat(expected).isEqualTo(
                HomeContract.State(
                    homeState = HomeContract.HomeState.Success(
                        contacts = PagingModel(
                            uiModels,
                            contactsItems.total,
                            contactsItems.currentPage
                        ),
                    )
                )
            )

            val expectedData =
                (expected.homeState as HomeContract.HomeState.Success).contacts
            Truth.assertThat(expectedData)
                .isEqualTo(
                    PagingModel(
                        uiModels,
                        contactsItems.total,
                        contactsItems.currentPage
                    )
                )

            val models = (expected.homeState as HomeContract.HomeState.Success).contacts.data
            Truth.assertThat(
                models
            ).containsExactlyElementsIn(uiModels)

            homeViewModel.setEvent(HomeContract.Event.LoadMoreData)
            // Expect Success
            val expectedPageTwo = expectItem()
            Truth.assertThat(expectedPageTwo).isEqualTo(
                HomeContract.State(
                    homeState = HomeContract.HomeState.Success(
                        contacts = PagingModel(
                            uiModels ,
                            contactsItemsPage2.total,
                            contactsItemsPage2.currentPage
                        )
                    )
                )
            )

            val modelsPageTwo = (expected.homeState as HomeContract.HomeState.Success).contacts
            Truth.assertThat(modelsPageTwo)
                .isEqualTo(
                    PagingModel(
                        uiModels ,
                        contactsItems.total,
                        contactsItems.currentPage
                    )
                )

            Truth.assertThat(
                modelsPageTwo.data
            ).containsExactlyElementsIn( uiModels)


            //Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }


        // Then
        coVerify { getContactsUseCase.execute(1) }
        coVerify { getContactsUseCase.execute(2) }
    }

    @Test
    fun `test select contact item`() = runTest {

        val contacts = TestDataGenerator.generateContacts()
        val selectedContactUiModel = contactsMapper.from(contacts.data.firstOrNull())

        // Given (no-op)

        // When && Assertions
        homeViewModel.event.test {
            homeViewModel.setEvent(HomeContract.Event.ContactSelected(contactUiModel = selectedContactUiModel))

            Truth.assertThat(expectItem()).isEqualTo(
                HomeContract.Event.ContactSelected(selectedContactUiModel)
            )
            // Cancel and ignore remaining
            cancelAndIgnoreRemainingEvents()
        }

        homeViewModel.effect.test {
            Truth.assertThat(expectItem()).isEqualTo(
                HomeContract.Effect.NavigateToContactDetails(selectedContactUiModel)
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

}
