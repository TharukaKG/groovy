package petros.efthymiou.groovy.views.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import petros.efthymiou.groovy.R
import petros.efthymiou.groovy.databinding.PlaylistItemBinding
import petros.efthymiou.groovy.models.PlayListItem


class MyPlayListRecyclerViewAdapter(
    private val values: List<PlayListItem>,
    private val listener: (playlistId:String) -> Unit
) : RecyclerView.Adapter<MyPlayListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(PlaylistItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {holder.onBind(values[position], listener)}

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(private val binding: PlaylistItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(item:PlayListItem, listener:(playlistId:String) -> Unit) = binding.apply {
            playlistName.text = item.name
            playlistCategory.text = item.category
            item.image?.let { imagePlaylist.setImageResource(it) }

            binding.parent.setOnClickListener {
                listener(item.id)
            }
        }
    }

}