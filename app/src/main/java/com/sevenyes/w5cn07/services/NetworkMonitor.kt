package com.sevenyes.w5cn07.services

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NetworkMonitor(
    private val connectivityManager: ConnectivityManager,
    private val networkRequest: NetworkRequest
) : ConnectivityManager.NetworkCallback() {

    init {
        connectivityManager.registerNetworkCallback(networkRequest, this)
    }

    private var _networkStateLive: MutableLiveData<NetworkState> =
        MutableLiveData(NetworkState.NETWORK_DISCONNECTED)
    val networkStateLive: LiveData<NetworkState> get() = _networkStateLive

    override fun onLost(network: Network) {
        super.onLost(network)
        _networkStateLive.value = NetworkState.NETWORK_DISCONNECTED
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)
        _networkStateLive.postValue(NetworkState.NETWORK_CONNECTED)
    }

}

