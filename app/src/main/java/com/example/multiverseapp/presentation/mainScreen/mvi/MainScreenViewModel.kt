package com.example.multiverseapp.presentation.mainScreen.mvi

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainScreenViewModel(private val mainScreenReducer: MainScreenReducer) : ViewModel() {

    private val _state = mutableStateOf(MainScreenState())

    val state: State<MainScreenState> = _state

    fun fetchLocation() {
        viewModelScope.launch {
            _state.value = mainScreenReducer.reduce(intent = MainScreenIntent.FetchUserLocation)
        }
    }

    fun displayPermissionRationale() {
        viewModelScope.launch {
            val newState =
                mainScreenReducer.reduce(intent = MainScreenIntent.DisplayPermissionRationale)
            _state.value =
                _state.value.copy(displayPermissionRationale = newState.displayPermissionRationale)
        }
    }

    fun hidePermissionRationale() {
        viewModelScope.launch {
            val newState =
                mainScreenReducer.reduce(intent = MainScreenIntent.HidePermissionRationale)
            _state.value =
                _state.value.copy(displayPermissionRationale = newState.displayPermissionRationale)
        }
    }

}