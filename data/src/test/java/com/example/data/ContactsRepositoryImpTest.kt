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

        // Given
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

}