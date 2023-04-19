package com.mahapp1397.codelearn1.repository

import com.mahapp1397.codelearn1.database.MyDAO
import com.mahapp1397.codelearn1.models.Sale
import javax.inject.Inject

class F2Repository @Inject constructor(var myDAO: MyDAO)
{
    fun index() = myDAO.index()
}