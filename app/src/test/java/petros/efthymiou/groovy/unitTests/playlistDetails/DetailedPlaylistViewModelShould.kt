package petros.efthymiou.groovy.unitTests.playlistDetails

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import petros.efthymiou.groovy.models.PlayListDetails
import petros.efthymiou.groovy.services.DetailedPlaylistService
import petros.efthymiou.groovy.unitTests.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest
import petros.efthymiou.groovy.viewModels.DetailedPlayListViewModel

class DetailedPlaylistViewModelShould: BaseUnitTest() {

    private val playListId: String = "001"
    lateinit var viewModel: DetailedPlayListViewModel

    private val service: DetailedPlaylistService = mock()
    private val playListDetails:PlayListDetails = mock()
    private val exception:Exception = Exception("Something went wrong")


    @Test
    fun switchPlaylistId() {

        viewModel = DetailedPlayListViewModel(service)

        viewModel.getPlayListDetails(playListId)
        viewModel.playListId.captureValues {
            viewModel.playListId.getValueForTest()
            assertEquals(playListId, values[0])
        }
    }

    @Test
    fun callToGetPlaylistDetails() = runTest {

        viewModel = DetailedPlayListViewModel(service)

        viewModel.getPlayListDetails(playListId)
        viewModel.playlistDetails.getValueForTest()
        verify(service, times(1)).getDetailedPlayList(playListId)
    }

    @Test
    fun emitPlayListDetails() = runTest {
        mockSuccessFulCase()
        viewModel.playlistDetails.captureValues {
            viewModel.getPlayListDetails(playListId)
            viewModel.playlistDetails.getValueForTest()
            assertEquals(Result.success(playListDetails), values[0])
        }
    }

    @Test
    fun emitErrorOnFailure() = runTest {
        mockFailingCase()
        viewModel.getPlayListDetails(playListId)
        assertEquals(Result.failure<Exception>(exception), viewModel.playlistDetails.getValueForTest())
    }

    @Test
    fun postLoadingTrue() {
        viewModel = DetailedPlayListViewModel(service)
        viewModel.loading.captureValues {
            viewModel.loading.getValueForTest()
            assertEquals(true, values[0])
        }
    }

    @Test
    fun postLoadingFalseOnResults() = runTest{
       mockSuccessFulCase()
        viewModel.loading.captureValues {
            viewModel.getPlayListDetails(playListId)
            viewModel.playlistDetails.getValueForTest()
            assertEquals(false, values[1])
        }
    }

    private suspend fun mockFailingCase() {
        viewModel = DetailedPlayListViewModel(service)
        whenever(service.getDetailedPlayList(playListId)).then {
            flow {
                emit(Result.failure<Exception>(exception))
            }
        }
    }

    private suspend fun mockSuccessFulCase() {
        viewModel = DetailedPlayListViewModel(service)
        whenever(service.getDetailedPlayList(playListId)).then {
            flow {
                emit(Result.success(playListDetails))
            }
        }
    }

}