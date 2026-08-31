package com.example.careplus.data.local

import androidx.room.TypeConverter
import com.example.careplus.data.model.AgeRange
import com.example.careplus.data.model.BidStatus
import com.example.careplus.data.model.CareType
import com.example.careplus.data.model.Consciousness
import com.example.careplus.data.model.EscrowStatus
import com.example.careplus.data.model.Mobility
import com.example.careplus.data.model.PatientGender
import com.example.careplus.data.model.RequestStatus
import com.example.careplus.data.model.WeightRange

class CareConverters {
    @TypeConverter
    fun fromCareType(value: CareType): String = value.name
    @TypeConverter
    fun toCareType(value: String): CareType = CareType.valueOf(value)

    @TypeConverter
    fun fromMobility(value: Mobility): String = value.name
    @TypeConverter
    fun toMobility(value: String): Mobility = Mobility.valueOf(value)

    @TypeConverter
    fun fromConsciousness(value: Consciousness): String = value.name
    @TypeConverter
    fun toConsciousness(value: String): Consciousness = Consciousness.valueOf(value)

    @TypeConverter
    fun fromWeightRange(value: WeightRange): String = value.name
    @TypeConverter
    fun toWeightRange(value: String): WeightRange = WeightRange.valueOf(value)

    @TypeConverter
    fun fromPatientGender(value: PatientGender): String = value.name
    @TypeConverter
    fun toPatientGender(value: String): PatientGender = PatientGender.valueOf(value)

    @TypeConverter
    fun fromAgeRange(value: AgeRange): String = value.name
    @TypeConverter
    fun toAgeRange(value: String): AgeRange = AgeRange.valueOf(value)

    @TypeConverter
    fun fromRequestStatus(value: RequestStatus): String = value.name
    @TypeConverter
    fun toRequestStatus(value: String): RequestStatus = RequestStatus.valueOf(value)

    @TypeConverter
    fun fromBidStatus(value: BidStatus): String = value.name
    @TypeConverter
    fun toBidStatus(value: String): BidStatus = BidStatus.valueOf(value)

    @TypeConverter
    fun fromEscrowStatus(value: EscrowStatus): String = value.name
    @TypeConverter
    fun toEscrowStatus(value: String): EscrowStatus = EscrowStatus.valueOf(value)

    @TypeConverter
    fun fromStringList(list: List<String>): String = list.joinToString(";;")
    @TypeConverter
    fun toStringList(data: String): List<String> = if (data.isEmpty()) emptyList() else data.split(";;")
}
