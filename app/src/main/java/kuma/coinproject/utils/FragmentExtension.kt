package kuma.coinproject.utils

import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

// livedata 가 resume 상태일때만 동작하도록 체크하는 함수
fun Fragment.isLiveDataResume() = viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED

fun Fragment.navigateUp() = findNavController().navigateUp()

fun Fragment.navigate(direction: NavDirections) = findNavController().navigate(direction)

fun Fragment.showToast(msg:String? ) = msg?.let { Toast.makeText(context , it, Toast.LENGTH_LONG).show() }

