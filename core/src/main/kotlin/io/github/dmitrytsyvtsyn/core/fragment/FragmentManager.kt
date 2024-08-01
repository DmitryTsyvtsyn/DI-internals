package io.github.dmitrytsyvtsyn.core.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.github.dmitrytsyvtsyn.core.R

fun FragmentManager.navigate(fragment: Fragment) {
    beginTransaction()
        .replace(R.id.fragment_container, fragment)
        .addToBackStack(fragment::class.simpleName)
        .commit()
}