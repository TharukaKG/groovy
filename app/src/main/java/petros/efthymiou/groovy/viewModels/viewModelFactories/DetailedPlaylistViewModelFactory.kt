package petros.efthymiou.groovy.viewModels.viewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petros.efthymiou.groovy.services.DetailedPlaylistService
import petros.efthymiou.groovy.viewModels.DetailedPlayListViewModel
import javax.inject.Inject

class DetailedPlaylistViewModelFactory @Inject constructor(private val service: DetailedPlaylistService): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailedPlayListViewModel(service) as T
    }
}