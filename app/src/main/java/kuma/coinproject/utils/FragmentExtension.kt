package kuma.coinproject.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle

fun Fragment.isLiveDataResume() = viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED