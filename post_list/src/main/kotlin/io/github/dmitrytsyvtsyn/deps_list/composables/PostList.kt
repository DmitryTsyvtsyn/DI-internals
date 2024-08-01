package io.github.dmitrytsyvtsyn.deps_list.composables

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.dmitrytsyvtsyn.core.model.PostModel
import io.github.dmitrytsyvtsyn.post_list.R
import kotlinx.collections.immutable.PersistentList

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PostList(
    modifier: Modifier = Modifier,
    posts: PersistentList<PostModel>,
    onClick: (PostModel) -> Unit,
    onRemove: (PostModel) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            count = posts.size,
            key = { index -> posts[index].id }
        ) { index ->
            val post = posts[index]

            val dropdownExpanded = remember { mutableStateOf(false) }

            Column(modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(12.dp))
                .background(MaterialTheme.colorScheme.surfaceContainer)
                .combinedClickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(bounded = true),
                    onClick = { onClick.invoke(post) },
                    onLongClick = { dropdownExpanded.value = true }
                )
                .padding(12.dp)
            ) {
                Text(
                    text = post.title,
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = post.content,
                    style = MaterialTheme.typography.bodyLarge
                )

                if (dropdownExpanded.value) {
                    DropdownMenu(
                        expanded = true,
                        onDismissRequest = { dropdownExpanded.value = false },
                    ) {
                        DropdownMenuItem(
                            text = {  Text(stringResource(id = R.string.remove_post)) },
                            onClick = {
                                onRemove.invoke(post)
                                dropdownExpanded.value = false
                            }
                        )
                    }
                }
            }
        }
    }
}