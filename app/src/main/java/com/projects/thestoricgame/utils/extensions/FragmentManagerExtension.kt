package com.projects.thestoricgame.utils.extensions

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.Fragment

inline fun FragmentManager.transaction(function : FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().function().commit()
}

fun FragmentManager.addFragment(fragment : Fragment, fragId : Int) {
    transaction { add(fragId, fragment) }
}

fun FragmentManager.replaceFragment(fragment : Fragment, fragId : Int) {
    transaction { replace(fragId, fragment) }
}

fun FragmentManager.removeFragment(fragment : Fragment) {
    transaction { remove(fragment) }
}