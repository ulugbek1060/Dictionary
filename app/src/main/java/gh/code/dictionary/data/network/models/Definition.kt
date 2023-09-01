package gh.code.dictionary.data.network.models

import gh.code.dictionary.screens.adapters.ItemDefinitionView

data class Definition(
    val definition: String? = null,
    val example: String? = null,
    val antonyms: List<String>?,
    val synonyms: List<String>?
) : ItemDefinitionView {
    override val itemDefinition: String? get() = definition
    override val itemExample: String? get() = example
}