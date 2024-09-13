package com.dwh.gamesapp.core.presentation.utils.animations

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.dwh.gamesapp.core.domain.enums.StateValues
import com.dwh.gamesapp.core.domain.model.ScaleAndAlphaArgs

@Composable
fun scaleAndAlpha(
    args: ScaleAndAlphaArgs,
    animation: FiniteAnimationSpec<Float>,
): Pair<Float, Float> {
    val transitionStateValues = remember { MutableTransitionState(StateValues.PLACING).apply { targetState = StateValues.PLACED } }
    val transition = updateTransition(transitionStateValues, label = "")
    val alpha by transition.animateFloat(transitionSpec = { animation }, label = "") { state ->
        when (state) {
            StateValues.PLACING -> args.fromAlpha
            StateValues.PLACED -> args.toAlpha
        }
    }
    val scale by transition.animateFloat(transitionSpec = { animation }, label = "") { state ->
        when (state) {
            StateValues.PLACING -> args.fromScale
            StateValues.PLACED -> args.toScale
        }
    }
    return alpha to scale
}