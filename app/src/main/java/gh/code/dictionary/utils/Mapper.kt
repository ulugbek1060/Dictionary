package gh.code.dictionary.utils

import gh.code.dictionary.data.database.entity.EntityWord
import gh.code.dictionary.data.network.models.Word

interface Mapper {

    fun fromModel(model: Word): EntityWord

    fun toModel(entityWord: EntityWord): Word

}