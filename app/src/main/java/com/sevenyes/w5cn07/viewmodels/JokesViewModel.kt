package com.sevenyes.w5cn07.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevenyes.w5cn07.database.IDataBaseRepository
import com.sevenyes.w5cn07.netrrestapi.IJokesApiRepository
import com.sevenyes.w5cn07.netrrestapi.JokesAPI
import com.sevenyes.w5cn07.views.state.JokeUIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class JokesViewModel(
    private val jokesAPIRepository: IJokesApiRepository,
    private val dataBaseRepository: IDataBaseRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

) : ViewModel() {

    private var _singleJokeState: MutableLiveData<JokeUIState> =
        MutableLiveData(JokeUIState.LOADING)
    val singleJokeState: LiveData<JokeUIState> get() = _singleJokeState

    var explicit : Boolean = false
    var lastJoke =""
    var lastCustomJoke =""

    fun checkedExplicitContent() = if(explicit) "" else JokesAPI.EXPLICIT

    fun getSingleJoke() {
        //_singleJokeState.value = JokeUIState.LOADING
        viewModelScope.launch(ioDispatcher) {

            try {
                val response = jokesAPIRepository.getJokes(1, checkedExplicitContent())
                if(response.isSuccessful) {
                    response.body()?.let {
                        _singleJokeState.postValue(JokeUIState.SUCCESS(it.jokes))
                    } ?: throw Exception("Request not Success")
                } else {
                  throw Exception("Request not Success")
                }
            } catch (e: Exception) {
                _singleJokeState.postValue(JokeUIState.ERROR(e))
            }
        }
    }

    fun getCustomJoke(firstName: String, lastName: String) {
        _singleJokeState.value = JokeUIState.LOADING
        viewModelScope.launch(ioDispatcher) {
            try {
               val response = jokesAPIRepository.getCustom(firstName, lastName, checkedExplicitContent())
                if(response.isSuccessful){

                   response.body()?.let {
                       _singleJokeState.postValue(JokeUIState.SUCCESS(it.jokes))
                   } ?:throw Exception("Request not Success")

                } else {
                    throw Exception("Request not Success")
                }

            } catch (e: Exception){
               _singleJokeState.postValue(JokeUIState.ERROR(e))
            }
        }
    }



    fun getBigList() {
        _singleJokeState.value = JokeUIState.LOADING
        viewModelScope.launch(ioDispatcher) {
            try {
                val response = jokesAPIRepository.getJokes(JokesAPI.RANDOM_JOKE_LIST,
                    checkedExplicitContent())
                if(response.isSuccessful){

                    response.body()?.let {
                       dataBaseRepository.writeAll(it.jokes)

                    } ?:throw Exception("Request not Success")

                } else {
                    throw Exception("Request not Success")
                }

            } catch (e: Exception){
                _singleJokeState.postValue(JokeUIState.ERROR(e))
            }
            try {
                _singleJokeState.postValue(JokeUIState.SUCCESS(dataBaseRepository.readAll()))
            } catch (e: Exception) {
                throw Exception("No Internet or DataBase")
            }

        }
    }
}

