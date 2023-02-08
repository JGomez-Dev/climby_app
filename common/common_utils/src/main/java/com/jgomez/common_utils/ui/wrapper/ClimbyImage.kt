package com.jgomez.common_utils.ui.wrapper

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import com.google.accompanist.drawablepainter.DrawablePainter

sealed interface ClimbyImage {
    @Immutable
    @JvmInline
    value class Resource(@DrawableRes val id: Int) : ClimbyImage
    @Immutable
    @JvmInline
    value class DrawableImage(val image: Drawable) : ClimbyImage
    @Immutable
    @JvmInline
    value class Vector(val image: ImageVector) : ClimbyImage
    @Immutable
    @JvmInline
    value class Custom(val image: Painter) : ClimbyImage
}

inline val ClimbyImage.painter
    @Composable get() = when ( this) {
        is ClimbyImage.Resource  ->  painterResource(id = id)
        is ClimbyImage.DrawableImage -> DrawablePainter(image)
        is ClimbyImage.Vector -> rememberVectorPainter(image = image)
        is ClimbyImage.Custom -> image
    }