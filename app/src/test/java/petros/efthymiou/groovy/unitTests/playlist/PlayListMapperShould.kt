package petros.efthymiou.groovy.unitTests.playlist

import org.junit.Assert.assertEquals
import org.junit.Test
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.helpers.PlaylistMapper
import petros.efthymiou.groovy.services.responses.PlayListRaw
import petros.efthymiou.groovy.unitTests.BaseUnitTest

class PlayListMapperShould: BaseUnitTest() {

    private val playListRaw = PlayListRaw("1", "Chilled House", "house")
    private val playListRawRock = PlayListRaw("2", "Hard Rock Cafe", "rock")

    private val mapper:PlaylistMapper = PlaylistMapper()

    private val playlists = mapper(listOf(playListRaw, playListRawRock))

    private val playlist = playlists[0]
    private val playlistRock = playlists[1]

    @Test
    fun keepSameId(){
        assertEquals(playListRaw.id, playlist.id)
    }

    @Test
    fun keepSameName(){
        assertEquals(playListRaw.name, playlist.name)
    }

    @Test
    fun keepSameCategory(){
        assertEquals(playListRaw.category, playlist.category)
    }

    @Test
    fun mapImageForRock(){
        assertEquals(R.mipmap.rock, playlistRock.image)
    }

    @Test
    fun mapDefaultImageForNonRock(){
        assertEquals(R.mipmap.playlist, playlist.image)
    }

}