package io.github.dmitrytsyvtsyn.post_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.compose.content
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.dmitrytsyvtsyn.core.bundle.parcelable
import io.github.dmitrytsyvtsyn.core.model.IdLong
import io.github.dmitrytsyvtsyn.core.theme.DIInternalsTheme
import io.github.dmitrytsyvtsyn.post_detail.composables.PostTextField
import io.github.dmitrytsyvtsyn.post_detail.viewmodel.PostDetailEffect
import io.github.dmitrytsyvtsyn.post_detail.viewmodel.PostDetailEvent
import io.github.dmitrytsyvtsyn.post_detail.viewmodel.PostDetailViewModel

class PostDetailFragment() : Fragment() {

    constructor(id: IdLong) : this() {
        arguments = bundleOf(ID_KEY to id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = content {
        val viewModel : PostDetailViewModel = viewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.effect.collect { effect ->
                when(effect) {
                    is PostDetailEffect.NavigateBack -> {
                        parentFragmentManager.popBackStack()
                    }
                }
            }
        }

        LaunchedEffect(key1 = Unit) {
            val id = arguments?.parcelable<IdLong>(ID_KEY) ?: IdLong.Empty
            viewModel.handleEvent(PostDetailEvent.FetchById(id))
        }

        DIInternalsTheme {
            Scaffold { innerPadding ->
                Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {

                    val state by viewModel.state.collectAsState()
                    val titleErrorResource = state.titleErrorResource
                    val contentErrorResource = state.contentErrorResource

                    Spacer(modifier = Modifier.size(16.dp))

                    PostTextField(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        value = state.title,
                        onValueChange = { text ->
                            viewModel.handleEvent(PostDetailEvent.ChangeTitle(text))
                        },
                        textStyle = MaterialTheme.typography.headlineMedium,
                        placeholder = stringResource(id = R.string.title),
                        singleLine = true,
                        isError = titleErrorResource.isNotEmpty
                    )

                    if (titleErrorResource.isNotEmpty) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            text = stringResource(id = titleErrorResource.value),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Spacer(modifier = Modifier.size(12.dp))

                    PostTextField(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        value = state.content,
                        onValueChange = { text ->
                            viewModel.handleEvent(PostDetailEvent.ChangeContent(text))
                        },
                        textStyle = MaterialTheme.typography.bodyLarge,
                        placeholder = stringResource(id = R.string.content),
                        minLines = 6,
                        isError = contentErrorResource.isNotEmpty
                    )

                    if (contentErrorResource.isNotEmpty) {
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                            text = stringResource(id = contentErrorResource.value),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.error
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp).weight(1f))

                    Button(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        onClick = {
                            viewModel.handleEvent(PostDetailEvent.Save)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.save),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium
                            ),
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }

    companion object {
        private const val ID_KEY = "PostDetailFragment_id_key"
    }
}