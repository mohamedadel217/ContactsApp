package com.example.local

import androidx.test.filters.SmallTest
import com.example.common.PagingModel
import com.example.data.repository.LocalDataSource
import com.example.local.database.ContactsDao
import com.example.local.mapper.ContactsLocalDataMapper
import com.example.local.source.LocalDataSourceImp
import com.example.local.utils.TestDataGenerator
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@SmallTest
class LocalDataSourceImpTest {

    @MockK
    private lateinit var contactsDao: ContactsDao
    private val contactsLocalDataMapper = ContactsLocalDataMapper()

    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create RemoteDataSourceImp before every test
        localDataSource = LocalDataSourceImp(
            contactsDao = contactsDao,
            contactsLocalDataMapper = contactsLocalDataMapper,
        )
    }

    @Test
    fun `test get contacts success`() = runTest {

        val contacts = TestDataGenerator.generateContacts()
        val expected = PagingModel(
            data = contactsLocalDataMapper.fromList(contacts),
            total = 1,
            currentPage = 0
        )

        // Given
        coEvery { contactsDao.getAllContacts() } returns contacts

        // When
        val result = localDataSource.getContacts()

        // Then
        coVerify { contactsDao.getAllContacts() }

        // Assertion
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun `test get contacts fail`() = runTest {

        // Given
        coEvery { contactsDao.getAllContacts() } throws Exception()

        // When
        localDataSource.getContacts()

        // Then
        coVerify { contactsDao.getAllContacts() }

    }

    @Test
    fun `test save contacts success`() = runTest {

        val contacts = TestDataGenerator.generateContacts()
        val expected = listOf(1L)

        // Given
        coEvery { contactsDao.addContacts(any()) } returns expected

        // When
        val returned =
            localDataSource.saveContacts(contactsLocalDataMapper.fromList(list = contacts))

        // Then
        coVerify { contactsDao.addContacts(any()) }

        // Assertion
        Truth.assertThat(returned).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun `test save contacts fail`() = runBlockingTest {

        val contacts = TestDataGenerator.generateContacts()

        // Given
        coEvery { contactsDao.addContacts(any()) } throws Exception()

        // When
        localDataSource.saveContacts(contactsLocalDataMapper.fromList(list = contacts))

        // Then
        coVerify { contactsDao.addContacts(any()) }

    }

    @Test
    fun `test get favorite contacts success`() = runTest {

        val contacts = TestDataGenerator.generateContacts()
        val expected = PagingModel(
            data = contactsLocalDataMapper.fromList(contacts),
            total = 6,
            currentPage = 0
        )

        // Given
        coEvery { contactsDao.getAllFavoriteContacts() } returns contacts

        // When
        val result = localDataSource.getFavoriteContacts()

        // Then
        coVerify { contactsDao.getAllFavoriteContacts() }

        // Assertion
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun `test get favorite contacts fail`() = runTest {

        // Given
        coEvery { contactsDao.getAllFavoriteContacts() } throws Exception()

        // When
        localDataSource.getFavoriteContacts()

        // Then
        coVerify { contactsDao.getAllFavoriteContacts() }

    }

    @Test
    fun `test save favorite contact success`() = runTest {

        val contact = TestDataGenerator.generateContacts()[0]
        val expected = 1

        // Given
        coEvery { contactsDao.addFavorite(any()) } returns expected

        // When
        val returned =
            localDataSource.addFavoriteContacts(contactsLocalDataMapper.from(i = contact))

        // Then
        coVerify { contactsDao.addFavorite(any()) }

        // Assertion
        Truth.assertThat(returned).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun `test save favorite contacts fail`() = runTest {

        val contact = TestDataGenerator.generateContacts()[0]

        // Given
        coEvery { contactsDao.addFavorite(any()) } throws Exception()

        // When
        localDataSource.addFavoriteContacts(contactsLocalDataMapper.from(i = contact))

        // Then
        coVerify { contactsDao.addFavorite(any()) }

    }

    @Test
    fun `test clear all contacts success`() = runTest {

        val contacts = TestDataGenerator.generateContacts()
        val expected = contacts.size // Affected row count

        // Given
        coEvery { contactsDao.clearContactsCash() } returns expected

        // When
        val returned = localDataSource.clearContactsCashed()

        // Then
        coVerify { contactsDao.clearContactsCash() }

        // Assertion
        Truth.assertThat(returned).isEqualTo(expected)

    }

    @Test(expected = Exception::class)
    fun `test clear all contacts fail`() = runTest {

        // Given
        coEvery { contactsDao.clearContactsCash() } throws Exception()

        // When
        localDataSource.clearContactsCashed()

        // Then
        coVerify { contactsDao.clearContactsCash() }

    }

}