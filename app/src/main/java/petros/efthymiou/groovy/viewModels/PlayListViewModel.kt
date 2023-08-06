package petros.efthymiou.groovy.viewModels

import androidx.lifecycle.*
import kotlinx.coroutines.flow.onEach
import petros.efthymiou.groovy.models.PlayListItem
import petros.efthymiou.groovy.repositories.PlayListRepository

class PlayListViewModel(private val repository: PlayListRepository): ViewModel() {

    val loading = MutableLiveData<Boolean>(true)

    val playLists = liveData<Result<List<PlayListItem>>> {
        emitSource(repository.getPlayLists()
            .onEach { loading.postValue(false) }
            .asLiveData())
    }

}
