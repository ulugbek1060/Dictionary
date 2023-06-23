package gh.code.dictionary.data.network.models

data class Meaning(
    val partOfSpeech: String?,
    val antonyms: List<String>? = listOf(),
    val synonyms: List<String>? = listOf(),
    val definitions: List<Definition>? = listOf(),
)