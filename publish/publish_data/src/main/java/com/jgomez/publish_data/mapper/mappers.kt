package com.jgomez.publish_data.mapper

import com.jgomez.publish_data.model.ProvinceResponse
import com.jgomez.publish_domain.model.Province

fun ProvinceResponse.toDomainProvince(): Province {
    return Province(
        name = this.name,
        numberTravels = this.numberTravels
    )
}