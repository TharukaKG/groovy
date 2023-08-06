package petros.efthymiou.groovy.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.helpers.PlaylistMapper
import petros.efthymiou.groovy.models.PlayListItem
import petros.efthymiou.groovy.services.PlayListService
import petros.efthymiou.groovy.services.responses.PlayListRaw
import javax.inject.Inject

class PlayListRepository @Inject constructor(private val service: PlayListService, private val mapper: PlaylistMapper) {

    suspend fun getPlayLists(): Flow<Result<List<PlayListItem>>> {
        return  service.getPlayLists().map { result ->
            when(result.isSuccess){
                true -> Result.success(mapper.invoke(result.getOrNull()?: emptyList()))
                else -> Result.failure(RuntimeException(result.exceptionOrNull()))
            }

        }

    }

}
