package com.shu.models.profile

import com.shu.models.CinemaItem
import com.shu.models.collections.Collections

data class ProfileUi(
    val watched: List<CinemaItem>,
    val collections: List<Collections>,
    val interesting: List<CinemaItem>,
)
