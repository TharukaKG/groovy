package petros.efthymiou.groovy.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import petros.efthymiou.groovy.databinding.FragmentPlaylistBinding
import petros.efthymiou.groovy.models.PlayListItem
import petros.efthymiou.groovy.repositories.PlayListRepository
import petros.efthymiou.groovy.services.PlayListClients.PLayListClient
import petros.efthymiou.groovy.services.PlayListService
import petros.efthymiou.groovy.viewModels.viewModelFactories.PlayListViewModelFactory
import petros.efthymiou.groovy.viewModels.PlayListViewModel
import petros.efthymiou.groovy.views.adapters.MyPlayListRecyclerViewAdapter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@AndroidEntryPoint
class PlayListFragment : Fragment() {

    private var _binding:FragmentPlaylistBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var factory:PlayListViewModelFactory

    private lateinit var viewModel: PlayListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaylistBinding.inflate(layoutInflater)

        initViewModel()
        observeLoading()
        observePlayLists()

        return binding?.parent
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this, factory)[PlayListViewModel::class.java]
    }

    private fun observePlayLists(){
        viewModel.playLists.observe(this as LifecycleOwner) { playlistsResult ->
            if(playlistsResult.getOrNull()!=null){
                binding?.rvPlaylist?.initRecyclerView(playlistsResult.getOrNull()!!)
            }else{
                println("PlayListFragment::: else ${playlistsResult.getOrNull()}")
            }
        }
    }

    private fun observeLoading(){
        viewModel.loading.observe(this as LifecycleOwner){ loading->
            if(!loading) binding?.loaderPlaylist?.visibility = View.GONE else binding?.loaderPlaylist?.visibility = View.VISIBLE
        }
    }

    private fun RecyclerView.initRecyclerView(playLists:List<PlayListItem>){
        layoutManager = LinearLayoutManager(context)
        adapter = MyPlayListRecyclerViewAdapter(playLists){
            val action = PlayListFragmentDirections.actionPlayListFragmentToDetailedPlayListFragment(it)
            findNavController().navigate(action)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PlayListFragment()
    }
}