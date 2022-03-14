package com.example.myfootballworld.utils.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.myfootballworld.data.model.Team

class TeamsDiffUtil(
    private val oldList: List<Team>,
    private val newList: List<Team>
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
            oldList[oldItemPosition].clubColors != newList[newItemPosition].clubColors -> false
            oldList[oldItemPosition].crestUrl != newList[newItemPosition].crestUrl -> false
            oldList[oldItemPosition].email != newList[newItemPosition].email -> false
            oldList[oldItemPosition].founded != newList[newItemPosition].founded -> false
            oldList[oldItemPosition].lastUpdated != newList[newItemPosition].lastUpdated -> false
            oldList[oldItemPosition].phone != newList[newItemPosition].phone -> false
            oldList[oldItemPosition].shortName != newList[newItemPosition].shortName -> false
            oldList[oldItemPosition].tla != newList[newItemPosition].tla -> false
            oldList[oldItemPosition].venue != newList[newItemPosition].venue -> false
            oldList[oldItemPosition].website != newList[newItemPosition].website -> false
            else -> true
        }
    }
}
