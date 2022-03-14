package com.example.myfootballworld.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.myfootballworld.R
import com.example.myfootballworld.data.model.Squad
import com.example.myfootballworld.databinding.FragmentTeamDetailBinding
import com.example.myfootballworld.ui.viewModel.MainViewModel
import com.example.myfootballworld.utils.Constants.Companion.ERROR_MESSAGE
import com.example.myfootballworld.utils.Constants.Companion.NO_NETWORK
import com.example.myfootballworld.utils.adapter.SquadAdapter
import com.example.myfootballworld.utils.resource.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamDetailFragment : Fragment() {
    private var binding: FragmentTeamDetailBinding? = null
    private val viewModel: MainViewModel by viewModels()
    private val squadAdapter: SquadAdapter by lazy { SquadAdapter() }
    private val args: TeamDetailFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTeamDetailBinding.bind(view)
        viewModel.fetchTeam(args.teamId)
        observeTeamDetailAndSetViews()
    }

    /**
     * Observes the team detail livedata in viewModel and sets up the views in layout file
     */
    private fun observeTeamDetailAndSetViews() {
        setNetworkViews(true)
        viewModel.teamDetail.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> {
                    setNetworkViews(true)
                }
                Status.SUCCESS -> {
                    setNetworkViews(false)
                    it.data?.let { data ->
                        binding!!.teamDetailPageTitle.text = data.name
                        binding!!.teamEmail.text = data.email
                        binding!!.teamFoundedDate.text = data.founded.toString()
                        binding!!.tvWebsite.text = data.website
                        binding!!.teamAreaName.text = data.area.name
                        Glide.with(this)
                            .load(data.crestUrl)
                            .placeholder(R.drawable.ic_baseline_change_history_24)
                            .into(binding!!.teamDetailLogo)
                        setUpAdapter(data.squad)
                    }
                }
                Status.ERROR -> {
                    setNetworkViews(false)
                    if (it.message == NO_NETWORK) {
                        Toast.makeText(activity, NO_NETWORK, Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, ERROR_MESSAGE, Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

    }

    /**
     * Sets the progress bar and network helper text when request is made
     */
    private fun setNetworkViews(makeVisible: Boolean) {
        if (makeVisible) {
            binding!!.teamDetailPgBar.visibility = View.VISIBLE
            binding!!.teamDetailNetworkText.visibility = View.VISIBLE
        } else {
            binding!!.teamDetailPgBar.visibility = View.INVISIBLE
            binding!!.teamDetailNetworkText.visibility = View.INVISIBLE
        }
    }

    /**
     * Set up the squad adapter and connect the views to the recyclerView
     */
    private fun setUpAdapter(squadData: List<Squad>) {
        squadAdapter.setData(squadData)
        binding!!.squadRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        binding!!.squadRecyclerView.adapter = squadAdapter
    }


}