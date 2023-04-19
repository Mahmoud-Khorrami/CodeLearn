package com.mahapp1397.codelearn1.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahapp1397.codelearn1.models.Sale
import com.mahapp1397.codelearn1.repository.F2Repository
import com.mahapp1397.codelearn1.repository.F3Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class F2ViewModel @Inject constructor(private val f2Repository: F2Repository): ViewModel()
{
    var response = MutableLiveData<List<Sale>>()

    fun index()
    {
        viewModelScope.launch {
            f2Repository.index().collect{
                response.value = it
            }
        }
    }
}