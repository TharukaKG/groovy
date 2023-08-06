package petros.efthymiou.groovy.viewModels

import androidx.lifecycle.*
import kotlinx.coroutines.flow.onEach
import petros.efthymiou.groovy.services.DetailedPlaylistService
import petros.efthymiou.groovy.services.PlayListService

class DetailedPlayListViewModel(private val service: DetailedPlaylistService):ViewModel() {

    val loading: MutableLiveData<Boolean> = MutableLiveData(true)

    val playListId = MutableLiveData<String>()
    val playlistDetails = playListId.switchMap { id->
        liveData {
            emitSource(service.getDetailedPlayList(id)
                .onEach { loading.postValue(false) }
                .asLiveData())
        }
    }

    fun getPlayListDetails(id: String){
        playListId.postValue(id)
    }

}
