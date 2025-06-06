/*
 * PixelSafe - Steganography tool for PNG images
 * Copyright (C) 2025 Stefan Oltmann
 * https://stefan-oltmann.de/pixelsafe
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.readBytes
import io.github.vinceglb.filekit.size
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import org.jetbrains.skia.Image
import saveBytes
import ui.icons.Logo
import util.SteganographyUtil
import kotlin.math.max

const val UI_UPDATE_DELAY = 100L

@Composable
fun ContentView(
    scrollState: ScrollState,
    blockUserInput: MutableState<Boolean>,
    showToast: (Toast) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        modifier = Modifier.Companion
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        val coroutineScope = rememberCoroutineScope()

        val selectedFileState: MutableState<PlatformFile?> = remember { mutableStateOf(null) }

        val originalBytesState: MutableState<ByteArray?> = remember { mutableStateOf(null) }

        val imageState: MutableState<Image?> = remember { mutableStateOf(null) }

        val hiddenBytesState: MutableState<Pair<String, ByteArray>?> = remember { mutableStateOf(null) }

        androidx.compose.foundation.Image(
            imageVector = Logo,
            contentDescription = null,
            modifier = Modifier.Companion.height(64.dp)
        )

        Spacer(
            modifier = Modifier.Companion.height(8.dp)
        )

        Text(
            text = "Steganography tool for PNG images",
            fontSize = 16.sp,
            color = Color.Companion.Gray
        )

        Spacer(
            modifier = Modifier.Companion.height(16.dp)
        )

        Button(
            modifier = Modifier.Companion.width(288.dp),
            onClick = {

                coroutineScope.launch {

                    loadImage(
                        selectedFileState,
                        originalBytesState,
                        imageState,
                        hiddenBytesState,
                        blockUserInput,
                        showToast
                    )
                }
            }
        ) {

            Text("Select storage container PNG")
        }

        Spacer(
            modifier = Modifier.Companion.height(8.dp)
        )

        Box(
            contentAlignment = Alignment.Companion.Center,
            modifier = Modifier.Companion
                .size(
                    width = 288.dp,
                    height = 192.dp
                )
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = MaterialTheme.shapes.medium
                )
        ) {

            imageState.value?.let { image ->

                androidx.compose.foundation.Image(
                    bitmap = image.toComposeImageBitmap(),
                    contentDescription = null
                )
            }
        }

        Spacer(
            modifier = Modifier.Companion.height(8.dp)
        )

        imageState.value?.let { image ->

            val storageSize = remember(image) {
                SteganographyUtil.calculateHiddenSpaceInBytes(image)
            }

            Column(
                horizontalAlignment = Alignment.Companion.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp),
                modifier = Modifier.Companion
                    .width(288.dp)
                    .border(1.dp, Color.Companion.Black, MaterialTheme.shapes.medium)
                    .padding(8.dp)
            ) {

                Text(
                    text = "${image.width} x ${image.height} = ${image.width * image.height} Pixels",
                    fontSize = 14.sp
                )

                Text(
                    text = "Can hide up to ${storageSize / 1024} kb of data.",
                    fontSize = 14.sp
                )

                val currentHiddenBytes = hiddenBytesState.value

                if (currentHiddenBytes != null) {

                    val kbAmount = max(1, currentHiddenBytes.second.size / 1024)

                    Text(
                        text = "Currently stores $kbAmount kb of data:",
                        fontSize = 14.sp
                    )

                    Text(
                        text = currentHiddenBytes.first,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp
                    )

                } else {

                    Text(
                        text = "No hidden data found in image.",
                        fontSize = 14.sp,
                        color = Color.Companion.Gray
                    )
                }
            }

            Spacer(
                modifier = Modifier.Companion.height(8.dp)
            )

            Button(
                modifier = Modifier.Companion.width(288.dp),
                onClick = {

                    coroutineScope.launch {

                        hideDataInImage(
                            storageSize,
                            image,
                            originalBytesState,
                            hiddenBytesState,
                            blockUserInput,
                            showToast
                        )
                    }
                }
            ) {

                Text("Hide data in image")
            }

            Button(
                modifier = Modifier.Companion.width(288.dp),
                enabled = hiddenBytesState.value != null,
                onClick = {

                    coroutineScope.launch {

                        saveHiddenData(
                            hiddenBytesState,
                            blockUserInput,
                            showToast
                        )
                    }
                }
            ) {

                Text("Save hidden data to disk")
            }

            val selectedFileName = selectedFileState.value?.name

            Button(
                modifier = Modifier.Companion.width(288.dp),
                enabled = originalBytesState.value != null && selectedFileName != null && hiddenBytesState.value != null,
                onClick = {

                    coroutineScope.launch(Dispatchers.Default) {

                        saveModifiedBytes(
                            originalBytesState,
                            selectedFileName,
                            blockUserInput,
                            showToast
                        )
                    }
                }
            ) {

                Text("Save modified image to disk")
            }
        }
    }
}

private suspend fun loadImage(
    selectedFileState: MutableState<PlatformFile?>,
    originalBytesState: MutableState<ByteArray?>,
    imageState: MutableState<Image?>,
    hiddenBytesState: MutableState<Pair<String, ByteArray>?>,
    blockUserInput: MutableState<Boolean>,
    showToast: (Toast) -> Unit
) = withContext(Dispatchers.Default) {

    val file = FileKit.openFilePicker(
        type = FileKitType.File(extensions = listOf("png", "PNG")),
        title = "Select PNG image to hide data in",
    )

    if (file != null) {

        try {

            blockUserInput.value = true

            /* Let the UI update */
            delay(UI_UPDATE_DELAY)

            val bytes = file.readBytes()

            withContext(Dispatchers.Main) {
                selectedFileState.value = file
                originalBytesState.value = bytes
            }

            yield()

            val image = Image.Companion.makeFromEncoded(bytes)
            val hiddenBytes = SteganographyUtil.readLeastSignificantBits(image)

            withContext(Dispatchers.Main) {
                imageState.value = image
                hiddenBytesState.value = hiddenBytes
            }

        } catch (ex: Exception) {

            showToast(Toast(ToastType.ERROR, "Can't load image."))

            ex.printStackTrace()

            null

        } finally {

            blockUserInput.value = false
        }
    }
}

