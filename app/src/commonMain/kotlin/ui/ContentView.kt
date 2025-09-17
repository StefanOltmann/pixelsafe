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
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.vinceglb.filekit.*
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.openFilePicker
import kotlinx.coroutines.*
import org.jetbrains.skia.Image
import saveBytes
import ui.icons.Logo
import util.SteganographyUtil
import kotlin.math.max

const val UI_UPDATE_DELAY_MS = 100L

const val TEXT_INPUT_BOUNCE_MS = 300L

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

        val passwordState: MutableState<String> = remember { mutableStateOf("") }

        /*
         * Try to decode bytes if the password changes.
         */
        LaunchedEffect(passwordState.value) {

            try {

                /*
                 * Wait a moment if the user makes additional
                 * keystrokes in entering the password.
                 */
                delay(TEXT_INPUT_BOUNCE_MS)

                val image = imageState.value ?: return@LaunchedEffect

                /*
                 * Don't skip here if the password is null or empty, because
                 * the user may have entered something and erased it again.
                 */
                val password = passwordState.value

                val hiddenBytes = SteganographyUtil.readLeastSignificantBits(image, password)

                withContext(Dispatchers.Main) {
                    hiddenBytesState.value = hiddenBytes
                }

            } catch (_: CancellationException) {

                /*
                 * We can ignore this if the user enters data in quick succession.
                 */

            } catch (ex: Throwable) {

                showToast(Toast(ToastType.ERROR, "Can't decode hidden data."))

                ex.printStackTrace()
            }
        }

        val focusManager = LocalFocusManager.current

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
                        showToast,
                        passwordState.value
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
                SteganographyUtil.calculateApproximateHiddenSpaceInBytes(image)
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

            TextField(
                value = passwordState.value,
                onValueChange = { passwordState.value = it },
                label = { Text("Optional: Encryption password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.Companion.width(288.dp)
                    .onPreviewKeyEvent { keyEvent ->

                        if (keyEvent.key == Key.Enter || keyEvent.key == Key.Tab) {

                            focusManager.clearFocus()

                            true

                        } else {
                            false
                        }
                    }
            )

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
                            passwordState.value,
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
    showToast: (Toast) -> Unit,
    password: String
) = withContext(Dispatchers.Default) {

    val file = FileKit.openFilePicker(
        type = FileKitType.File(extensions = listOf("png", "PNG")),
        title = "Select PNG image to hide data in",
    )

    if (file != null) {

        try {

            blockUserInput.value = true

            /* Let the UI update */
            delay(UI_UPDATE_DELAY_MS)

            val bytes = file.readBytes()

            withContext(Dispatchers.Main) {
                selectedFileState.value = file
                originalBytesState.value = bytes
            }

            yield()

            val image = Image.Companion.makeFromEncoded(bytes)

            val hiddenBytes = SteganographyUtil.readLeastSignificantBits(image, password)

            withContext(Dispatchers.Main) {
                imageState.value = image
                hiddenBytesState.value = hiddenBytes
            }

        } catch (_: CancellationException) {

            /*
             * We can ignore this if the user quickly chooses another image.
             */

        } catch (ex: Throwable) {

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
    password: String,
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
            delay(UI_UPDATE_DELAY_MS)

            val bytesToHide = file.readBytes()

            yield()

            val modifiedImage = SteganographyUtil.writeLeastSignificantBits(
                image = image,
                fileName = file.name,
                bytes = bytesToHide,
                password = password
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
        delay(UI_UPDATE_DELAY_MS)

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
        delay(UI_UPDATE_DELAY_MS)

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
