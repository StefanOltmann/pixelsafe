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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.icons.IconGitHubSponsors

private val backgroundColor = Color(0xFF28292A)
private val heartColor = Color(0xFFEA4AAA)

@Composable
fun SponsorButton(
    onClick: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .background(backgroundColor, RoundedCornerShape(4.dp))
            .height(24.dp)
            .padding(horizontal = 8.dp)
            .clickable(onClick = onClick)
    ) {
        Icon(
            imageVector = IconGitHubSponsors,
            contentDescription = null,
            tint = heartColor,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = "Sponsor",
            fontSize = 14.sp,
            color = Color.White,
            maxLines = 1,
            modifier = Modifier.offset(y = -1.dp)
        )
    }
}
