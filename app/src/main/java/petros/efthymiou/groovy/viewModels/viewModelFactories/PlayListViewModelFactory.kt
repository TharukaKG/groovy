package petros.efthymiou.groovy.viewModels.viewModelFactories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import petros.efthymiou.groovy.repositories.PlayListRepository
import petros.efthymiou.groovy.viewModels.PlayListViewModel
import javax.inject.Inject

class PlayListViewModelFactory @Inject constructor(private val playListRepository: PlayListRepository): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayListViewModel(playListRepository) as T
    }

}
