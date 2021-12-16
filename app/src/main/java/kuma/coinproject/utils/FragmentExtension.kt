package kuma.coinproject.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

fun Fragment.isLiveDataResume() = viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED

fun Fragment.navigateUp() = findNavController().navigateUp()

fun Fragment.navigate(direction: NavDirections) = findNavController().navigate(direction)