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

import APP_NAME
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.launch
import org.jetbrains.skia.Image
import saveFullBytes
import saveHiddenBytes
import ui.icons.AppIcon
import util.SteganographyUtil
import kotlin.math.max

@Composable
fun ContentView(
    scrollState: ScrollState,
    showToast: (String) -> Unit
) {

    Column(
        horizontalAlignment = Alignment.Companion.CenterHorizontally,
        modifier = Modifier.Companion
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        val coroutineScope = rememberCoroutineScope()

        var selectedFile: PlatformFile? by remember { mutableStateOf(null) }

        var originalBytes: ByteArray? by remember { mutableStateOf(null) }

        val image: Image? = remember(originalBytes) {

            try {

                originalBytes?.let { Image.Companion.makeFromEncoded(it) }

            } catch (ex: Exception) {

                showToast("Can't load image.")

                ex.printStackTrace()

                null
            }
        }

        Row(
            verticalAlignment = Alignment.Companion.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Icon(
                imageVector = AppIcon,
                contentDescription = null,
                modifier = Modifier.Companion.size(48.dp)
            )

            Text(
                text = APP_NAME,
                fontSize = 40.sp
            )
        }

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

                    val file = FileKit.openFilePicker(
                        type = FileKitType.File(extensions = listOf("png", "PNG")),
                        title = "Select PNG image to hide data in",
                    )

                    if (file != null) {

                        selectedFile = file

                        originalBytes = file.readBytes()
                    }
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
                .border(1.dp, Color.Companion.Black, MaterialTheme.shapes.medium)
        ) {

            if (image != null) {

                androidx.compose.foundation.Image(
                    bitmap = image.toComposeImageBitmap(),
                    contentDescription = null
                )
            }
        }

        Spacer(
            modifier = Modifier.Companion.height(8.dp)
        )

        if (image != null) {

            val storageSize = remember(image) {
                SteganographyUtil.calculateHiddenSpaceInBytes(image)
            }

            val hiddenBytes = remember(image) {
                SteganographyUtil.readLeastSignificantBits(image)
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

                if (hiddenBytes != null) {

                    val kbAmount = max(1, hiddenBytes.second.size / 1024)

                    Text(
                        text = "Currently stores $kbAmount kb of data:",
                        fontSize = 14.sp
                    )

                    Text(
                        text = hiddenBytes.first,
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

                        val file = FileKit.openFilePicker(
                            title = "Select secret data to hide in image"
                        )

                        if (file != null) {

                            if (file.size() > storageSize) {

                                showToast("File is too large to be hidden in image.")

                                return@launch
                            }

                            /*
                             * Replace the bytes
                             */

                            val bytesToHide = file.readBytes()

                            val modifiedImage = SteganographyUtil.writeLeastSignificantBits(
                                image = image,
                                fileName = file.name,
                                byteArray = bytesToHide
                            )

                            originalBytes = modifiedImage.encodeToData()!!.bytes
                        }
                    }
                }
            ) {

                Text("Hide data in image")
            }

            Button(
                modifier = Modifier.Companion.width(288.dp),
                onClick = {

                    if (hiddenBytes == null)
                        return@Button

                    coroutineScope.launch {

                        /* Write new bytes to file */

                        saveHiddenBytes(selectedFile!!, hiddenBytes.first, hiddenBytes.second)
                    }
                }
            ) {

                Text("Save hidden data to disk")
            }

            Button(
                modifier = Modifier.Companion.width(288.dp),
                onClick = {

                    if (originalBytes == null)
                        return@Button

                    coroutineScope.launch {

                        /* Write new bytes to file */

                        saveFullBytes(selectedFile!!, originalBytes!!)
                    }
                }
            ) {

                Text("Save modified image to disk")
            }
        }
    }
}
