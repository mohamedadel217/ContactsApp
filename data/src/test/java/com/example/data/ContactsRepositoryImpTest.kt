package com.example.data

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.common.PagingModel
import com.example.data.mapper.ContactsDataDomainMapper
import com.example.data.repository.ContactsRepositoryImp
import com.example.data.repository.LocalDataSource
import com.example.data.repository.RemoteDataSource
import com.example.data.utils.TestDataGenerator
import com.example.domain.repository.ContactsRepository
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
class ContactsRepositoryImpTest {

    @MockK
    private lateinit var remoteDataSource: RemoteDataSource

    @MockK
    private lateinit var localDataSource: LocalDataSource

    private val contactsMapper = ContactsDataDomainMapper()

    private lateinit var contactsRepository: ContactsRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create RepositoryImp before every test
        contactsRepository = ContactsRepositoryImp(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            contactsMapper = contactsMapper
        )
    }

    @Test
    fun `test get contacts remote success`() = runTest {

        val pagingModel = TestDataGenerator.generateContacts()
        val affectedIds = MutableList(pagingModel.data.size) { index -> index.toLong() }

        // Given
        coEvery { remoteDataSource.getContacts() } returns pagingModel
        coEvery { localDataSource.saveContacts(pagingModel.data) } returns affectedIds
        coEvery { remoteDataSource.getContacts() } returns pagingModel

        // When & Assertions
        val flow = contactsRepository.getContacts(1)
        flow.test {
            // Expect Offer Items
            val expected = expectItem()
            Truth.assertThat(expected)
                .isEqualTo(
                    PagingModel(
                        contactsMapper.fromList(pagingModel.data),
                        total = 6,
                        currentPage = 1
                    )
                )
            expectComplete()
        }

        // Then
        coVerify { remoteDataSource.getContacts() }
    }

    @Test
    fun `test get data from local when remote fails`() = runTest {

        val pagingModel = TestDataGenerator.generateContacts()

        // Given
        coEvery { remoteDataSource.getContacts() } throws Exception()
        coEvery { localDataSource.getContacts() } returns pagingModel

        // When & Assertions
        val flow = contactsRepository.getContacts(1)
        flow.test {
            // Expect Offer Items
            val expected = expectItem()
            Truth.assertThat(expected)
                .isEqualTo(
                    PagingModel(
                        contactsMapper.fromList(pagingModel.data),
                        total = 6,
                        currentPage = 1
                    )
                )
            expectComplete()
        }

        // Then
        coVerify { remoteDataSource.getContacts() }
        coVerify { localDataSource.getContacts() }

    }

    @Test(expected = Exception::class)
    fun `test get error when remote fail and local fail`() = runTest {

        // Given
        coEvery { remoteDataSource.getContacts() } throws Exception()
        coEvery { localDataSource.getContacts() } throws Exception()

        // When & Assertions
        val flow = contactsRepository.getContacts(1)
        flow.test {
            // Expect Error
            throw expectError()
        }

        // Then
        coVerify { remoteDataSource.getContacts() }
    }

    @Test
    fun `test get favorite contact from local `() = runTest {

        val pagingModel = TestDataGenerator.generateContacts()

        // Given
        coEvery { localDataSource.getFavoriteContacts() } returns pagingModel

        // When & Assertions
        val flow = contactsRepository.getFavoriteContacts(1)
        flow.test {
            // Expect Offer Items
            val expected = expectItem()
            Truth.assertThat(expected)
                .isEqualTo(
                    PagingModel(
                        contactsMapper.fromList(pagingModel.data),
                        total = 6,
                        currentPage = 1
                    )
                )
            expectComplete()
        }

        // Then
        coVerify { localDataSource.getFavoriteContacts() }

    }

    @Test(expected = Exception::class)
    fun `test get error when get favorite contacts fail`() = runTest {

        // Given
        coEvery { localDataSource.getFavoriteContacts() } throws Exception()

        // When & Assertions
        val flow = contactsRepository.getFavoriteContacts(1)
        flow.test {
            // Expect Error
            throw expectError()
        }

        // Then
        coVerify { localDataSource.getFavoriteContacts() }
    }

    @Test
    fun `test save favorite contact success `() = runTest {

        val favoriteContact = TestDataGenerator.generateContacts().data[0]

        // Given
        coEvery { localDataSource.addFavoriteContacts(any()) } returns 1

        // When & Assertions
        val result = contactsRepository.saveFavoriteContact(contactsMapper.from(favoriteContact))
        Truth.assertThat(result).isEqualTo(1)


        // Then
        coVerify { localDataSource.addFavoriteContacts(any()) }

    }

    @Test(expected = Exception::class)
    fun `test save favorite contact fail`() = runTest {
        val favoriteContact = TestDataGenerator.generateContacts().data[0]

        // Given
        coEvery { localDataSource.addFavoriteContacts(any()) } throws Exception()

        // When & Assertions
        val result = contactsRepository.saveFavoriteContact(contactsMapper.from(favoriteContact))
        Truth.assertThat(result).isEqualTo(0)

        // Then
        coVerify { localDataSource.addFavoriteContacts(any()) }
    }

}