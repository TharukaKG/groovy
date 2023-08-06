package petros.efthymiou.groovy.services.PlayListClients

import petros.efthymiou.groovy.models.PlayListDetails
import petros.efthymiou.groovy.models.PlayListItem
import petros.efthymiou.groovy.services.responses.PlayListRaw
import retrofit2.http.GET
import retrofit2.http.Path

interface PLayListClient {

    @GET("playlists")
   suspend fun getAllPlayLists():List<PlayListRaw>

   @GET("playlistDetails/{playlistId}")
   suspend fun getPlayListDetails(@Path("playlistId") playlistId:String): PlayListDetails

}
