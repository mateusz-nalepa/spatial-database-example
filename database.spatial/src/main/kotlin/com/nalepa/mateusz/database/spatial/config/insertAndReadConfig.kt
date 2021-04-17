package com.nalepa.mateusz.database.spatial.config

import com.nalepa.mateusz.database.spatial.infrastructure.insertAndRead.InsertAndReadRepository
import com.nalepa.mateusz.database.spatial.infrastructure.insertAndRead.SpatialRepository
import com.nalepa.mateusz.database.spatial.infrastructure.insertAndRead.XYRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class InsertAndReadConfig(@Value("\${poiType}") val poiType: String) {

    @Bean
    fun insertAndReadRepository(): InsertAndReadRepository {
        return when (poiType) {
            "spatial" -> SpatialRepository()
            "xy" -> XYRepository()
            else -> throw IllegalArgumentException("nie dziala XD")
        }
    }

}
