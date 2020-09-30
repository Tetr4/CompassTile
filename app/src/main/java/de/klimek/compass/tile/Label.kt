package de.klimek.compass.tile

import de.klimek.compass.R
import de.klimek.compass.TileService

fun TileService.label(degrees: Float) = when (degrees) {
    in 0.0..22.5, in 337.5..360.0 -> getString(R.string.N)
    in 22.5..67.5 -> getString(R.string.NE)
    in 67.5..112.5 -> getString(R.string.E)
    in 112.5..157.5 -> getString(R.string.SE)
    in 157.5..202.5 -> getString(R.string.S)
    in 202.5..247.5 -> getString(R.string.SW)
    in 247.5..292.5 -> getString(R.string.W)
    in 292.5..337.5 -> getString(R.string.NW)
    else -> "$degrees°"
}
