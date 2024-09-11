package br.com.fiap.email.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class ConnectivityLiveData(context: Context) : LiveData<Boolean>() {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            postValue(true)
        }

        override fun onLost(network: Network) {
            postValue(false)
        }
    }

    override fun onActive() {
        super.onActive()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}

class ConnectivityViewModel(context: Context) : ViewModel(){
    private val _isConnected = ConnectivityLiveData(context)
    val isConnected: LiveData<Boolean> get() = _isConnected
}

@Composable
fun ConnectivityObserver(){
    val context = LocalContext.current
    val viewModel: ConnectivityViewModel = viewModel(factory = ConnectivityViewModelFactory(context))
    val isConnected by viewModel.isConnected.observeAsState(initial = true)

    var showDialog by remember { mutableStateOf(false) }

    if (!isConnected) {
        showDialog = true
    }

    if (showDialog) {
        ShowNoConnectionDialog(onDismiss = { showDialog = false })
    }
}

@Composable
fun ShowNoConnectionDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Sem conexão com a internet") },
        text = { Text(text = "Por favor, verifique sua conexão com a internet.") },
        confirmButton = {
            TextButton(onClick = {onDismiss()}) {
                Text("Tentar novamente")
            }
        },
        dismissButton = {
            TextButton(onClick = {onDismiss()}) {
                Text("Fechar")
            }
        }
    )
}
class ConnectivityViewModelFactory(private val context: Context) : androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConnectivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConnectivityViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}