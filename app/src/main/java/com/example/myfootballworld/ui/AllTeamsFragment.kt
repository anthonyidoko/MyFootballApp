package com.example.myfootballworld.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myfootballworld.R
import com.example.myfootballworld.data.model.Team
import com.example.myfootballworld.databinding.FragmentAllTeamsBinding
import com.example.myfootballworld.ui.viewModel.MainViewModel
import com.example.myfootballworld.utils.Constants.Companion.ERROR_MESSAGE
import com.example.myfootballworld.utils.Constants.Companion.NO_NETWORK
import com.example.myfootballworld.utils.adapter.LeagueAdapter
import com.example.myfootballworld.utils.resource.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllTeamsFragment : Fragment() {
    private var binding: FragmentAllTeamsBinding? = null
    private val viewModel: MainViewModel by activityViewModels()
    private val adapter: LeagueAdapter by lazy { LeagueAdapter() }
    private val args: AllTeamsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAllTeamsBinding.bind(view)
        viewModel.fetchTeams(args.leagueId)

        observeTeamDataAndUpdateViews()
        navigate()

    }

    /**
     * Observes the teams livedata in viewModel in update views
     */
    private fun observeTeamDataAndUpdateViews() {
        viewModel.teams.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    setNetworkViews(true)
                }
                Status.SUCCESS -> {
                    setNetworkViews(false)
                    it.data?.let { teamsData ->
                        binding!!.leagueName.text = teamsData.competition.name
                        adapter.populateList(teamsData.teams)
                    }

                    binding!!.leagueRecyclerView.layoutManager = GridLayoutManager(context, 2)
                    binding!!.leagueRecyclerView.adapter = adapter

                    adapter.setOnItemClickListener(object : LeagueAdapter.SetOnItemClickListener {
                        override fun onItemClick(item: Team) {
                            val action =
                                AllTeamsFragmentDirections.actionAllTeamsFragmentToTeamDetailFragment(
                                    item.id
                                )
                            findNavController().navigate(action)
                        }

                    })
                }
                Status.ERROR -> {
                    setNetworkViews(false)
                    if (it.message == NO_NETWORK) {
                        Toast.makeText(activity, NO_NETWORK, Toast.LENGTH_SHORT).show()
                    } else {
                        binding!!.allTeamsNetworkText.visibility = View.VISIBLE
                        binding!!.allTeamsNetworkText.text = ERROR_MESSAGE
                    }
                }
            }
        })
    }

    /**
     * Sets visibility of views in layout file
     */
    private fun setNetworkViews(makeVisible: Boolean) {
        if (makeVisible) {
            binding!!.allTeamsNetworkText.visibility = View.VISIBLE
            binding!!.allTeamsPgBar.visibility = View.VISIBLE
            binding!!.leagueRecyclerView.visibility = View.INVISIBLE
            binding!!.leagueName.visibility = View.INVISIBLE
        } else {
            binding!!.allTeamsNetworkText.visibility = View.INVISIBLE
            binding!!.allTeamsPgBar.visibility = View.INVISIBLE
            binding!!.leagueRecyclerView.visibility = View.VISIBLE
            binding!!.leagueName.visibility = View.VISIBLE
        }

    }

    private fun navigate(){
        binding!!.backArrow.setOnClickListener {
            findNavController().navigate(R.id.action_allTeamsFragment_to_allCompetitionFragment)
        }
    }


}