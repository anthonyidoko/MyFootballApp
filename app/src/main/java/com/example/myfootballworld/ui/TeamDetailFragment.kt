package com.example.myfootballworld.ui

import android.os.Bundle
import android.util.Log
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
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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
    private val viewModel: MainViewModel by activityViewModels()
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
//        navigateBack()
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
                        binding!!.teamPageTitle.text = data.name
                        binding!!.tvEmailAddress.text = data.email

                        binding!!.teamFoundedDate.text = data.founded.toString()

                        binding!!.tvWebsite.text = data.website
                        binding!!.tvNickName.text = data.shortName
                        binding!!.tvAddress.text = data.address

                        binding!!.tvPhoneNumber.text = data.phone
                        Glide.with(this)
                            .load(data.crestUrl)
                            .apply(RequestOptions().override(600, 200))
                            .centerCrop()
//                            .placeholder(R.drawable.ic_baseline_change_history_24)
                            .into(binding!!.teamDetailLogo)
                        setUpAdapter(data.squad)
                    }
                    Log.d("ZOET", "observeTeamDetailAndSetViews: ${it.data?.crestUrl}")
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
            binding!!.cvClubDetail.visibility = View.INVISIBLE
            binding!!.clubDetailCard.visibility = View.INVISIBLE
            binding!!.teamDetailLogo.visibility = View.INVISIBLE
            binding!!.tvClubDetail.visibility = View.INVISIBLE
            binding!!.tvTeamPlayers.visibility = View.INVISIBLE
        } else {
            binding!!.teamDetailPgBar.visibility = View.INVISIBLE
            binding!!.teamDetailNetworkText.visibility = View.INVISIBLE
            binding!!.cvClubDetail.visibility = View.VISIBLE
            binding!!.clubDetailCard.visibility = View.VISIBLE
            binding!!.teamDetailLogo.visibility = View.VISIBLE
            binding!!.tvClubDetail.visibility = View.VISIBLE
            binding!!.tvTeamPlayers.visibility = View.VISIBLE

        }
    }

    /**
     * Set up the squad adapter and connect the views to the recyclerView
     */
    private fun setUpAdapter(squadData: List<Squad>) {
        squadAdapter.setData(squadData)
        binding!!.squadRecyclerView.adapter = squadAdapter
    }

    private fun navigateBack(){
        binding!!.ivBackArrow.setOnClickListener {
            findNavController().navigate(R.id.action_teamDetailFragment_to_allTeamsFragment)
        }
    }


}