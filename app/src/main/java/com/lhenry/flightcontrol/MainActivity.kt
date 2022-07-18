package com.lhenry.flightcontrol

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.lhenry.flightcontrol.databinding.MainActivityBinding
import com.lhenry.flightcontrol.ui.theme.FlightControlTheme
import kotlin.properties.Delegates


class MainActivity : ComponentActivity() {

    private lateinit var mainBinding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = MainActivityBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        //initialize bluetooth service
        initialiseBluetooth()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            onSuccessFullConnection()
        }
    }


    fun initialiseBluetooth() {
        val bluetoothManager: BluetoothManager =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getSystemService(BluetoothManager::class.java)
            } else {
                TODO("VERSION.SDK_INT < M")
            }
        val bluetoothAdapter: BluetoothAdapter? = bluetoothManager.adapter
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            mainBinding.txtMainTitle.setTextColor(resources.getColor(R.color.purple_200))
        }

        if (bluetoothAdapter?.isEnabled == false) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                startActivityForResult(enableBtIntent, 100)
                return
            } else {
                //already granted
                test_makeChanges("Green for GO .. ")
            }

        }

    }




    fun onSuccessFullConnection() {
        test_makeChanges("Bluetooth COM Green for GO ..")
    }


    @Composable
    fun Greeting(name: String) {
        Text(text = "Hello $name!")
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        FlightControlTheme {
            Greeting("Android")
        }
    }


    fun test_makeChanges(prompt: String) {
        mainBinding.txtMainTitle.setTextColor(resources.getColor(R.color.teal_700))
        mainBinding.txtMainTitle.text = prompt
    }
    
    fun executeProcedure(procedureVal:Int){
        //TODO Decode procedure to bluetooth bytes for sending
        //TODO execute read bytes from bluetooth drone device and display output, telemetry, procedures, e.t.c.


        mainBinding.txtMainTitle.setTextColor(resources.getColor(R.color.))
        mainBinding.txtMainTitle.text = prompt
    }
}


//TODO JETPACK COMPOSE UI
//        setContent {
//            FlightControlTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(color = MaterialTheme.colors.background) {
//                    Greeting("Controller")
//                }
//            }
//        }