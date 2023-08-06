package petros.efthymiou.groovy.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import petros.efthymiou.groovy.databinding.FragmentDetailedPlaylistBinding
import petros.efthymiou.groovy.models.PlayListDetails
import petros.efthymiou.groovy.viewModels.DetailedPlayListViewModel
import petros.efthymiou.groovy.viewModels.viewModelFactories.DetailedPlaylistViewModelFactory
import javax.inject.Inject

@AndroidEntryPoint
class DetailedPlayListFragment: Fragment() {

    private var _binding:FragmentDetailedPlaylistBinding? = null
    private val binding get() = _binding

    @Inject
    lateinit var factory: DetailedPlaylistViewModelFactory

    private lateinit var viewModel:DetailedPlayListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailedPlaylistBinding.inflate(layoutInflater)

        initViewModel()
        getFromBundle()

        return binding?.parent
    }

    private fun getFromBundle(){
        arguments?.getString("playlistId")?.apply {
            viewModel.getPlayListDetails(this)
        }
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this, factory)[DetailedPlayListViewModel::class.java]
        observe()
    }

    private fun observe(){

        viewModel.loading.observe(viewLifecycleOwner){ loading->
            if(loading) binding?.loaderDetails?.visibility = View.VISIBLE else binding?.loaderDetails?.visibility = View.GONE
        }

        viewModel.playlistDetails.observe(viewLifecycleOwner){
            if(it.getOrNull()!=null){
                it.getOrNull()?.bindUI()
            }else if(it.getOrNull()==null&&it.isFailure){
                binding?.tvPlaylistName?.text = "No Playlist content available"
            }
        }

    }

    private fun PlayListDetails.bindUI() = binding?.apply {
        tvPlaylistName.text = name
        tvSongs.text = details
    }

}