package com.example.myfootballworld.utils.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.myfootballworld.R
import com.example.myfootballworld.data.model.Team
import com.example.myfootballworld.utils.diffutil.TeamsDiffUtil


class LeagueAdapter: RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder>() {
    private var oldTeamsList = emptyList<Team>()
    private lateinit var clickListener: SetOnItemClickListener

    inner class LeagueViewHolder(view: View, listener: SetOnItemClickListener): RecyclerView.ViewHolder(view){
        val teamLogo: ImageView = view.findViewById(R.id.iv_team_logo)
        val teamName: TextView = view.findViewById(R.id.tv_team_name)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(oldTeamsList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.league_item, parent,false)
        return LeagueViewHolder(view,clickListener)
    }

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        val currentTeam = oldTeamsList[position]
        holder.apply {
            teamName.text = currentTeam.name
            Glide.with(itemView)
                .load(currentTeam.crestUrl)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .placeholder(R.drawable.ic_baseline_change_history_24)
                .into(teamLogo)
        }
    }

    override fun getItemCount() = oldTeamsList.size

    fun populateList(newTeamsList: List<Team>){
        val diffUtil = TeamsDiffUtil(oldTeamsList, newTeamsList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldTeamsList = newTeamsList
        diffResult.dispatchUpdatesTo(this)
    }

    interface SetOnItemClickListener{
        fun onItemClick(item: Team)
    }

    fun setOnItemClickListener(listener: SetOnItemClickListener){
        clickListener = listener
    }
}