package gh.code.dictionary.data.models

data class ResponseWord(
    val word: String,
    val phonetic: String,
    val phonetics: List<Phonetic>,
    val meanings: List<Meaning>,
    val sourceUrls: List<String>,
)