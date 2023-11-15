package com.droidcon.downloadmanager.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droidcon.downloadmanager.DownloadViewModel

@Composable
fun DownloadScreen(viewModel: DownloadViewModel) {
    val progress = viewModel.downloadProgress.collectAsState().value

    DownloadColumn(progress = progress, onDownloadClick = { downloadUrl ->
        viewModel.requestDownload(downloadUrl)
    }, onCancelClick = {
        viewModel.cancelDownload()
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadColumn(
    progress: Float,
    onDownloadClick: (downloadUrl: String) -> Unit,
    onCancelClick: () -> Unit
) {
    var downloadUrl by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = downloadUrl,
            onValueChange = { downloadUrl = it },
            label = { Text("Download URL") },
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = {
                if (downloadUrl.isNotEmpty()) {
                    onDownloadClick(downloadUrl)
                }
            }, modifier = Modifier
                .width(200.dp)
                .padding(16.dp)
        ) {
            Text("Download")
        }

        Button(
            onClick = {
                onCancelClick()
            }, modifier = Modifier
                .width(200.dp)
                .padding(16.dp)
        ) {
            Text("Cancel")
        }
        CircularProgressIndicator(
            progress = progress,
            modifier = Modifier
                .size(150.dp)
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DownloadColumnPreview() {
    DownloadColumn(progress = 0.3F, onDownloadClick = {}) {}
}