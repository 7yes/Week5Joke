package com.sevenyes.w5cn07.services

sealed class NetworkState {
    object  NETWORK_CONNECTED : NetworkState()
    object  NETWORK_DISCONNECTED : NetworkState()
}