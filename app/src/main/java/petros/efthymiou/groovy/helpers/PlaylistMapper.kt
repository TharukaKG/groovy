package petros.efthymiou.groovy.helpers

import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.models.PlayListItem
import petros.efthymiou.groovy.services.responses.PlayListRaw
import javax.inject.Inject

class PlaylistMapper @Inject constructor() : Function1<List<PlayListRaw>, List<PlayListItem>> {

    override fun invoke(list: List<PlayListRaw>): List<PlayListItem> {
        return list.map { item ->
            PlayListItem(
                name = item.name,
                category = item.category,
                id = item.id,
                image = when (item.category) {
                    "rock" -> R.mipmap.rock
                    else -> R.mipmap.playlist
                }
            )
        }
    }

}
