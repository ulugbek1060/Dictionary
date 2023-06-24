package gh.code.dictionary.data.network.models

import gh.code.dictionary.core.ItemDefinitionView
import gh.code.dictionary.core.ItemMeaningView

data class Meaning(
    val partOfSpeech: String?,
    val antonyms: List<String>? = listOf(),
    val synonyms: List<String>? = listOf(),
    val definitions: List<Definition>? = listOf(),
) : ItemMeaningView {
    override val itemPartOfSpeech: String? get() = partOfSpeech
    override val itemAntonyms: List<String>? get() = antonyms
    override val itemSynonyms: List<String>? get() = synonyms
    override val itemDefinitions: List<ItemDefinitionView> get() = definitions ?: listOf()

}