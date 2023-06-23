package gh.code.dictionary.data

import gh.code.dictionary.core.AppException
import gh.code.dictionary.data.database.entity.DefinitionR
import gh.code.dictionary.data.database.entity.EntityWord
import gh.code.dictionary.data.database.entity.MeaningR
import gh.code.dictionary.data.network.models.Definition
import gh.code.dictionary.data.network.models.Meaning
import gh.code.dictionary.data.network.models.Word

class MapperImpl : Mapper {
    override fun fromModel(model: Word): EntityWord {
        if (model.phonetic.isNullOrBlank()) throw AppException("Word phonetic is incorrect!")
        return EntityWord(
            word = model.word,
            phonetic = model.phonetic,
            meanings = model.meanings?.map { meaning ->
                MeaningR(
                    partOfSpeech = meaning.partOfSpeech,
                    antonyms = meaning.antonyms,
                    synonyms = meaning.synonyms,
                    definitions = meaning.definitions?.map { def ->
                        DefinitionR(
                            definition = def.definition,
                            example = def.example,
                            synonyms = def.synonyms,
                            antonyms = def.antonyms
                        )
                    }
                )
            },
            sourceUrl = model.sourceUrls
        )
    }

    override fun toModel(entityWord: EntityWord): Word =
        Word(
            word = entityWord.word,
            phonetic = entityWord.phonetic,
            meanings = entityWord.meanings?.map { meaning ->
                Meaning(
                    partOfSpeech = meaning.partOfSpeech,
                    antonyms = meaning.antonyms,
                    synonyms = meaning.synonyms,
                    definitions = meaning.definitions?.map { def ->
                        Definition(
                            definition = def.definition,
                            example = def.example,
                            antonyms = def.antonyms,
                            synonyms = def.synonyms
                        )
                    }
                )
            },
            sourceUrls = entityWord.sourceUrl
        )
}