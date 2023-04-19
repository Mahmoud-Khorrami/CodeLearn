package com.mahapp1397.codelearn1.repository

import com.mahapp1397.codelearn1.database.MyDAO
import com.mahapp1397.codelearn1.models.Sale
import javax.inject.Inject

class F3Repository @Inject constructor(var myDAO: MyDAO)
{
    suspend fun saveSale(sale: Sale) = myDAO.saveSale(sale)
}