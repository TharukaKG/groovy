package petros.efthymiou.groovy.services

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import petros.efthymiou.groovy.models.PlayListDetails
import petros.efthymiou.groovy.models.PlayListItem
import petros.efthymiou.groovy.services.PlayListClients.PLayListClient
import petros.efthymiou.groovy.services.responses.PlayListRaw
import javax.inject.Inject

class PlayListService @Inject constructor(private val client: PLayListClient) {

   suspend fun getPlayLists(): Flow<Result<List<PlayListRaw>>>{
//        client.getAllPlayLists()
        return flow {
            emit(Result.success(client.getAllPlayLists()))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

}
