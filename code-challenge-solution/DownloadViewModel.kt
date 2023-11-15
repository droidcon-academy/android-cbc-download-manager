package com.droidcon.downloadmanager

import android.app.DownloadManager
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.downloadmanager.util.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DownloadViewModel @Inject constructor(private val downloadManager: DownloadManager) : ViewModel() {
    private val _downloadProgress = MutableStateFlow(0F)
    val downloadProgress: MutableStateFlow<Float> get() = _downloadProgress

    private var downloadId: Long = -1

    fun requestDownload(downloadUrl: String) {
        val fileName = Util.getFileNameFromUri(downloadUrl)
        val request = DownloadManager.Request(Uri.parse(downloadUrl))
        request.setMimeType(Util.parseMimeType(downloadUrl))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
        request.setTitle(fileName)
        request.setDescription("Downloading a file")
        request.addRequestHeader("Authorization", "Bearer <token>")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)

        downloadId = downloadManager.enqueue(request)

        viewModelScope.launch {
            while (isActive) {
                val progress = getDownloadProgress()
                _downloadProgress.value = progress
                if (progress == 1F) {
                    cancel()
                }
                delay(1000)
            }
        }
    }

    private fun getDownloadProgress(): Float {
        val query = DownloadManager.Query().setFilterById(downloadId)
        val cursor = downloadManager.query(query)
        cursor.use {
            if (it.moveToFirst()) {
                val bytesDownloaded =
                    it.getLong(it.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR)) * 100L
                val bytesTotal =
                    it.getLong(it.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                return if (bytesTotal > 0) {
                    (bytesDownloaded / bytesTotal).div(100F)
                } else {
                    0F
                }
            }
        }
        return 0F
    }

    fun cancelDownload() {
        downloadManager.remove(downloadId)
    }
}