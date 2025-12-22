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

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AppFooter() {

    val uriHandler = LocalUriHandler.current

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(32.dp)
            .background(Color.Black)
            .fillMaxWidth()
            .padding(
                horizontal = 2.dp
            )
            .clickable {
                uriHandler.openUri("https://stefan-oltmann.de")
            }
    ) {

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "Made by Stefan Oltmann",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.offset(y = -1.dp)
        )

        Spacer(modifier = Modifier.weight(1F))

        SponsorButton(
            onClick = {
                uriHandler.openUri("https://github.com/sponsors/StefanOltmann")
            }
        )
    }
}
