package io.github.dmitrytsyvtsyn.deps_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.compose.content
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.dmitrytsyvtsyn.core.fragment.navigate
import io.github.dmitrytsyvtsyn.core.theme.DIInternalsTheme
import io.github.dmitrytsyvtsyn.deps_list.composables.PostList
import io.github.dmitrytsyvtsyn.deps_list.viewmodel.PostListEffect
import io.github.dmitrytsyvtsyn.deps_list.viewmodel.PostListEvent
import io.github.dmitrytsyvtsyn.deps_list.viewmodel.PostListViewModel
import io.github.dmitrytsyvtsyn.post_detail.PostDetailFragment
import io.github.dmitrytsyvtsyn.post_list.R

class PostListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = content {
        val viewModel : PostListViewModel = viewModel()

        LaunchedEffect(key1 = Unit) {
            viewModel.effect.collect { effect ->
                val fragment = when (effect) {
                    is PostListEffect.View -> PostDetailFragment(effect.model.id)
                    is PostListEffect.Add -> PostDetailFragment()
                }
                parentFragmentManager.navigate(fragment)
            }
        }

        LaunchedEffect(key1 = Unit) {
            viewModel.handleEvent(PostListEvent.FetchAll)
        }

        DIInternalsTheme {
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(
                        shape = RoundedCornerShape(24.dp),
                        onClick = {
                            viewModel.handleEvent(PostListEvent.Add)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = stringResource(id = R.string.add_post)
                        )
                    }
                }
            ) { innerPadding ->
                val posts by viewModel.state.collectAsState()
                PostList(
                    modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
                    posts = posts,
                    onClick = { post ->
                        viewModel.handleEvent(PostListEvent.View(post))
                    },
                    onRemove = { post ->
                        viewModel.handleEvent(PostListEvent.Delete(post))
                    }
                )
            }
        }
    }

}