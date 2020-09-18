package com.ffzs.draw

import ai.djl.android.core.BuildConfig
import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * @author: ffzs
 * @Date: 20-9-18 上午9:07
 */

@HiltAndroidApp
class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        System.setProperty("DJL_CACHE_DIR", filesDir.absolutePath)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}