package gh.code.dictionary.data.models

data class Meaning(
    val antonyms: List<String>?,
    val definitions: List<Definition>?,
    val partOfSpeech: String?,
    val synonyms: List<String>?
)