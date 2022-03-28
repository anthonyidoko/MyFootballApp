package com.example.myfootballworld.utils.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myfootballworld.R
import com.example.myfootballworld.data.model.Competition
import com.example.myfootballworld.utils.diffutil.CompetitionsDiffUtil

class CompetitionAdapter : RecyclerView.Adapter<CompetitionAdapter.TeamsViewHolder>() {

    private var competitions = emptyList<Competition>()
    private lateinit var clickListener: OnItemClickListener

    /**
     * The api does not grant access to certain end points.
     * This list represents the ID of competitions that do not
     * require upgrading subscription before accessing
     */
    private val competitionIdList =
        listOf(2000, 2001, 2002, 2003, 2013, 2014, 2015, 2016, 2017, 2018, 2019, 2021, 2152)

    inner class TeamsViewHolder(view: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(view) {
        val teamName: TextView = view.findViewById(R.id.tvTeamName)
        val startDate: TextView = view.findViewById(R.id.tv_start_date)
        val availabilityInfo: ImageView = view.findViewById(R.id.iv_unavailable_sub)
        val competitionId: TextView = view.findViewById(R.id.tv_comp_id)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(competitions[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.competition_item, parent, false)
        return TeamsViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        val currentItem = competitions[position]
        holder.apply {
            teamName.text = currentItem.name
            startDate.text = currentItem.currentSeason?.startDate ?: ""
            competitionId.text = currentItem.area.name
            if (competitionIdList.contains(currentItem.id)) {
                availabilityInfo.visibility = View.VISIBLE
            } else {
                availabilityInfo.visibility = View.GONE
            }
        }
    }

    override fun getItemCount() = competitions.size

    fun populateList(list: List<Competition>) {
        val diffUtil = CompetitionsDiffUtil(competitions, list)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        competitions = list
        diffResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onItemClick(item: Competition)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        clickListener = listener
    }
}