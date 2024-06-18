package com.example.domain

import androidx.test.filters.SmallTest
import com.example.domain.repository.ContactsRepository
import com.example.domain.usecase.SaveFavoriteContactUseCase
import com.example.domain.utils.TestDataGenerator
import com.example.domain.utils.TestDispatcherProvider
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
class SaveFavoriteUseCaseTest {

    @MockK
    private lateinit var contactsRepository: ContactsRepository

    private lateinit var saveFavoriteContactUseCase: SaveFavoriteContactUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        saveFavoriteContactUseCase = SaveFavoriteContactUseCase(
            contactsRepository = contactsRepository,
            dispatcherProvider = TestDispatcherProvider()
        )
    }

    @Test
    fun `test save favorite contacts success`() = runTest {

        val favoriteContact = TestDataGenerator.generateContacts().data[0]

        // Given
        coEvery { contactsRepository.saveFavoriteContact(favoriteContact) } returns 1

        // When & Assertions
        val result = saveFavoriteContactUseCase.execute(favoriteContact)
        Truth.assertThat(result).isEqualTo(1)

        // Then
        coVerify { contactsRepository.saveFavoriteContact(favoriteContact) }

    }

    @Test(expected = Exception::class)
    fun `test save favorite contacts fail`() = runTest {
        val favoriteContact = TestDataGenerator.generateContacts().data[0]

        // Given
        coEvery { saveFavoriteContactUseCase.execute(favoriteContact) } returns 0

        // When & Assertions
        val result = saveFavoriteContactUseCase.execute(favoriteContact)
        Truth.assertThat(result).isEqualTo(0)

        // Then
        coVerify { saveFavoriteContactUseCase.execute(favoriteContact) }

    }

}