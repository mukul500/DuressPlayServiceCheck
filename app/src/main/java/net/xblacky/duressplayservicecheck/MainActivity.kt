package net.xblacky.duressplayservicecheck

import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import android.service.autofill.OnClickAction
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import net.xblacky.duressplayservicecheck.ui.theme.DuressPlayServiceCheckTheme
import net.xblacky.duressplayservicecheck.ui.theme.MainViewModel

class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DuressPlayServiceCheckTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainScreen(onClickAction = {
                        checkGooglePlayServices(viewModel)
                    }, mainViewModel = viewModel)
                }
            }
        }
    }


    fun checkGooglePlayServices(viewModel: MainViewModel) {
        val googleApiAvailability = GoogleApiAvailability.getInstance()
        val status = googleApiAvailability.isGooglePlayServicesAvailable(this)
        viewModel.updateStatus(status, googleApiAvailability.getErrorString(status))
        if (status != ConnectionResult.SUCCESS) {
            Toast.makeText(this, "This device is not supported", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Google Play Services is available.", Toast.LENGTH_LONG).show()
        }
    }
}

@Preview
@Composable

fun MainScreenPreview() {
    DuressPlayServiceCheckTheme {
        MainScreen(onClickAction = {})
    }
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = viewModel(),
    onClickAction: () -> Unit
) {


    Column(
        modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val statusCodeState = mainViewModel.statusCode.collectAsState()
        val errorMessageState = mainViewModel.errorMessage.collectAsState()
        Text(
            text = "Status ${statusCodeState.value}",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(16.dp))
        Text(
            text = "Message: ${errorMessageState.value}",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = onClickAction,
            Modifier
                .background(MaterialTheme.colors.primary)
        ) {
            Text(text = "Check \n Google Play Services", textAlign = TextAlign.Center)
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DuressPlayServiceCheckTheme {
        Greeting("Android")
    }
}