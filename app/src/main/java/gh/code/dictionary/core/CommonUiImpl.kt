package gh.code.dictionary.core

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class CommonUiImpl(
    private val context: Context
) : CommonUi, ActivityRequired {

    private var currentActivity: FragmentActivity? = null
    private var isStarted: Boolean = false
    private val dialogRecords = mutableListOf<DialogRecord>()

    override fun onCreated(activity: FragmentActivity) {
        currentActivity = activity
    }

    override fun onStarted() {
        isStarted = true
        dialogRecords.forEach { startDialog(it) }
    }

    override fun onStopped() {
        isStarted = false
        dialogRecords.forEach { it.dialog?.dismiss() }
    }

    override fun onDestroyed() {
        if (this.currentActivity?.isFinishing == true) {
            this.dialogRecords.clear()
        }
        this.currentActivity = null
    }

    override fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override suspend fun alertDialog(config: AlertDialogConfig): Boolean =
        suspendCancellableCoroutine {
            val record = DialogRecord(config, it)
            dialogRecords.add(record)
            if (isStarted) {
                startDialog(record)
            }
            it.invokeOnCancellation {
                record.dialog?.dismiss()
                dialogRecords.remove(record)
            }
        }

    private fun startDialog(record: DialogRecord) {
        val activity = currentActivity ?: return
        val builder = AlertDialog.Builder(activity)
            .setTitle(record.config.title)
            .setMessage(record.config.message)
            .setCancelable(true)
            .setOnCancelListener {
                record.continuation.resume(false)
                dialogRecords.remove(record)
            }
            .setPositiveButton(record.config.positiveButton) { _, _ ->
                record.continuation.resume(true)
                dialogRecords.remove(record)
            }
        if (record.config.negativeButton != null) {
            builder.setNegativeButton(record.config.negativeButton) { _, _ ->
                record.continuation.resume(false)
                dialogRecords.remove(record)
            }
        }
        val dialog = builder.create()
        record.dialog = dialog
        dialog.show()
    }

    private class DialogRecord(
        var config: AlertDialogConfig,
        var continuation: CancellableContinuation<Boolean>,
        var dialog: Dialog? = null
    )
}