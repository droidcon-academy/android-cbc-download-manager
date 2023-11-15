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

    }

    private fun getDownloadProgress(): Float {
        return 0F
    }

    fun cancelDownload() {

    }
}