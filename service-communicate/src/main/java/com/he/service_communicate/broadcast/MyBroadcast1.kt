package com.he.service_communicate.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.he.service_communicate.listener.OnDownloadReceiverListener

class MyBroadcast1 : BroadcastReceiver() {

    private var onDownloadReceiverListener: OnDownloadReceiverListener? = null

    fun setDownloadReceiverListener(listener: OnDownloadReceiverListener) {
        onDownloadReceiverListener = listener
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (action == IntentAction) {
            val ext = intent.getIntExtra(KEY_EXTRA,0)
            onDownloadReceiverListener?.downloadReceiver(ext)
        }
    }

    companion object {
        private const val TAG = "P5DownloadReceiver"
        const val IntentAction = "com.he.service_communicate"
        const val KEY_EXTRA = "key_extra"
    }

}
