package code.namanbir.gitfit.app.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.facebook.stetho.Stetho
import code.namanbir.gitfit.app.BuildConfig
import code.namanbir.gitfit.app.R
import code.namanbir.gitfit.app.utils.SharedPref
import code.namanbir.gitfit.app.utils.timber.DebugTree
import code.namanbir.gitfit.app.utils.timber.ReleaseTree
import timber.log.Timber

class MyApplication : Application() {

    private lateinit var sharedPreferences : SharedPreferences

    override fun onCreate() {
        super.onCreate()

        sharedPreferences = getSharedPreferences(SharedPref.SHARED_PREF.name, Context.MODE_PRIVATE)

        setTheme()

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            Timber.plant(DebugTree())
        } else {
            Timber.plant(ReleaseTree())
        }
    }

    private fun setTheme() {

        if (sharedPreferences.getBoolean(SharedPref.IS_DARK_MODE.name, true))
            applicationContext.setTheme(R.style.Dark)
        else
            applicationContext.setTheme(R.style.Light)
    }

}