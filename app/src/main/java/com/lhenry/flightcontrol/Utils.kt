package com.lhenry.flightcontrol

import android.view.InputDevice
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * Created by Henry Letting on 29/05/2022.
 */


class MyLifecycleObserver(private val registry : ActivityResultRegistry)
    : DefaultLifecycleObserver {
    lateinit var getContent : ActivityResultLauncher<String>

    override fun onCreate(owner: LifecycleOwner) {
        getContent = registry.register("key", owner, ActivityResultContracts.GetContent()) { uri ->
            // Handle the returned Uri
        }
    }

    fun selectImage() {
        getContent.launch("image/*")
    }
}


fun getGameControllerIds(): List<Int> {
    val gameControllerDeviceIds = mutableListOf<Int>()
    val deviceIds = InputDevice.getDeviceIds()
    deviceIds.forEach { deviceId ->
        InputDevice.getDevice(deviceId).apply {

            // Verify that the device has gamepad buttons, control sticks, or both.
            if (sources and InputDevice.SOURCE_GAMEPAD == InputDevice.SOURCE_GAMEPAD
                || sources and InputDevice.SOURCE_JOYSTICK == InputDevice.SOURCE_JOYSTICK) {
                // This device is a game controller. Store its device ID.
                gameControllerDeviceIds
                    .takeIf { !it.contains(deviceId) }
                    ?.add(deviceId)
            }
        }
    }
    return gameControllerDeviceIds


}