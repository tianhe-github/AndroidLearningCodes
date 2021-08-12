package com.he.service_communicate.listener
import androidx.annotation.Keep

@Keep
interface IService {
    fun start()
}

@Keep
interface OnDownloadReceiverListener {
    fun downloadReceiver(progress: Int)
}
