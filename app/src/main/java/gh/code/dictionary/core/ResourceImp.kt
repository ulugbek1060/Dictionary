package gh.code.dictionary.core

import android.content.Context

class ResourceImp(private val context: Context) : Resource {

    override fun getString(id: Int): String {
        return context.getString(id)
    }
}