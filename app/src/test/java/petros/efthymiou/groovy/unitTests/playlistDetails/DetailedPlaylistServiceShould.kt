package petros.efthymiou.groovy.unitTests.playlistDetails

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import petros.efthymiou.groovy.models.PlayListDetails
import petros.efthymiou.groovy.services.DetailedPlaylistService
import petros.efthymiou.groovy.services.PlayListClients.PLayListClient
import petros.efthymiou.groovy.unitTests.BaseUnitTest

class DetailedPlaylistServiceShould: BaseUnitTest() {

    private val playlistId: String = "001"
    private val exception:RuntimeException = RuntimeException("Damn Backend Devs")

    private lateinit var service: DetailedPlaylistService
    private val client:PLayListClient = mock()
    private val playlistDetails: PlayListDetails = mock()

    @Test
    fun callDetailedPlaylistService() = runTest{
        service = DetailedPlaylistService(client)
        service.getDetailedPlayList(playlistId).first()
        verify(client, times(1)).getPlayListDetails(playlistId)
    }

    @Test
    fun emitPlaylistDetails() = runTest {
        mockSuccessfulCase()
        service.getDetailedPlayList(playlistId).first {
            assertEquals(playlistDetails, it.getOrNull())
            true
        }
    }

    @Test
    fun emitErrorOnFailing() = runTest {
        mockFailingCase()
        service.getDetailedPlayList(playlistId).first {
            assertEquals("Something went wrong", it.exceptionOrNull()?.message)
            true
        }
    }

    private suspend fun mockFailingCase() {
        service = DetailedPlaylistService(client)
        whenever(client.getPlayListDetails(playlistId)).thenThrow(exception)
    }

    private suspend fun mockSuccessfulCase() {
        service = DetailedPlaylistService(client)
        whenever(client.getPlayListDetails(playlistId)).thenReturn(playlistDetails)
    }

}