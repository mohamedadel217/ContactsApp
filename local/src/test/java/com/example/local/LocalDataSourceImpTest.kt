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

}