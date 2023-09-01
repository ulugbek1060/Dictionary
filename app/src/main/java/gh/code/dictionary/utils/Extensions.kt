package gh.code.dictionary.utils

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.CheckResult
import gh.code.dictionary.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart

@ExperimentalCoroutinesApi
@CheckResult
fun EditText.textChanges(): Flow<CharSequence?> {
    return callbackFlow {
        val listener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySend(s)
            }
        }
        addTextChangedListener(listener)
        awaitClose { removeTextChangedListener(listener) }
    }.onStart { emit(text) }
}

@SuppressLint("UseCompatLoadingForDrawables")
fun ImageView.playFromUrl(
    url: String?,
    onStart: MediaPlayer.() -> Unit
) {
    val audioAttributes = AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_MEDIA)
        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
        .build()

    if (url.isNullOrBlank()) return

    MediaPlayer().apply {
        setAudioAttributes(audioAttributes)
        setDataSource(url)

        setOnPreparedListener {
            isEnabled = false
            start()
            setImageDrawable(context.getDrawable(R.drawable.ic_pause_24))
        }

        setOnCompletionListener {
            setImageDrawable(context.getDrawable(R.drawable.ic_play_arrow_24))
            release()
            isEnabled = true
        }
    }.onStart()
}

//@ExperimentalCoroutinesApi
//fun <T> Flow<T>.simpleScan(count: Int): Flow<List<T?>> {
//    val items = List<T?>(count) { null }
//    return this.scan(items) { previous, value ->
//        previous.drop(1) + value
//    }
//}