package com.nalepa.mateusz.database.spatial.infrastructure

import org.jetbrains.exposed.sql.*
import org.postgis.PGbox2d
import org.postgis.PGgeometry

class PointColumnType(val srid: Int = 4326) : ColumnType() {
    override fun sqlType() =
        "GEOMETRY(Point, $srid)"

    override fun valueFromDB(value: Any): Any =
        if (value is PGgeometry) {
            value.geometry
        } else {
            value
        }

    override fun notNullValueToDB(value: Any): Any {
        if (value is PostGisPoint) {
            if (value.srid == PostGisPoint.UNKNOWN_SRID) {
                value.srid = srid
            }
            return PGgeometry(value)
        }
        return value
    }
}


fun Table.point(name: String): Column<PostGisPoint> = registerColumn(name, PointColumnType())

infix fun ExpressionWithColumnType<*>.within(box: PGbox2d): Op<Boolean> = WithinOp(this, box)

class WithinOp(private val expr1: Expression<*>, private val box: PGbox2d) : Op<Boolean>() {
    override fun toQueryBuilder(queryBuilder: QueryBuilder) {
        expr1
            .like("ST_MakeEnvelope(${box.llb.x}, ${box.llb.y}, ${box.urt.x}, ${box.urt.y}, 4326)")
            .toQueryBuilder(queryBuilder)
    }
}

infix fun Expression<*>.like(pattern: String) = LikeOp(this, stringParam(pattern))