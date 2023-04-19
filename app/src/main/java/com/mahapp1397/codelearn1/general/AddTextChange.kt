package com.mahapp1397.codelearn1.general

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class AddTextChange @AssistedInject constructor(@Assisted var textInputLayout: TextInputLayout): TextWatcher
{

    @AssistedFactory
    interface Factory
    {
        fun create(textInputLayout: TextInputLayout): AddTextChange
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
    {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
    {
        textInputLayout.error = null
    }

    override fun afterTextChanged(s: Editable?)
    {
    }
}