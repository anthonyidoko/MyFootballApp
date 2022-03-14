package com.example.myfootballworld.utils.adapter

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myfootballworld.R
import com.example.myfootballworld.data.model.Squad
import com.example.myfootballworld.utils.diffutil.SquadDiffUtil
import java.time.LocalDateTime
import java.util.*

class SquadAdapter : RecyclerView.Adapter<SquadAdapter.SquadViewHolder>() {

    private var oldSquadList = emptyList<Squad>()

    private val monthMap = mapOf(
        "01" to 1,
        "02" to 2,
        "03" to 3,
        "04" to 4,
        "05" to 5,
        "06" to 6,
        "07" to 7,
        "08" to 8,
        "09" to 9,
        "10" to 10,
        "11" to 11,
        "12" to 12
    )


    inner class SquadViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val playerName: TextView = view.findViewById(R.id.player_name)
        val playerAge: TextView = view.findViewById(R.id.player_age)
        val playerPosition: TextView = view.findViewById(R.id.player_position)
        val playerRole: TextView = view.findViewById(R.id.player_role)
        val playerBirthCountry: TextView = view.findViewById(R.id.player_birth_country)
        val playerNationality: TextView = view.findViewById(R.id.player_nationality)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquadViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.squad_item, parent, false)
        return SquadViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: SquadViewHolder, position: Int) {

        val currentSquad = oldSquadList[position]
        val role = currentSquad.role?.lowercase()

        holder.apply {

            playerAge.text = currentSquad.dateOfBirth?.let { calculateAge(it) }
            playerName.text = currentSquad.name
            playerPosition.text = currentSquad.position
            playerRole.text = role?.replaceFirst(
                    "${role[0]}",
                    "${currentSquad.role?.get(0)}")
            playerBirthCountry.text = currentSquad.countryOfBirth
            playerNationality.text = currentSquad.nationality
        }

    }

    override fun getItemCount() = oldSquadList.size

    fun setData(newSquadList: List<Squad>) {
        val diffUtil = SquadDiffUtil(oldSquadList, newSquadList)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldSquadList = newSquadList
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Uses date of birth to calculate age
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateAge(dateOfBirth: String): String {
        var age = ""
        val currentDate = LocalDateTime.now().toString()
        val currentYear = currentDate.substring(0, currentDate.indexOf('-')).toInt()
        val currentMonth =
            currentDate.substring(currentDate.indexOf('-') + 1, currentDate.lastIndexOf('-'))

        val currentDay = currentDate.substring(
            currentDate.lastIndexOf('-') + 1,
            currentDate.lastIndexOf('-') + 3
        )

        val yearOfBirth = dateOfBirth.substring(0, dateOfBirth.indexOf('-')).toInt()
        val monthOfBirth =
            dateOfBirth.substring(dateOfBirth.indexOf('-') + 1, dateOfBirth.lastIndexOf('-'))

        val dayOfBirth = dateOfBirth.substring(
            dateOfBirth.lastIndexOf('-') + 1,
            dateOfBirth.lastIndexOf('-') + 3
        )

        try {
            age = if (monthMap[currentMonth]!! > monthMap[monthOfBirth]!!) {
                "${currentYear - (yearOfBirth + 1)}"
            } else if (monthMap[currentMonth]!! == monthMap[monthOfBirth]!! && dayOfBirth < currentDay) {
                "${currentYear - yearOfBirth}"
            } else {
                "${currentYear - (yearOfBirth + 1)}"
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return age
    }
}