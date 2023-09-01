package gh.code.dictionary.data.network.models

import gh.code.dictionary.screens.adapters.ItemWordView

data class Word(
    val word: String?,
    val phonetic: String?,
    val meanings: List<Meaning>? = listOf(),
    val phonetics: List<Phonetic>? = listOf(),
    val sourceUrls: List<String>? = listOf(),
) : ItemWordView {
    override val itemWord: String? get() = word
    override val itemPhonetic: String? get() = phonetic
}