package com.example.myfootballworld.utils.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.example.myfootballworld.data.model.Squad


class SquadDiffUtil(
    private val oldList: List<Squad>,
    private val newList: List<Squad>
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
            oldList[oldItemPosition].countryOfBirth != newList[newItemPosition].countryOfBirth -> false
            oldList[oldItemPosition].countryOfBirth != newList[newItemPosition].dateOfBirth -> false
            oldList[oldItemPosition].nationality != newList[newItemPosition].nationality -> false
            oldList[oldItemPosition].position != newList[newItemPosition].position -> false
            oldList[oldItemPosition].role != newList[newItemPosition].role -> false
            else -> true
        }
    }
}