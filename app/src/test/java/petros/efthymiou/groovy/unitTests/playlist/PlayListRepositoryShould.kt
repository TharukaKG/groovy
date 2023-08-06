package petros.efthymiou.groovy.unitTests.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.helpers.PlaylistMapper
import petros.efthymiou.groovy.models.PlayListItem
import petros.efthymiou.groovy.services.PlayListService
import petros.efthymiou.groovy.repositories.PlayListRepository
import petros.efthymiou.groovy.services.responses.PlayListRaw
import petros.efthymiou.groovy.unitTests.BaseUnitTest

class PlayListRepositoryShould: BaseUnitTest() {

    private val playListService: PlayListService = mock()
    private val playlist:List<PlayListItem> = mock()
    private val playListRaw:List<PlayListRaw> = mock()
    private val mapper: PlaylistMapper = mock()
    private val exception:Exception = Exception("Something went wrong while getting result")
    private val repository:PlayListRepository = PlayListRepository(playListService, mapper)

    @Test
    fun callPlayListServiceToGetPlayListRaw() = runTest{
        repository.getPlayLists()
        verify(playListService, times(1)).getPlayLists()
    }

    @Test
    fun callPlaylistMapperDelegate() = runTest{
        mockSuccessfulResultFromService()
        repository.getPlayLists().first()
        verify(mapper, times(1)).invoke(playListRaw)
    }

    @Test
    fun emitsPlaylist() = runTest{
        mockSuccessfulResultFromService()
        mockSuccessfulResultFromMapper()
        assertEquals(playlist, repository.getPlayLists().first().getOrNull())
    }

    @Test
    fun propagateErrors() = runTest{
        mockFailingResult()
        assertEquals(exception, playListService.getPlayLists().first().exceptionOrNull())
    }

    private suspend fun mockSuccessfulResultFromService(){
        whenever(playListService.getPlayLists()).thenReturn(
            flow<Result<List<PlayListRaw>>> {
                emit(Result.success(playListRaw))
            }
        )
    }

    private fun mockFailingResult() = runTest{
        whenever(playListService.getPlayLists()).thenReturn(
            flow<Result<List<PlayListRaw>>> {
                emit(Result.failure(exception))
            }
        )
    }

    private fun mockSuccessfulResultFromMapper(){
        whenever(mapper.invoke(playListRaw)).thenReturn(playlist)
    }

}