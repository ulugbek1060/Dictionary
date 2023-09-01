package gh.code.dictionary.core.core_impl

import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log
import gh.code.dictionary.core.Logger


class AndroidLogger(
    private val context: Context
) : Logger {

    private val TAG = "application_log"

    override fun log(obj: Any?) {
        if (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0) {
            Log.d(TAG, "log: $obj")
        }
    }

    override fun err(obj: Any?) {
        if (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0) {
            Log.d(TAG, "error: $obj")
        }
    }
}