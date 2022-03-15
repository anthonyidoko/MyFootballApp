package com.example.myfootballworld.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myfootballworld.data.model.AllTeamsResponseData
import com.example.myfootballworld.data.model.Competition
import com.example.myfootballworld.data.model.TeamDetailResponseData
import com.example.myfootballworld.data.repository.CompetitionsRepo
import com.example.myfootballworld.utils.Constants.Companion.API_KEY
import com.example.myfootballworld.utils.Constants.Companion.ERROR_MESSAGE
import com.example.myfootballworld.utils.Constants.Companion.NO_NETWORK
import com.example.myfootballworld.utils.resource.NetworkChecker
import com.example.myfootballworld.utils.resource.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val competitionRepo: CompetitionsRepo,
    private val networkChecker: NetworkChecker
) : ViewModel() {

    private val _competitions = MutableLiveData<Resource<List<Competition>>>()
    val competitions: LiveData<Resource<List<Competition>>> = _competitions

    val networkState = MutableLiveData<Resource<String>>()

    private val _teams = MutableLiveData<Resource<AllTeamsResponseData>>()
    val teams: LiveData<Resource<AllTeamsResponseData>> = _teams

    private val _teamDetail = MutableLiveData<Resource<TeamDetailResponseData>>()
    val teamDetail: LiveData<Resource<TeamDetailResponseData>> = _teamDetail

    init {
        fetchCompetitionsFromApi()
    }

    fun fetchTeams(id: Int) {
        _teams.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            if (networkChecker.hasInternetConnection()) {
                try {
                    val response = competitionRepo.getAllTeams(API_KEY, id)
                    if (response.isSuccessful) {
                        _teams.postValue(Resource.success(response.body()))
                    } else if (response.code() == 403) {
                        _teams.postValue(Resource.error(ERROR_MESSAGE, null))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                networkState.postValue(Resource.error(NO_NETWORK, null))
                _teams.postValue(Resource.error(NO_NETWORK, null))
            }

        }
    }

    fun fetchTeam(id: Int) {
        _teamDetail.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            if (networkChecker.hasInternetConnection()) {
                try {
                    val response = competitionRepo.getTeams(API_KEY, id)
                    if (response.isSuccessful) {
                        _teamDetail.postValue(Resource.success(response.body()))
                    } else if (response.code() == 403) {
                        _teamDetail.postValue(Resource.error(ERROR_MESSAGE, null))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                networkState.postValue(Resource.error(NO_NETWORK, null))
                _teamDetail.postValue(Resource.error(NO_NETWORK, null))
            }

        }
    }

    private fun fetchCompetitionsFromApi() {
        _competitions.postValue(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            if (networkChecker.hasInternetConnection()) {
                try {
                    val response = competitionRepo.getAllCompetitions(API_KEY)
                    if (response.isSuccessful) {
                        val result =
                            competitionRepo.saveCompetitionsToDb(response.body()!!.competitions)
                        if (result.isNotEmpty()) fetchCompetitionsFromDb()
                    } else {
                        _competitions.postValue(
                            Resource.error(
                                response.errorBody().toString(),
                                null
                            )
                        )
                        networkState.postValue(Resource.error(NO_NETWORK, null))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            } else {
                _competitions.postValue(Resource.error(NO_NETWORK, null))
                fetchCompetitionsFromDb()
                networkState.postValue(Resource.error(NO_NETWORK, null))
            }
        }
    }

    private fun competitionListIsEmpty(): Boolean {
        return competitions.value?.data == null
    }

    fun fetchCompetitionsFromDb() {
        if(competitionListIsEmpty()) _competitions.postValue(Resource.loading(null))

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = competitionRepo.fetchCompetitionsFromDb()
                _competitions.postValue(Resource.success(response))
            } catch (e: Exception) {
                _competitions.postValue(Resource.error(e.message, null))
                e.printStackTrace()
            }
        }
    }
}