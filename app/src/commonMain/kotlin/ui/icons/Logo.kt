package ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val Logo: ImageVector
    get() {
        if (_Logo != null) {
            return _Logo!!
        }
        _Logo = ImageVector.Builder(
            name = "Logo",
            defaultWidth = 256.dp,
            defaultHeight = 64.dp,
            viewportWidth = 256f,
            viewportHeight = 64f
        ).apply {
            path(fill = SolidColor(Color(0xFFFFFFFF))) {
                moveTo(0f, 0f)
                horizontalLineToRelative(256f)
                verticalLineToRelative(64f)
                horizontalLineToRelative(-256f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(79.89f, 36.05f)
                lineTo(79.89f, 47.63f)
                lineTo(76.02f, 47.63f)
                lineTo(76.02f, 18.01f)
                lineTo(86.94f, 18.01f)
                curveTo(90.19f, 18.01f, 92.69f, 18.84f, 94.56f, 20.51f)
                curveTo(96.39f, 22.14f, 97.31f, 24.34f, 97.31f, 27.05f)
                curveTo(97.31f, 29.93f, 96.39f, 32.13f, 94.6f, 33.72f)
                curveTo(92.81f, 35.26f, 90.23f, 36.05f, 86.89f, 36.05f)
                lineTo(79.89f, 36.05f)
                close()
                moveTo(79.89f, 32.84f)
                lineTo(86.94f, 32.84f)
                curveTo(89.02f, 32.84f, 90.6f, 32.34f, 91.73f, 31.39f)
                curveTo(92.85f, 30.39f, 93.39f, 28.97f, 93.39f, 27.09f)
                curveTo(93.39f, 25.34f, 92.85f, 23.93f, 91.73f, 22.89f)
                curveTo(90.6f, 21.8f, 89.1f, 21.26f, 87.14f, 21.22f)
                lineTo(79.89f, 21.22f)
                lineTo(79.89f, 32.84f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(105.81f, 47.63f)
                lineTo(102.01f, 47.63f)
                lineTo(102.01f, 25.64f)
                lineTo(105.81f, 25.64f)
                lineTo(105.81f, 47.63f)
                close()
                moveTo(101.72f, 19.8f)
                curveTo(101.72f, 19.18f, 101.89f, 18.68f, 102.26f, 18.22f)
                curveTo(102.64f, 17.8f, 103.18f, 17.59f, 103.93f, 17.59f)
                curveTo(104.68f, 17.59f, 105.22f, 17.8f, 105.6f, 18.22f)
                curveTo(105.97f, 18.68f, 106.18f, 19.18f, 106.18f, 19.8f)
                curveTo(106.18f, 20.39f, 105.97f, 20.89f, 105.6f, 21.3f)
                curveTo(105.22f, 21.72f, 104.68f, 21.93f, 103.93f, 21.93f)
                curveTo(103.18f, 21.93f, 102.64f, 21.72f, 102.26f, 21.3f)
                curveTo(101.89f, 20.89f, 101.72f, 20.39f, 101.72f, 19.8f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(119.21f, 33.68f)
                lineTo(124.08f, 25.64f)
                lineTo(128.46f, 25.64f)
                lineTo(121.25f, 36.51f)
                lineTo(128.71f, 47.63f)
                lineTo(124.33f, 47.63f)
                lineTo(119.25f, 39.38f)
                lineTo(114.17f, 47.63f)
                lineTo(109.79f, 47.63f)
                lineTo(117.21f, 36.51f)
                lineTo(110f, 25.64f)
                lineTo(114.38f, 25.64f)
                lineTo(119.21f, 33.68f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(141.2f, 48.05f)
                curveTo(138.2f, 48.05f, 135.79f, 47.05f, 133.91f, 45.09f)
                curveTo(132.04f, 43.13f, 131.08f, 40.51f, 131.08f, 37.22f)
                lineTo(131.08f, 36.55f)
                curveTo(131.08f, 34.34f, 131.49f, 32.43f, 132.33f, 30.72f)
                curveTo(133.16f, 28.97f, 134.33f, 27.64f, 135.83f, 26.68f)
                curveTo(137.33f, 25.72f, 138.95f, 25.22f, 140.7f, 25.22f)
                curveTo(143.58f, 25.22f, 145.79f, 26.18f, 147.41f, 28.05f)
                curveTo(148.99f, 29.93f, 149.79f, 32.63f, 149.79f, 36.13f)
                lineTo(149.79f, 37.72f)
                lineTo(134.87f, 37.72f)
                curveTo(134.91f, 39.88f, 135.54f, 41.63f, 136.74f, 42.97f)
                curveTo(137.99f, 44.3f, 139.54f, 44.97f, 141.37f, 44.97f)
                curveTo(142.7f, 44.97f, 143.83f, 44.72f, 144.79f, 44.18f)
                curveTo(145.7f, 43.59f, 146.49f, 42.88f, 147.2f, 42.01f)
                lineTo(149.49f, 43.8f)
                curveTo(147.66f, 46.63f, 144.87f, 48.05f, 141.2f, 48.05f)
                close()
                moveTo(140.7f, 28.3f)
                curveTo(139.2f, 28.3f, 137.91f, 28.84f, 136.91f, 29.97f)
                curveTo(135.87f, 31.05f, 135.24f, 32.63f, 134.99f, 34.59f)
                lineTo(145.99f, 34.59f)
                lineTo(145.99f, 34.34f)
                curveTo(145.91f, 32.43f, 145.37f, 30.93f, 144.45f, 29.89f)
                curveTo(143.54f, 28.84f, 142.29f, 28.3f, 140.7f, 28.3f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(154.44f, 16.39f)
                horizontalLineToRelative(3.75f)
                verticalLineToRelative(31.25f)
                horizontalLineToRelative(-3.75f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(173.55f, 34.43f)
                curveTo(170.22f, 33.47f, 167.76f, 32.3f, 166.22f, 30.89f)
                curveTo(164.72f, 29.47f, 163.97f, 27.76f, 163.97f, 25.68f)
                curveTo(163.97f, 23.34f, 164.89f, 21.43f, 166.76f, 19.89f)
                curveTo(168.6f, 18.39f, 171.05f, 17.59f, 174.01f, 17.59f)
                curveTo(176.05f, 17.59f, 177.89f, 18.01f, 179.47f, 18.8f)
                curveTo(181.05f, 19.55f, 182.3f, 20.68f, 183.18f, 22.05f)
                curveTo(184.05f, 23.43f, 184.47f, 24.93f, 184.47f, 26.59f)
                lineTo(180.55f, 26.59f)
                curveTo(180.55f, 24.8f, 179.97f, 23.39f, 178.85f, 22.34f)
                curveTo(177.72f, 21.34f, 176.1f, 20.8f, 174.01f, 20.8f)
                curveTo(172.1f, 20.8f, 170.6f, 21.26f, 169.51f, 22.09f)
                curveTo(168.43f, 22.93f, 167.89f, 24.14f, 167.89f, 25.64f)
                curveTo(167.89f, 26.84f, 168.43f, 27.84f, 169.43f, 28.68f)
                curveTo(170.47f, 29.51f, 172.22f, 30.26f, 174.68f, 30.97f)
                curveTo(177.14f, 31.68f, 179.05f, 32.43f, 180.43f, 33.26f)
                curveTo(181.85f, 34.09f, 182.85f, 35.05f, 183.51f, 36.18f)
                curveTo(184.22f, 37.3f, 184.55f, 38.59f, 184.55f, 40.09f)
                curveTo(184.55f, 42.51f, 183.6f, 44.43f, 181.72f, 45.88f)
                curveTo(179.85f, 47.3f, 177.35f, 48.05f, 174.22f, 48.05f)
                curveTo(172.18f, 48.05f, 170.3f, 47.63f, 168.51f, 46.88f)
                curveTo(166.76f, 46.09f, 165.39f, 45.01f, 164.43f, 43.68f)
                curveTo(163.51f, 42.3f, 163.01f, 40.76f, 163.01f, 39.05f)
                lineTo(166.93f, 39.05f)
                curveTo(166.93f, 40.84f, 167.6f, 42.26f, 168.93f, 43.3f)
                curveTo(170.26f, 44.34f, 172.01f, 44.84f, 174.22f, 44.84f)
                curveTo(176.3f, 44.84f, 177.89f, 44.43f, 178.97f, 43.59f)
                curveTo(180.05f, 42.76f, 180.6f, 41.59f, 180.6f, 40.13f)
                curveTo(180.6f, 38.68f, 180.1f, 37.59f, 179.1f, 36.8f)
                curveTo(178.05f, 35.97f, 176.22f, 35.22f, 173.55f, 34.43f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(202.57f, 47.63f)
                curveTo(202.32f, 47.22f, 202.15f, 46.43f, 202.02f, 45.3f)
                curveTo(200.27f, 47.13f, 198.19f, 48.05f, 195.73f, 48.05f)
                curveTo(193.57f, 48.05f, 191.82f, 47.43f, 190.4f, 46.22f)
                curveTo(189.02f, 44.97f, 188.32f, 43.43f, 188.32f, 41.55f)
                curveTo(188.32f, 39.26f, 189.19f, 37.47f, 190.94f, 36.18f)
                curveTo(192.69f, 34.93f, 195.15f, 34.3f, 198.27f, 34.3f)
                lineTo(201.94f, 34.3f)
                lineTo(201.94f, 32.55f)
                curveTo(201.94f, 31.26f, 201.57f, 30.18f, 200.77f, 29.43f)
                curveTo(199.98f, 28.64f, 198.82f, 28.26f, 197.32f, 28.26f)
                curveTo(195.94f, 28.26f, 194.82f, 28.59f, 193.94f, 29.26f)
                curveTo(193.02f, 29.93f, 192.57f, 30.76f, 192.57f, 31.72f)
                lineTo(188.77f, 31.72f)
                curveTo(188.77f, 30.64f, 189.15f, 29.55f, 189.94f, 28.55f)
                curveTo(190.73f, 27.51f, 191.77f, 26.72f, 193.11f, 26.09f)
                curveTo(194.44f, 25.51f, 195.94f, 25.22f, 197.52f, 25.22f)
                curveTo(200.07f, 25.22f, 202.02f, 25.84f, 203.48f, 27.14f)
                curveTo(204.9f, 28.39f, 205.65f, 30.14f, 205.73f, 32.34f)
                lineTo(205.73f, 42.47f)
                curveTo(205.73f, 44.51f, 205.98f, 46.13f, 206.48f, 47.3f)
                lineTo(206.48f, 47.63f)
                lineTo(202.57f, 47.63f)
                close()
                moveTo(196.32f, 44.76f)
                curveTo(197.48f, 44.76f, 198.61f, 44.47f, 199.65f, 43.84f)
                curveTo(200.73f, 43.26f, 201.48f, 42.43f, 201.94f, 41.47f)
                lineTo(201.94f, 36.97f)
                lineTo(199.02f, 36.97f)
                curveTo(194.4f, 36.97f, 192.11f, 38.3f, 192.11f, 41.01f)
                curveTo(192.11f, 42.18f, 192.48f, 43.09f, 193.27f, 43.76f)
                curveTo(194.07f, 44.43f, 195.07f, 44.76f, 196.32f, 44.76f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(213.48f, 47.63f)
                lineTo(213.48f, 28.55f)
                lineTo(209.98f, 28.55f)
                lineTo(209.98f, 25.64f)
                lineTo(213.48f, 25.64f)
                lineTo(213.48f, 23.34f)
                curveTo(213.48f, 21.01f, 214.1f, 19.18f, 215.35f, 17.89f)
                curveTo(216.6f, 16.59f, 218.4f, 15.97f, 220.73f, 15.97f)
                curveTo(221.56f, 15.97f, 222.44f, 16.09f, 223.31f, 16.3f)
                lineTo(223.1f, 19.34f)
                curveTo(222.44f, 19.22f, 221.77f, 19.18f, 221.06f, 19.18f)
                curveTo(219.85f, 19.18f, 218.9f, 19.51f, 218.23f, 20.26f)
                curveTo(217.56f, 20.97f, 217.23f, 21.97f, 217.23f, 23.3f)
                lineTo(217.23f, 25.64f)
                lineTo(221.94f, 25.64f)
                lineTo(221.94f, 28.55f)
                lineTo(217.23f, 28.55f)
                lineTo(217.23f, 47.63f)
                lineTo(213.48f, 47.63f)
                close()
            }
            path(fill = SolidColor(Color(0xFF000000))) {
                moveTo(234.75f, 48.05f)
                curveTo(231.75f, 48.05f, 229.33f, 47.05f, 227.46f, 45.09f)
                curveTo(225.58f, 43.13f, 224.62f, 40.51f, 224.62f, 37.22f)
                lineTo(224.62f, 36.55f)
                curveTo(224.62f, 34.34f, 225.04f, 32.43f, 225.87f, 30.72f)
                curveTo(226.71f, 28.97f, 227.87f, 27.64f, 229.37f, 26.68f)
                curveTo(230.87f, 25.72f, 232.5f, 25.22f, 234.25f, 25.22f)
                curveTo(237.12f, 25.22f, 239.33f, 26.18f, 240.96f, 28.05f)
                curveTo(242.54f, 29.93f, 243.33f, 32.63f, 243.33f, 36.13f)
                lineTo(243.33f, 37.72f)
                lineTo(228.41f, 37.72f)
                curveTo(228.46f, 39.88f, 229.08f, 41.63f, 230.29f, 42.97f)
                curveTo(231.54f, 44.3f, 233.08f, 44.97f, 234.91f, 44.97f)
                curveTo(236.25f, 44.97f, 237.37f, 44.72f, 238.33f, 44.18f)
                curveTo(239.25f, 43.59f, 240.04f, 42.88f, 240.75f, 42.01f)
                lineTo(243.04f, 43.8f)
                curveTo(241.21f, 46.63f, 238.41f, 48.05f, 234.75f, 48.05f)
                close()
                moveTo(234.25f, 28.3f)
                curveTo(232.75f, 28.3f, 231.46f, 28.84f, 230.46f, 29.97f)
                curveTo(229.41f, 31.05f, 228.79f, 32.63f, 228.54f, 34.59f)
                lineTo(239.54f, 34.59f)
                lineTo(239.54f, 34.34f)
                curveTo(239.46f, 32.43f, 238.91f, 30.93f, 238f, 29.89f)
                curveTo(237.08f, 28.84f, 235.83f, 28.3f, 234.25f, 28.3f)
                close()
            }
            path(fill = SolidColor(Color(0xFF012C47))) {
                moveTo(37f, 38f)
                lineTo(33f, 38f)
                lineTo(33.81f, 33.53f)
                curveTo(32.91f, 33.1f, 32.35f, 32.2f, 32.35f, 31.14f)
                curveTo(32.35f, 29.66f, 33.5f, 28.44f, 34.97f, 28.44f)
                curveTo(36.45f, 28.44f, 37.65f, 29.64f, 37.65f, 31.12f)
                curveTo(37.65f, 32.16f, 37.06f, 33.06f, 36.19f, 33.5f)
                lineTo(37f, 38f)
                close()
                moveTo(44.39f, 25f)
                lineTo(25.64f, 25f)
                curveTo(24.76f, 25f, 24.01f, 25.74f, 24.01f, 26.62f)
                lineTo(24f, 27f)
                lineTo(24f, 42f)
                lineTo(46f, 42f)
                lineTo(46f, 27f)
                lineTo(45.99f, 26.53f)
                curveTo(45.99f, 25.65f, 45.27f, 25f, 44.39f, 25f)
            }
            path(fill = SolidColor(Color(0xFF012C47))) {
                moveTo(42f, 22.35f)
                lineTo(42f, 25f)
                lineTo(39f, 25f)
                lineTo(39f, 22f)
                curveTo(39f, 19.83f, 37.17f, 18.45f, 35.02f, 18.45f)
                curveTo(32.86f, 18.45f, 31f, 19.83f, 31f, 22f)
                lineTo(31f, 25f)
                lineTo(28f, 25f)
                lineTo(28.01f, 22.35f)
                curveTo(28.01f, 18.5f, 31.17f, 15.37f, 35.02f, 15.37f)
                curveTo(36.94f, 15.37f, 38.69f, 16.15f, 39.95f, 17.41f)
                curveTo(41.21f, 18.67f, 42f, 20.42f, 42f, 22.35f)
            }
            path(fill = SolidColor(Color(0xFF30A1CB))) {
                moveTo(35f, 2.73f)
                lineTo(35f, 8.96f)
                curveTo(37.49f, 10.34f, 40.38f, 11.9f, 43f, 13f)
                lineTo(43f, 7f)
                curveTo(40.08f, 5.66f, 37.19f, 4.07f, 35f, 2.73f)
            }
            path(fill = SolidColor(Color(0xFF30A1CB))) {
                moveTo(42f, 51f)
                lineTo(44f, 51f)
                lineTo(44f, 46f)
                lineTo(50f, 46f)
                lineTo(50f, 41f)
                lineTo(54f, 41f)
                lineTo(54f, 21f)
                lineTo(48f, 21f)
                lineTo(48f, 17f)
                lineTo(43f, 17f)
                lineTo(43f, 13f)
                lineTo(47f, 13f)
                lineTo(47f, 9f)
                curveTo(48.6f, 9.54f, 50.49f, 10f, 52f, 10.31f)
                lineTo(52f, 17f)
                lineTo(56f, 17f)
                lineTo(56.01f, 11.39f)
                curveTo(57.49f, 11.74f, 58.9f, 12.02f, 60.22f, 12.25f)
                curveTo(60.56f, 12.31f, 60.82f, 12.6f, 60.84f, 12.95f)
                curveTo(60.9f, 14.13f, 60.94f, 15.49f, 60.92f, 17f)
                lineTo(60.92f, 17.41f)
                curveTo(60.9f, 18.8f, 60.84f, 20.32f, 60.72f, 21.95f)
                lineTo(60.72f, 22.02f)
                curveTo(60.68f, 22.55f, 60.63f, 23.09f, 60.58f, 23.65f)
                curveTo(60.04f, 29.12f, 59.54f, 34.36f, 57.04f, 40.12f)
                curveTo(56.99f, 40.24f, 56.94f, 40.35f, 56.89f, 40.47f)
                curveTo(56.63f, 41.04f, 56.35f, 41.62f, 56.06f, 42.21f)
                curveTo(55.54f, 43.22f, 55f, 44.18f, 54.44f, 45.08f)
                curveTo(53.78f, 46.14f, 53.1f, 47.11f, 52.42f, 48.01f)
                curveTo(51.97f, 48.61f, 51.52f, 49.16f, 51.08f, 49.69f)
                curveTo(49.92f, 51.07f, 48.8f, 52.21f, 47.81f, 53.14f)
                curveTo(47.35f, 53.57f, 46.91f, 53.96f, 46.53f, 54.3f)
                curveTo(46.18f, 54.61f, 45.86f, 54.88f, 45.58f, 55.11f)
                curveTo(44.78f, 55.8f, 43.98f, 56.42f, 43.2f, 56.99f)
                curveTo(42.24f, 57.71f, 41.29f, 58.35f, 40.38f, 58.92f)
                curveTo(38.42f, 60.15f, 36.51f, 61.3f, 35f, 61.99f)
                lineTo(35f, 55f)
                lineTo(40f, 55f)
                lineTo(40f, 52f)
                lineTo(37f, 52f)
                lineTo(37f, 47f)
                lineTo(42f, 47f)
                lineTo(42f, 51f)
                close()
            }
            path(fill = SolidColor(Color(0xFF012C47))) {
                moveTo(18f, 16.29f)
                lineTo(18f, 19f)
                lineTo(22f, 19f)
                lineTo(22f, 15f)
                curveTo(22.61f, 14.72f, 23.47f, 14.27f, 24f, 14f)
                lineTo(24f, 16f)
                lineTo(28f, 16f)
                lineTo(28f, 12f)
                curveTo(30.43f, 10.85f, 32.91f, 9.88f, 35f, 8.96f)
                lineTo(35f, 2.73f)
                curveTo(31.98f, 4.58f, 28.26f, 6.56f, 23.86f, 8.3f)
                curveTo(18.62f, 10.37f, 13.8f, 11.56f, 9.8f, 12.25f)
                curveTo(9.46f, 12.31f, 9.2f, 12.6f, 9.18f, 12.95f)
                curveTo(9.12f, 14.2f, 9.08f, 15.65f, 9.1f, 17.27f)
                curveTo(9.12f, 19.18f, 9.21f, 21.32f, 9.44f, 23.65f)
                curveTo(10.04f, 29.77f, 10.6f, 35.61f, 13.96f, 42.21f)
                curveTo(17.49f, 49.15f, 22.25f, 53.26f, 24.44f, 55.11f)
                curveTo(28.34f, 58.42f, 32.21f, 60.72f, 35f, 61.99f)
                lineTo(35f, 55f)
                lineTo(31f, 55f)
                lineTo(31f, 52f)
                lineTo(26f, 52f)
                lineTo(26f, 46f)
                lineTo(20f, 46f)
                lineTo(20f, 40f)
                lineTo(17f, 40f)
                lineTo(17f, 34f)
                lineTo(14f, 34f)
                lineTo(14f, 27f)
                lineTo(20f, 27f)
                lineTo(20f, 22f)
                lineTo(14.98f, 22.01f)
                lineTo(14.99f, 17.12f)
                lineTo(18f, 16.29f)
                close()
            }
            path(fill = SolidColor(Color(0xFF012C47))) {
                moveTo(15f, 30.5f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-5f)
                close()
            }
            path(fill = SolidColor(Color(0xFF76CDE2))) {
                moveTo(60.92f, 17f)
                lineTo(60.92f, 17.41f)
                curveTo(60.9f, 18.8f, 60.84f, 20.32f, 60.72f, 21.95f)
                lineTo(60.72f, 22f)
                lineTo(56f, 22f)
                lineTo(56f, 17f)
                lineTo(60.92f, 17f)
                close()
            }
            path(fill = SolidColor(Color(0xFF30A1CB))) {
                moveTo(30.99f, 52f)
                horizontalLineToRelative(4.01f)
                verticalLineToRelative(4f)
                horizontalLineToRelative(-4.01f)
                close()
            }
            path(fill = SolidColor(Color(0xFF76CDE2))) {
                moveTo(54f, 27f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-4f)
                close()
            }
            path(fill = SolidColor(Color(0xFF30A1CB))) {
                moveTo(50f, 27f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-4f)
                close()
            }
            path(fill = SolidColor(Color(0xFF76CDE2))) {
                moveTo(50f, 37f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(4f)
                horizontalLineToRelative(-4f)
                close()
            }
            path(fill = SolidColor(Color(0xFF76CDE2))) {
                moveTo(28f, 46f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(4f)
                horizontalLineToRelative(-4f)
                close()
            }
            path(fill = SolidColor(Color(0xFF30A1CB))) {
                moveTo(24f, 37f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-5f)
                close()
            }
            path(fill = SolidColor(Color(0xFF30A1CB))) {
                moveTo(41f, 32f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF012C47)),
                fillAlpha = 0.7f,
                strokeAlpha = 0.7f
            ) {
                moveTo(167.5f, 54f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF012C47)),
                fillAlpha = 0.7f,
                strokeAlpha = 0.7f
            ) {
                moveTo(143f, 15.96f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF012C47)),
                fillAlpha = 0.7f,
                strokeAlpha = 0.7f
            ) {
                moveTo(84f, 8.46f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF30A1CB)),
                fillAlpha = 0.7f,
                strokeAlpha = 0.7f
            ) {
                moveTo(116f, 10.96f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF30A1CB)),
                fillAlpha = 0.7f,
                strokeAlpha = 0.7f
            ) {
                moveTo(126f, 53f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF30A1CB)),
                fillAlpha = 0.7f,
                strokeAlpha = 0.7f
            ) {
                moveTo(208.5f, 53f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF30A1CB)),
                fillAlpha = 0.7f,
                strokeAlpha = 0.7f
            ) {
                moveTo(199.5f, 13.46f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF30A1CB)),
                fillAlpha = 0.7f,
                strokeAlpha = 0.7f
            ) {
                moveTo(163f, 8.46f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF30A1CB)),
                fillAlpha = 0.7f,
                strokeAlpha = 0.7f
            ) {
                moveTo(89f, 46.04f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-5f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFF30A1CB)),
                fillAlpha = 0.7f,
                strokeAlpha = 0.7f
            ) {
                moveTo(236f, 15.96f)
                horizontalLineToRelative(5f)
                verticalLineToRelative(5f)
                horizontalLineToRelative(-5f)
                close()
            }
        }.build()

        return _Logo!!
    }

@Suppress("ObjectPropertyName")
private var _Logo: ImageVector? = null
