package com.droidcon.downloadmanager.util

import android.webkit.MimeTypeMap
import java.io.File

class Util {
    companion object {
        fun parseMimeType(url: String): String {
            val file = File(url)
            val map = MimeTypeMap.getSingleton()
            val ext = MimeTypeMap.getFileExtensionFromUrl(file.name)
            var type = map.getMimeTypeFromExtension(ext)
            type = type ?: "*/*"
            return type;
        }

        fun getFileNameFromUri(url: String): String {
            return url.substring(url.lastIndexOf('/') + 1, url.length);
        }
    }
}