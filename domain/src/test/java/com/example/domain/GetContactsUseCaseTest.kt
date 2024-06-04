package com.example.domain

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.common.PagingModel
import com.example.domain.entity.ContactsItemEntity
import com.example.domain.repository.ContactsRepository
import com.example.domain.usecase.GetContactsUseCase
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
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class GetContactsUseCaseTest {

    @MockK
    private lateinit var contactsRepository: ContactsRepository

    private lateinit var getContactsUseCase: GetContactsUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        getContactsUseCase = GetContactsUseCase(
            contactsRepository = contactsRepository,
            dispatcherProvider = TestDispatcherProvider()
        )
    }

    @Test
    fun `test get contacts success`() = runTest {

        val contacts = TestDataGenerator.generateContacts()
        val contactsFlow = flowOf(contacts)

        // Given
        coEvery { contactsRepository.getContacts(1) } returns contactsFlow

        // When & Assertions
        val result = getContactsUseCase.execute(params = 1)
        result.test {
            // Expect Offer Items
            val expected = expectItem()
            Truth.assertThat(expected).isEqualTo(contacts)
            expectComplete()
        }

        // Then
        coVerify { contactsRepository.getContacts(1) }

    }

    @Test(expected = Exception::class)
    fun `test get contacts fail`() = runTest {

        val contactsFlow = flow<PagingModel<List<ContactsItemEntity>>> {
            throw Exception()
        }

        // Given
        coEvery { contactsRepository.getContacts(1) } returns contactsFlow

        // When & Assertions
        val result = getContactsUseCase.execute(1)
        result.test {
            // Expect Error
            throw expectError()
        }

        // Then
        coVerify { contactsRepository.getContacts(1) }

    }

}