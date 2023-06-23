package gh.code.dictionary.data.database.entity

class DefinitionR(
    val definition: String?,
    val synonyms: List<String>? = listOf(),
    val antonyms: List<String>? = listOf()
)