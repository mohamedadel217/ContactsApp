package com.example.domain

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.common.PagingModel
import com.example.domain.entity.ContactsItemEntity
import com.example.domain.repository.ContactsRepository
import com.example.domain.usecase.GetFavoriteContactsUseCase
import com.example.domain.utils.TestDataGenerator
import com.example.domain.utils.TestDispatcherProvider
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class GetFavoriteUseCaseTest {

    @MockK
    private lateinit var contactsRepository: ContactsRepository

    private lateinit var getFavoriteContactsUseCase: GetFavoriteContactsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        getFavoriteContactsUseCase = GetFavoriteContactsUseCase(
            contactsRepository = contactsRepository,
            dispatcherProvider = TestDispatcherProvider()
        )
    }

    @Test
    fun `test get favorite success`() = runTest {

        val contacts = TestDataGenerator.generateContacts()
        val contactsFlow = flowOf(contacts)

        // Given
        coEvery { contactsRepository.getFavoriteContacts(1) } returns contactsFlow

        // When & Assertions

        withContext(TestDispatcherProvider().default()) {

            val result = getFavoriteContactsUseCase.execute(params = 1)
            result.test {
                // Expect Items
                val expected = expectItem()
                Truth.assertThat(expected).isEqualTo(contacts)
                expectComplete()
            }
        }

        // Then
        coVerify { contactsRepository.getFavoriteContacts(1) }

    }

    @Test(expected = Exception::class)
    fun `test get favorite contacts fail`() = runTest {

        val contactsFlow = flow<PagingModel<List<ContactsItemEntity>>> {
            throw Exception()
        }

        // Given
        coEvery { contactsRepository.getFavoriteContacts(1) } returns contactsFlow

        // When & Assertions
        val result = getFavoriteContactsUseCase.execute(1)
        result.test {
            // Expect Error
            throw expectError()
        }

        // Then
        coVerify { contactsRepository.getContacts(1) }

    }

}