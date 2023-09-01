package gh.code.dictionary.core.core_impl

import android.content.Context
import gh.code.dictionary.core.Resources

class AndroidResources(private val context: Context) : Resources {
    override fun getString(id: Int): String {
        return context.getString(id)
    }

    override fun getString(id: Int, vararg args: Any): String {
        return context.getString(id, args)
    }

}