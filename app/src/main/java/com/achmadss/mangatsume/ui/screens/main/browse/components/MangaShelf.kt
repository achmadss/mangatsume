package com.achmadss.mangatsume.ui.screens.main.browse.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.achmadss.domain.enums.MangaTrendingType
import com.achmadss.domain.models.MangaModel
import com.achmadss.mangatsume.common.noRippleClickable
import com.achmadss.mangatsume.ui.components.cover.MangaCover
import com.achmadss.mangatsume.ui.components.cover.MangaCoverStyle

@Composable
fun MangaShelf(
    modifier: Modifier = Modifier,
    type: MangaTrendingType,
    data: List<MangaModel>,
    onMangaClick: (slug: String) -> Unit,
    onArrowClick: (MangaTrendingType) -> Unit,
) {
    val density = LocalDensity.current
    Column(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .noRippleClickable { onArrowClick(type) }
                .padding(bottom = 20.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = type.label,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "",
            )
        }
//        if (data.isEmpty()) {
//            Box(modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//                .wrapContentSize(Alignment.Center)
//            ) {
//                CircularProgressIndicator()
//            }
//        }
        AnimatedVisibility(
            visible = data.isEmpty().not(),
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
        ) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item { Spacer(modifier = Modifier.width(4.dp)) }
                items(data) {
                    val inLibrary = (0..1).random()
                    MangaCover(
                        modifier = Modifier
                            .width(96.dp),
                        style = MangaCoverStyle.Outside,
                        inLibrary = inLibrary != 0,
                        slug = it.slug,
                        title = it.title,
                        imageUrl = it.coverUrl,
                        onClick = onMangaClick
                    )
                }
                item { Spacer(modifier = Modifier.width(4.dp)) }
            }
        }
    }
}