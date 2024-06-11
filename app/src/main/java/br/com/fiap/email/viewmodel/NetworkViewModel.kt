package br.com.fiap.email.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.fiap.email.network.ConnectivityLiveData

class NetworkViewModel(application: Application) : AndroidViewModel(application) {
    val connectivityLiveData = ConnectivityLiveData(application)
}