private suspend fun hideDataInImage(
    storageSize: Long,
    image: Image,
    originalBytesState: MutableState<ByteArray?>,
    hiddenBytesState: MutableState<Pair<String, ByteArray>?>,
    blockUserInput: MutableState<Boolean>,
    showToast: (Toast) -> Unit
) = withContext(Dispatchers.Default) {

    val file = FileKit.openFilePicker(
        title = "Select secret data to hide in image"
    )

    if (file != null) {

        if (file.size() > storageSize) {

            showToast(Toast(ToastType.ERROR, "File is too large to be hidden in image."))

            return@withContext
        }

        /*
         * Replace the bytes
         */

        try {

            blockUserInput.value = true

            /* Let the UI update */
            delay(UI_UPDATE_DELAY)

            val bytesToHide = file.readBytes()

            yield()

            val modifiedImage = SteganographyUtil.writeLeastSignificantBits(
                image = image,
                fileName = file.name,
                byteArray = bytesToHide
            )

            yield()

            val newBytes = modifiedImage.encodeToData()!!.bytes

            withContext(Dispatchers.Main) {

                originalBytesState.value = newBytes
                hiddenBytesState.value = file.name to bytesToHide
            }

            showToast(Toast(ToastType.SUCCESS, "Data hidden in image."))

        } finally {

            blockUserInput.value = false
        }
    }
}

private suspend fun saveHiddenData(
    hiddenBytesState: MutableState<Pair<String, ByteArray>?>,
    blockUserInput: MutableState<Boolean>,
    showToast: (Toast) -> Unit
) = withContext(Dispatchers.Default) {

    val hiddenBytes = hiddenBytesState.value
        ?: return@withContext

    /* Write new bytes to file */

    try {

        blockUserInput.value = true

        /* Let the UI update */
        delay(UI_UPDATE_DELAY)

        val saved = saveBytes(
            fileName = hiddenBytes.first,
            bytes = hiddenBytes.second
        )

        if (saved)
            showToast(Toast(ToastType.SUCCESS, "Hidden data saved to disk."))

    } finally {

        blockUserInput.value = false
    }
}

private suspend fun saveModifiedBytes(
    originalBytesState: MutableState<ByteArray?>,
    selectedFileName: String?,
    blockUserInput: MutableState<Boolean>,
    showToast: (Toast) -> Unit
) = withContext(Dispatchers.Default) {

    if (originalBytesState.value == null || selectedFileName == null)
        return@withContext

    /* Write new bytes to file */

    try {

        blockUserInput.value = true

        /* Let the UI update */
        delay(UI_UPDATE_DELAY)

        val saved = saveBytes(
            fileName = selectedFileName,
            bytes = originalBytesState.value!!
        )

        if (saved)
            showToast(Toast(ToastType.SUCCESS, "Modified image saved to disk."))

    } finally {

        blockUserInput.value = false
    }
}
