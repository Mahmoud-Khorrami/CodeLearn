package com.mahapp1397.codelearn1.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@Entity(tableName = "sales_table")
data class Sale @AssistedInject constructor(
        @Assisted("productName") var productName: String,
        @Assisted("number") var number: Int,
        @Assisted("unitPrice") var unitPrice: Long,
        @Assisted("date") var date: String)
{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted("productName") productName: String,
            @Assisted("number") number: Int,
            @Assisted("unitPrice") unitPrice: Long,
            @Assisted("date") date: String): Sale
    }
}