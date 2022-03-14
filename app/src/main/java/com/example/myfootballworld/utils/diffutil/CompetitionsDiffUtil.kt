package com.example.myfootballworld.utils.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.myfootballworld.data.model.Competition


class CompetitionsDiffUtil(
    private val oldList: List<Competition>,
    private val newList: List<Competition>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return when{
            oldList[oldItemPosition].id != newList[newItemPosition].id -> false
            oldList[oldItemPosition].name != newList[newItemPosition].name -> false
            oldList[oldItemPosition].area != newList[newItemPosition].area -> false
            oldList[oldItemPosition].code != newList[newItemPosition].code -> false
            oldList[oldItemPosition].currentSeason != newList[newItemPosition].currentSeason -> false
            oldList[oldItemPosition].emblemUrl != newList[newItemPosition].emblemUrl -> false
            oldList[oldItemPosition].lastUpdated != newList[newItemPosition].lastUpdated -> false
            oldList[oldItemPosition].numberOfAvailableSeasons != newList[newItemPosition].numberOfAvailableSeasons -> false
            oldList[oldItemPosition].plan != newList[newItemPosition].plan -> false
            else -> true
        }
    }
}