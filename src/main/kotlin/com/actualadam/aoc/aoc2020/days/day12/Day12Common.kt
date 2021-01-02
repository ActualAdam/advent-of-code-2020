package com.actualadam.aoc.aoc2020.days.day12

import kotlin.math.absoluteValue

data class Position(
    val orientation: Orientation = Orientation.East,
    val location: Location = Location()
) {
    enum class Orientation(val degrees: Int) {
        North(0),
        East(90),
        South(180),
        West(270);

        companion object {
            private val orientationsByDegrees = values().associateBy { it.degrees }
            fun fromDegrees(degrees: Int): Orientation =
                orientationsByDegrees[degrees] ?: throw IllegalArgumentException("no specific orientation for $degrees")

            fun explement(degrees: Int): Int {
                require(degrees <= 360)
                return if (degrees == 0) 0 else 360 - degrees
            }
        }

        enum class RotationDirection {
            Left,
            Right;
        }

        /*
         * Returns a new Orientation with the given rotation applied. You can rotate left or right any number of degrees.
         */
        fun rotate(direction: RotationDirection, degrees: Int): Orientation {
            require(degrees >= 0)
            return when (direction) {
                RotationDirection.Left -> {
                    val degreesValue = (this.degrees - degrees) % 360
                    val leftAdjustedOrientation = if (degreesValue >= 0) {
                        degreesValue
                    } else {
                        explement(degreesValue.absoluteValue)
                    }
                    Orientation.fromDegrees(leftAdjustedOrientation)
                }
                RotationDirection.Right -> Orientation.fromDegrees((this.degrees + degrees) % 360)
            }
        }
    }
}

data class Location(
    val northSouth: Int = 0,
    val eastWest: Int = 0,
)
