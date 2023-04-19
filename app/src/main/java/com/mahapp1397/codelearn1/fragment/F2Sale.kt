package com.mahapp1397.codelearn1.fragment

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mahapp1397.codelearn1.R
import com.mahapp1397.codelearn1.adapter.F2Adapter
import com.mahapp1397.codelearn1.databinding.F2DetailsBinding
import com.mahapp1397.codelearn1.databinding.F2SaleBinding
import com.mahapp1397.codelearn1.general.AddTextChange
import com.mahapp1397.codelearn1.general.AppUtils
import com.mahapp1397.codelearn1.models.Sale
import com.mahapp1397.codelearn1.viewModel.F2ViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat
import javax.inject.Inject


@AndroidEntryPoint
class F2Sale : Fragment(), F2Adapter.SetOnClickListener
{
    private var _binding: F2SaleBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var appUtils: AppUtils
    @Inject
    lateinit var addTextChangeFactory: AddTextChange.Factory
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    @Inject
    lateinit var f2AdapterFactory: F2Adapter.Factory
    private lateinit var f2Adapter: F2Adapter
    private var items = ArrayList<Sale>()
    private val f2ViewModel: F2ViewModel by viewModels()
    @Inject
    lateinit var df: DecimalFormat

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = F2SaleBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        //--------------------------------------

        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        f2Adapter = f2AdapterFactory.create(items, this)
        binding.recyclerView.adapter = f2Adapter

        f2ViewModel.index()

        //--------------------------------------

        f2ViewModel.response.observe(viewLifecycleOwner){

            items.clear()
            items.addAll(it)
            f2Adapter.notifyDataSetChanged()
        }

        //--------------------------------------

        binding.newInvoice.setOnClickListener {
            findNavController().navigate(R.id.action_f2Sale_to_f3NewSale)
        }
    }

    override fun onClick(sale: Sale) {
        details(sale)
    }

    private fun details(sale: Sale)
    {
        val binding1 = F2DetailsBinding.inflate(LayoutInflater.from(context))
        val view: View = binding1.root
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.setView(view)
        alertDialogBuilder.setCancelable(false)
        alertDialogBuilder.setNeutralButton(getString(R.string.cancel), null)
        val alertDialog: AlertDialog = alertDialogBuilder.create()

        //-------------------------------------------

        binding1.title.text = getString(R.string.factor_number, (sale.id + 1).toString())
        binding1.productName.text = sale.productName
        binding1.number.text = sale.number.toString()
        binding1.unitPrice.text = getString(R.string.amount1, df.format(sale.unitPrice))
        binding1.totalPrice.text = getString(R.string.amount1, df.format(sale.unitPrice * sale.number))
        binding1.date.text = sale.date

        //-------------------------------------------

        alertDialog.setOnShowListener {

            val cancel = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL)
            cancel.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
            cancel.setOnClickListener {
                alertDialog.dismiss()
            }
        }

        //------------------------------------------------------------------------------------------

        alertDialog.window!!.setBackgroundDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.bkg3))
        alertDialog.show()
        val display = requireContext().resources.displayMetrics
        var width = display.widthPixels
        width = (width * (4.toDouble() / 5)).toInt()
        alertDialog.window!!.setLayout(width, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun onDestroy()
    {
        super.onDestroy()
        _binding = null
    }

}