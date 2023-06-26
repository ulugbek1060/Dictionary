package gh.code.dictionary.core

import android.content.Context

class ResourceImp(private val context: Context) : Resource {
    override fun getString(id: Int): String {
        return context.getString(id)
    }

    override fun getString(id: Int, vararg args: Any): String {
        return context.getString(id, args)
    }

}