package gh.code.dictionary.core

import androidx.fragment.app.FragmentActivity

interface ActivityRequired {

    fun onCreated(activity: FragmentActivity)

    fun onStarted()

    fun onStopped()

    fun onDestroyed()

}