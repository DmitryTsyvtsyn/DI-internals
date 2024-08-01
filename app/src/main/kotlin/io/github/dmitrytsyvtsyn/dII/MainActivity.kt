package io.github.dmitrytsyvtsyn.dII

import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import io.github.dmitrytsyvtsyn.core.fragment.navigate
import io.github.dmitrytsyvtsyn.deps_list.PostListFragment
import io.github.dmitrytsyvtsyn.core.R as coreR

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        val fragmentContainer = FrameLayout(this)
        fragmentContainer.id = coreR.id.fragment_container
        fragmentContainer.layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        setContentView(fragmentContainer)

        if (savedInstanceState == null) {
            supportFragmentManager.navigate(PostListFragment())
        }
    }
}
