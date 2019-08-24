package com.example.made.activity

import android.os.Bundle
import androidx.fragment.app.Fragment

class FragmentStateHelper(val fragmentManager: androidx.fragment.app.FragmentManager) {
    private val fragmentSavedStates = mutableMapOf<Int, Fragment.SavedState?>()

    fun restoreState(fragment: androidx.fragment.app.Fragment, key: Int) {
        fragmentSavedStates[key]?.let { savedState ->
            if (!fragment.isAdded) {
                fragment.setInitialSavedState(savedState)
            }
        }
    }

    fun saveState(fragment: Fragment, key: Int) {
        if (fragment.isAdded) {
            fragmentSavedStates[key] = fragmentManager.saveFragmentInstanceState(fragment)
        }
    }
    fun saveHelperState(): Bundle {
        val state = Bundle()

        fragmentSavedStates.forEach { (key, fragmentState) ->
            state.putParcelable(key.toString(), fragmentState)
        }

        return state
    }

    fun restoreHelperState(savedState: Bundle) {
        fragmentSavedStates.clear()
        savedState.classLoader = this.javaClass.classLoader
        savedState.keySet().forEach { key ->
            fragmentSavedStates[key.toInt()] = savedState.getParcelable(key)
        }
    }
}