package com.pantausikecil.network

object ApiConfig {
    /**
     * Android emulator cannot access laptop localhost through http://localhost.
     * Use 10.0.2.2 to reach backend running on the host machine.
     */
    const val DEFAULT_BASE_URL = "http://10.0.2.2:3000/"

    /** Use this if you test on a real Android phone connected to the same Wi-Fi. */
    const val REAL_DEVICE_EXAMPLE = "http://192.168.1.10:3000/"
}
