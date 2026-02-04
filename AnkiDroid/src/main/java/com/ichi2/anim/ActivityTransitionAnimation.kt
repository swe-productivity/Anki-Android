//noinspection MissingCopyrightHeader #8659

package com.ichi2.anim

import android.app.Activity
import android.content.Context
import android.os.Build
import android.os.Parcelable
import android.util.LayoutDirection
import androidx.core.app.ActivityOptionsCompat
import com.ichi2.anki.R
import kotlinx.parcelize.Parcelize

object ActivityTransitionAnimation {
    private fun overrideTransition(
        activity: Activity,
        enter: Int,
        exit: Int,
        open: Boolean,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            val ty =
                if (open) {
                    Activity.OVERRIDE_TRANSITION_OPEN
                } else {
                    Activity.OVERRIDE_TRANSITION_CLOSE
                }
            activity.overrideActivityTransition(ty, enter, exit)
        } else {
            @Suppress("DEPRECATION", "deprecated in API34 for predictive back, must plumb through new open/close parameter")
            activity.overridePendingTransition(
                enter,
                exit,
            )
        }
    }

    fun slide(
        activity: Activity,
        direction: Direction,
        open: Boolean,
    ) {
        when (direction) {
            Direction.START ->
                if (isRightToLeft(activity)) {
                    overrideTransition(activity, R.anim.slide_right_in, R.anim.slide_right_out, open)
                } else {
                    overrideTransition(activity, R.anim.slide_left_in, R.anim.slide_left_out, open)
                }

            Direction.END ->
                if (isRightToLeft(activity)) {
                    overrideTransition(activity, R.anim.slide_left_in, R.anim.slide_left_out, open)
                } else {
                    overrideTransition(
                        activity,
                        R.anim.slide_right_in,
                        R.anim.slide_right_out,
                        open,
                    )
                }

            Direction.RIGHT ->
                overrideTransition(
                    activity,
                    R.anim.slide_right_in,
                    R.anim.slide_right_out,
                    open,
                )

            Direction.LEFT ->
                overrideTransition(
                    activity,
                    R.anim.slide_left_in,
                    R.anim.slide_left_out,
                    open,
                )

            Direction.FADE -> overrideTransition(activity, R.anim.fade_in, R.anim.fade_out, open)
            Direction.UP ->
                overrideTransition(
                    activity,
                    R.anim.slide_up_in,
                    R.anim.slide_up_out,
                    open,
                )

            Direction.DOWN ->
                overrideTransition(
                    activity,
                    R.anim.slide_down_in,
                    R.anim.slide_down_out,
                    open,
                )

            Direction.NONE -> overrideTransition(activity, R.anim.none, R.anim.none, open)
            Direction.DEFAULT -> {
            }
        }
    }

    fun getAnimationOptions(
        activity: Activity,
        direction: Direction?,
    ): ActivityOptionsCompat =
        when (direction) {
            Direction.START ->
                if (isRightToLeft(
                        activity,
                    )
                ) {
                    ActivityOptionsCompat.makeCustomAnimation(
                        activity,
                        R.anim.slide_right_in,
                        R.anim.slide_right_out,
                    )
                } else {
                    ActivityOptionsCompat.makeCustomAnimation(
                        activity,
                        R.anim.slide_left_in,
                        R.anim.slide_left_out,
                    )
                }

            Direction.END ->
                if (isRightToLeft(
                        activity,
                    )
                ) {
                    ActivityOptionsCompat.makeCustomAnimation(
                        activity,
                        R.anim.slide_left_in,
                        R.anim.slide_left_out,
                    )
                } else {
                    ActivityOptionsCompat.makeCustomAnimation(
                        activity,
                        R.anim.slide_right_in,
                        R.anim.slide_right_out,
                    )
                }

            Direction.RIGHT ->
                ActivityOptionsCompat.makeCustomAnimation(
                    activity,
                    R.anim.slide_right_in,
                    R.anim.slide_right_out,
                )

            Direction.LEFT ->
                ActivityOptionsCompat.makeCustomAnimation(
                    activity,
                    R.anim.slide_left_in,
                    R.anim.slide_left_out,
                )

            Direction.FADE ->
                ActivityOptionsCompat.makeCustomAnimation(
                    activity,
                    R.anim.fade_in,
                    R.anim.fade_out,
                )

            Direction.UP ->
                ActivityOptionsCompat.makeCustomAnimation(
                    activity,
                    R.anim.slide_up_in,
                    R.anim.slide_up_out,
                )

            Direction.DOWN ->
                ActivityOptionsCompat.makeCustomAnimation(
                    activity,
                    R.anim.slide_down_in,
                    R.anim.slide_down_out,
                )

            Direction.NONE ->
                ActivityOptionsCompat.makeCustomAnimation(
                    activity,
                    R.anim.none,
                    R.anim.none,
                )

            Direction.DEFAULT -> // this is the default animation, we shouldn't try to override it
                ActivityOptionsCompat.makeBasic()

            else -> ActivityOptionsCompat.makeBasic()
        }

    private fun isRightToLeft(c: Context): Boolean = c.resources.configuration.layoutDirection == LayoutDirection.RTL

    @Parcelize
    enum class Direction : Parcelable {
        START,
        END,
        FADE,
        UP,
        DOWN,
        RIGHT,
        LEFT,
        DEFAULT,
        NONE,
        ;

        /** @see getInverseTransition */
        fun invert(): Direction = getInverseTransition(this)
    }

    /**
     * @return inverse transition of [direction]
     * if there isn't one, return the same [direction]
     */
    fun getInverseTransition(direction: Direction): Direction =
        when (direction) {
            // Directional transitions which should return their opposites
            Direction.RIGHT -> Direction.LEFT
            Direction.LEFT -> Direction.RIGHT
            Direction.UP -> Direction.DOWN
            Direction.DOWN -> Direction.UP
            Direction.START -> Direction.END
            Direction.END -> Direction.START
            // Non-directional transitions which should return themselves
            Direction.FADE -> Direction.FADE
            Direction.DEFAULT -> Direction.DEFAULT
            Direction.NONE -> Direction.NONE
        }
}
