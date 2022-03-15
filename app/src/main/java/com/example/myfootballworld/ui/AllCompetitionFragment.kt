package com.example.myfootballworld.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myfootballworld.R
import com.example.myfootballworld.data.model.Competition
import com.example.myfootballworld.databinding.FragmentAllCompetitionBinding
import com.example.myfootballworld.ui.viewModel.MainViewModel
import com.example.myfootballworld.utils.Constants.Companion.ERROR_MESSAGE
import com.example.myfootballworld.utils.adapter.CompetitionAdapter
import com.example.myfootballworld.utils.resource.Resource
import com.example.myfootballworld.utils.resource.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AllCompetitionFragment : Fragment() {
    private var binding: FragmentAllCompetitionBinding? = null
    private val competitionAdapter by lazy { CompetitionAdapter() }
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_competition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllCompetitionBinding.bind(view)
        mainViewModel.fetchCompetitionsFromDb()

        populateRecyclerView()
        observeNetworkState()
    }

    /**
     * Observes competitions livedata in viewModel and update recyclerView accordingly
     */
    private fun populateRecyclerView() {
        setViews(true)
        mainViewModel.competitions.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Status.LOADING -> {
                    setViews(true)
                }
                Status.SUCCESS -> {
                    setViews(false)
                    if (it.data != null){
                        competitionAdapter.populateList(it.data)
                        val recyclerView = binding!!.allCompetitionsRecyclerView

                        recyclerView.layoutManager = GridLayoutManager(context, 2)
                        recyclerView.adapter = competitionAdapter
                        competitionAdapter.setOnItemClickListener(object :
                            CompetitionAdapter.OnItemClickListener {
                            override fun onItemClick(item: Competition) {
                                val action =
                                    AllCompetitionFragmentDirections.actionAllCompetitionFragmentToAllTeamsFragment(
                                        item.id
                                    )
                                findNavController().navigate(action)
                            }

                        })
                    }

                }
                Status.ERROR -> {
                    setViews(false)
                    binding!!.tvPlsWait.visibility = View.INVISIBLE
                    binding!!.tvPlsWait.text = ERROR_MESSAGE
                }
            }

        })
    }

    override fun onPause() {
        super.onPause()
        mainViewModel.networkState.value = Resource.error(null,null)
    }

    override fun onResume() {
        super.onResume()
//        mainViewModel.saveCompetitionsIntoDb()
        observeNetworkState()
    }


    /**
     * Watches for internet connection
     */
    private fun observeNetworkState() {
        mainViewModel.networkState.observe(viewLifecycleOwner, Observer {
            if (it.message != null) {
                setNetworkViews(it.message)
            }else{
                setNetworkViews(null)
            }
        })
    }

    /**
     * Set visibility of internet related views in layout file
     */
    private fun setNetworkViews(message: String?) {
        if (message != null){
            binding!!.tvConnectToInternet.visibility = View.VISIBLE
            binding!!.tvConnectToInternet.text = message
            binding!!.ivConnectToInternet.visibility = View.VISIBLE
        }else{
            binding!!.tvConnectToInternet.visibility = View.GONE
            binding!!.ivConnectToInternet.visibility = View.GONE
        }

    }

    /**
     * Set visibility of views in layout file
     */
    private fun setViews(makeVisible: Boolean) {
        if (makeVisible) {
            binding!!.allCompetitionsRecyclerView.visibility = View.INVISIBLE
            binding!!.allCompetitionPgBar.visibility = View.VISIBLE
            binding!!.tvPlsWait.visibility = View.VISIBLE
        } else {
            binding!!.allCompetitionsRecyclerView.visibility = View.VISIBLE
            binding!!.allCompetitionPgBar.visibility = View.INVISIBLE
            binding!!.tvPlsWait.visibility = View.INVISIBLE
        }
    }


}