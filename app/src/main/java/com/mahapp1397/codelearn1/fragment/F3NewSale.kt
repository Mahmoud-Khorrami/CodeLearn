package com.mahapp1397.codelearn1.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aminography.primedatepicker.picker.callback.SingleDayPickCallback
import com.mahapp1397.codelearn1.R
import com.mahapp1397.codelearn1.databinding.F3NewSaleBinding
import com.mahapp1397.codelearn1.general.AddTextChange
import com.mahapp1397.codelearn1.general.Comma
import com.mahapp1397.codelearn1.general.DatePicker
import com.mahapp1397.codelearn1.models.Sale
import com.mahapp1397.codelearn1.viewModel.F3ViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import javax.inject.Inject


@AndroidEntryPoint
class F3NewSale : Fragment()
{
    private var _binding: F3NewSaleBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var addTextChangeFactory: AddTextChange.Factory
    @Inject
    lateinit var commaFactory: Comma.Factory
    @Inject
    lateinit var datePicker: DatePicker
    @Inject
    lateinit var saleFactory: Sale.Factory
    private val f3ViewModel: F3ViewModel by viewModels()
    private lateinit var date: String
    @Inject
    lateinit var df: DecimalFormat

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = F3NewSaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        //-------------------------------------------

        binding.productName.addTextChangedListener(addTextChangeFactory.create(binding.til1))
        binding.number.addTextChangedListener(addTextChangeFactory.create(binding.til2))
        binding.unitPrice.addTextChangedListener(addTextChangeFactory.create(binding.til3))
        binding.date.addTextChangedListener(addTextChangeFactory.create(binding.til5))

        val comma = commaFactory.create(binding.unitPrice)
        binding.unitPrice.addTextChangedListener(comma)

        //----------------------------------------

        binding.number.addTextChangedListener {

            if (binding.unitPrice.text.toString().isNotEmpty() && binding.number.text.toString().isNotEmpty())
            {
                val unitPrice = comma.trimCommaOfString(binding.unitPrice.text.toString())
                val totalPrice = unitPrice.toLong() * binding.number.text.toString().toInt()
                binding.totalPrice.setText(getString(R.string.amount1, df.format(totalPrice)))
            }

            else
                binding.totalPrice.setText("")
        }

        //----------------------------------------

        binding.unitPrice.addTextChangedListener {

            if (binding.unitPrice.text.toString().isNotEmpty() && binding.number.text.toString().isNotEmpty())
            {
                val unitPrice = comma.trimCommaOfString(binding.unitPrice.text.toString())
                val totalPrice = unitPrice.toLong() * binding.number.text.toString().toInt()
                binding.totalPrice.setText(getString(R.string.amount1, df.format(totalPrice)))
            }

            else
                binding.totalPrice.setText("")
        }

        //----------------------------------------

        val callback = SingleDayPickCallback{

            date = getString(R.string.g_date, it.dayOfMonth.toString(), it.monthName, it.year.toString())
            binding.date.setText(date)
        }

        binding.date.setOnClickListener {
            datePicker.getDate(callback, parentFragmentManager, "")
        }

        //----------------------------------------

        binding.approve.setOnClickListener{

            val productName = binding.productName.text.toString()
            val number = binding.number.text.toString()
            val unitPrice = binding.unitPrice.text.toString()

            when
            {
                productName.isEmpty() ->
                {
                    binding.scrollView.scrollTo(0, binding.til1.top)
                    binding.til1.error = getString(R.string.enter_product_name)
                }

                number.isEmpty() ->
                {
                    binding.scrollView.scrollTo(0, binding.til2.top)
                    binding.til2.error = getString(R.string.enter_number)
                }

                unitPrice.isEmpty() ->
                {
                    binding.scrollView.scrollTo(0, binding.til3.top)
                    binding.til3.error = getString(R.string.enter_unit_price)
                }

                !this::date.isInitialized ->
                {
                    binding.scrollView.scrollTo(0, binding.til5.top)
                    binding.til5.error = getString(R.string.enter_date)
                }

                else ->
                {
                    val unitPrice1 = comma.trimCommaOfString(unitPrice).toLong()
                    val sale = saleFactory.create(productName, number.toInt(), unitPrice1, date)
                    f3ViewModel.saveSale(sale)
                    findNavController().popBackStack()
                }

            }
        }

    }

    override fun onDestroy()
    {
        super.onDestroy()
        _binding = null
    }
}