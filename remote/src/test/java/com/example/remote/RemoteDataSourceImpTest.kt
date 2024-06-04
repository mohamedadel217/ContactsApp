package com.example.remote

import androidx.test.filters.SmallTest
import com.example.common.PagingModel
import com.example.data.repository.RemoteDataSource
import com.example.remote.api.ApiService
import com.example.remote.mapper.ContactsNetworkDataMapper
import com.example.remote.source.RemoteDataSourceImp
import com.example.remote.utils.TestDataGenerator
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
class RemoteDataSourceImpTest {

    @MockK
    private lateinit var apiService : ApiService
    private val contactsNetworkDataMapper = ContactsNetworkDataMapper()

    private lateinit var remoteDataSource : RemoteDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create RemoteDataSourceImp before every test
        remoteDataSource = RemoteDataSourceImp(
            apiService = apiService,
            contactsMapper = contactsNetworkDataMapper,
        )
    }

    @Test
    fun `test get news success`() = runTest {

        val contactsNetwork = TestDataGenerator.generateContacts()

        // Given
        coEvery { apiService.getContacts() } returns contactsNetwork

        // When
        val result = remoteDataSource.getContacts()

        // Then
        coVerify { apiService.getContacts() }

        // Assertion
        val list = contactsNetworkDataMapper.fromList(contactsNetwork.results)
        val expected = PagingModel(list, currentPage = 1, total = contactsNetwork.info?.results ?: 0)
        Truth.assertThat(result).isEqualTo(expected)
    }

    @Test(expected = Exception::class)
    fun `test get news fail`() = runTest {

        // Given
        coEvery { apiService.getContacts() } throws Exception()

        // When
        remoteDataSource.getContacts()

        // Then
        coVerify { apiService.getContacts() }

    }

}