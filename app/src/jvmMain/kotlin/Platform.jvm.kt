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

import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.openFileSaver
import io.github.vinceglb.filekit.write

private val downloadsDir =
    PlatformFile(System.getProperty("user.home") + "/Downloads")

actual suspend fun saveBytes(
    fileName: String,
    bytes: ByteArray
): Boolean {

    val destinationFile = FileKit.openFileSaver(
        suggestedName = fileName.substringBeforeLast("."),
        extension = fileName.substringAfter(".", "data"),
        directory = downloadsDir
    )

    destinationFile?.write(bytes)

    return destinationFile != null
}
