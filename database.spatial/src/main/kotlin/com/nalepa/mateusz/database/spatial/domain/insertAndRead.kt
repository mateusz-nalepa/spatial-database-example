package com.nalepa.mateusz.database.spatial.domain

//POI - Point Of Interest
//https://www.calculatorsoup.com/calculators/conversions/convert-decimal-degrees-to-degrees-minutes-seconds.php

//34.4456414
//
//0.4456414 * 60 = 26.738484 // 26 minuta
//0.738484 * 60 = 44.30904 // 44 sekunda

// Krakow
// 50.0466814
// 19.8647884

//50 stopni 2 minuta 48.05304 sekunda



//156.742  z GPS coord to minuty i sekundy
//156°44'31.2"




//156° bo przed przecinkiem 156
//po przecinku pomnoz przez 60, czyli 0.742 * 60 == 44.52
//44'
//pozostaly part pomnoz znowu przez 60, czyli 0,52 * 60 = 31.2
//31.2"

//https://www.latlong.net/

//Decimal Degrees = degrees + (minutes/60) + (seconds/3600)
//156°44'31.2"

//X = 156 + (44/60) + 31,2 / 3600) == 156.742
//156 + (44/60) + 31,2 / 3600)

//stopień [°], minuta ['] (0 to 59) i sekunda kątowa ["] (0 to 59.9999)

//1° = 60'
//1' = 60"

//23°26'13.7''

//max 7 liczb po przecinku
data class PointOfInterest(
    val locationDto: LocationDto,
    val name: String
)

//19.8914041 - wysokosc krakowa
//50.0159261, - szerokosc krakowa


//Czyli losuj max 6 liczb po przecinku!




