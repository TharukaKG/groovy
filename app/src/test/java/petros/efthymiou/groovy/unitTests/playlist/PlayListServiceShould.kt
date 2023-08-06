package petros.efthymiou.groovy.unitTests.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.services.PlayListClients.PLayListClient
import petros.efthymiou.groovy.services.PlayListService
import petros.efthymiou.groovy.services.responses.PlayListRaw
import java.lang.RuntimeException

class PlayListServiceShould {

    private val playListClient: PLayListClient = mock()
    private val playList:List<PlayListRaw> = mock()

    private val service:PlayListService = PlayListService(playListClient)

    @Test
    fun callGetAllPlayListAPI()  = runTest{
        service.getPlayLists().collectLatest {
            verify(playListClient, times(1)).getAllPlayLists()
        }
    }

    @Test
    fun getListOfPlayListsSuccessfully() = runTest {

        mockSuccessfulCase()

        assertEquals(Result.success(playList), service.getPlayLists().first())
    }

    private suspend fun mockSuccessfulCase() {
        whenever(playListClient.getAllPlayLists()).thenReturn(playList)
    }

    @Test
    fun getListOfPlayListsWithError() = runTest {

        mockFailingCase()

        assertEquals(
            "Something went wrong",
            service.getPlayLists().first().exceptionOrNull()?.message
        )
    }

    private suspend fun mockFailingCase() {
        whenever(playListClient.getAllPlayLists()).thenThrow(
            RuntimeException("Damn Backend Devs")
        )
    }

}