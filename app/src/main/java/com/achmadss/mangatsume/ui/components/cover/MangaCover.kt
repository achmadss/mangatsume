package com.achmadss.mangatsume.ui.components.cover

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CollectionsBookmark
import androidx.compose.material.icons.outlined.CollectionsBookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.achmadss.mangatsume.R
import com.achmadss.mangatsume.common.rememberResourceBitmapPainter

enum class MangaCoverStyle {
    Inside, Outside
}

const val inLibraryDim = 0.65f

@Composable
fun MangaCover(
    modifier: Modifier = Modifier,
    style: MangaCoverStyle,
    inLibrary: Boolean = false,
    slug: String,
    title: String,
    imageUrl: String?,
    onClick: (String) -> Unit,
) {
    Column(
        modifier = modifier.clickable(
            role = Role.Button,
            onClick = { onClick(slug) },
        )
    ) {
        when(style) {
            MangaCoverStyle.Inside -> {
                Box {
                    var sizeImage by remember { mutableStateOf(IntSize.Zero) }
                    val gradient = Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)),
                        startY = sizeImage.height.toFloat().div(4),  // 1/4
                        endY = sizeImage.height.toFloat()
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        placeholder = ColorPainter(Color(0x1F888888)),
                        error = rememberResourceBitmapPainter(id = R.drawable.cover_error),
                        contentDescription = null,
                        modifier = modifier
                            .onGloballyPositioned { sizeImage = it.size }
                            .aspectRatio(2f / 3f)
                            .clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Crop,
                    )
                    if (inLibrary) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.Black.copy(alpha = inLibraryDim))
                                .padding(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(4.dp)
                                    .align(Alignment.TopStart)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.CollectionsBookmark,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clip(RoundedCornerShape(4.dp))
                                .background(gradient)
                        )
                    }
                    Text(
                        modifier = Modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)
                            .align(Alignment.BottomStart),
                        style = MaterialTheme.typography.bodySmall,
                        text = title,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            MangaCoverStyle.Outside -> {
                Box {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(imageUrl)
                            .crossfade(true)
                            .build(),
                        placeholder = ColorPainter(Color(0x1F888888)),
                        error = rememberResourceBitmapPainter(id = R.drawable.cover_error),
                        contentDescription = null,
                        modifier = modifier
                            .aspectRatio(2f / 3f)
                            .clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Crop,
                    )
                    if (inLibrary) {
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.Black.copy(alpha = inLibraryDim))
                                .padding(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(18.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(MaterialTheme.colorScheme.primary)
                                    .padding(4.dp)
                                    .align(Alignment.TopStart)
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.CollectionsBookmark,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }
                Text(
                    modifier = Modifier
                        .padding(vertical = 4.dp),
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                    text = title,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }

}