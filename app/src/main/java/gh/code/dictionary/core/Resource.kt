package gh.code.dictionary.core

import androidx.annotation.StringRes

interface Resource {
    fun getString(@StringRes id: Int): String
}