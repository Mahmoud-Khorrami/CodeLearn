package com.mahapp1397.codelearn1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahapp1397.codelearn1.models.Sale
import com.mahapp1397.codelearn1.repository.F3Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class F3ViewModel @Inject constructor(private val f3Repository: F3Repository): ViewModel()
{
    fun saveSale(sale: Sale)
    {
        viewModelScope.launch {
            f3Repository.saveSale(sale)
        }
    }
}