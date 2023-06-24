package gh.code.dictionary.data.network.models

import gh.code.dictionary.core.RecyclerItemView

data class Word(
    val word: String?,
    val phonetic: String?,
    val meanings: List<Meaning>? = listOf(),
    val sourceUrls: List<String>? = listOf(),
) : RecyclerItemView {
    override val itemWord: String? get() = word
    override val itemPhonetic: String? get() = phonetic

}