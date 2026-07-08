package com.ayham.postask

import android.app.Application
import com.ayham.postask.di.initKoin
import org.koin.android.ext.koin.androidContext

class PosApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@PosApplication)
        }
    }
}
