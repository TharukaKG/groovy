package petros.efthymiou.groovy.services

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import petros.efthymiou.groovy.models.PlayListDetails
import petros.efthymiou.groovy.services.PlayListClients.PLayListClient
import javax.inject.Inject

class DetailedPlaylistService @Inject constructor(private val client: PLayListClient) {

    suspend fun getDetailedPlayList(playlistId:String): Flow<Result<PlayListDetails>> {
        return flow {
            emit(Result.success(client.getPlayListDetails(playlistId)))
        }.catch {
            emit(Result.failure(RuntimeException("Something went wrong")))
        }
    }

}
