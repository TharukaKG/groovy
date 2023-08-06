package petros.efthymiou.groovy.unitTests.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.models.PlayListItem
import petros.efthymiou.groovy.repositories.PlayListRepository
import petros.efthymiou.groovy.unitTests.BaseUnitTest
import petros.efthymiou.groovy.utils.captureValues
import petros.efthymiou.groovy.utils.getValueForTest
import petros.efthymiou.groovy.viewModels.PlayListViewModel

class PlayListViewModelShould: BaseUnitTest() {

    private val playLists = mock<List<PlayListItem>>()
    private val expected = Result.success(playLists)
    private val repository: PlayListRepository = mock()
    private val exception:RuntimeException = RuntimeException("Something went wrong")

    @Test
    fun getPlayListFromRepository() = runTest {
        val viewModel = mockSuccessfulCase()
        viewModel.playLists.getValueForTest()
        verify(repository, times(1)).getPlayLists()
    }

    @Test
    fun emitsPlayListFromRepository() = runTest {
        val viewModel = mockSuccessfulCase()

        assertEquals(expected, viewModel.playLists.getValueForTest())
    }

    private suspend fun mockSuccessfulCase(): PlayListViewModel {
        whenever(repository.getPlayLists()).then {
            flow {
                emit(expected)
            }
        }

        return PlayListViewModel(repository)
    }

    @Test
    fun emitsErrorWhenReceiveError() = runTest{

        val viewModel = mockFailureCase()

        assertEquals(exception, viewModel.playLists.getValueForTest()!!.exceptionOrNull())

    }

    private suspend fun mockFailureCase(): PlayListViewModel {
        whenever(repository.getPlayLists()).then {
            flow {
                emit(Result.failure<List<PlayListItem>>(exception))
            }
        }

        return PlayListViewModel(repository)
    }

    @Test
    fun emitLoadingTrue(){
        val viewModel = PlayListViewModel(repository)
        viewModel.loading.captureValues {
            assertEquals(true, values[0])
        }
    }

    @Test
    fun emitLoadingFalse() = runTest{
        val viewModel = mockSuccessfulCase()
        viewModel.loading.captureValues {
            viewModel.playLists.getValueForTest()
            assertEquals(false, values[1])
        }
    }

    @Test
    fun emitFalseOnError() = runTest {
        val viewModel = mockFailureCase()
        viewModel.loading.captureValues {
            viewModel.playLists.getValueForTest()
            assertEquals(false, values[1])
        }
    }

}