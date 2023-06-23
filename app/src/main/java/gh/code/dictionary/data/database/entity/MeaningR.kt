package gh.code.dictionary.data.database.entity

data class MeaningR(
    val partOfSpeech: String?,
    val definitions: List<DefinitionR>? = listOf(),
    val synonyms: List<String>? = listOf(),
    val antonyms: List<String>? = listOf()
)