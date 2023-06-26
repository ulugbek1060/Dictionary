package gh.code.dictionary.core

import androidx.annotation.StringRes

interface Resource {
    fun getString(@StringRes id: Int): String

    fun getString(@StringRes id: Int, vararg args: Any): String
}