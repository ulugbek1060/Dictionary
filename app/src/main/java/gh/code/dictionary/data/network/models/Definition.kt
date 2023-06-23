package gh.code.dictionary.data.network.models

data class Definition(
    val definition: String?,
    val example: String?,
    val antonyms: List<String>?,
    val synonyms: List<String>?
)