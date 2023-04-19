package com.mahapp1397.codelearn1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahapp1397.codelearn1.R
import com.mahapp1397.codelearn1.databinding.F2ItemBinding
import com.mahapp1397.codelearn1.models.Sale
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.DecimalFormat
import javax.inject.Inject

class F2Adapter @AssistedInject constructor(
    @Assisted var items: List<Sale>,
    @ApplicationContext var context: Context,
    @Assisted var setOnClickListener: SetOnClickListener): RecyclerView.Adapter<F2Adapter.F2ViewHolder>()
{

    @AssistedFactory
    interface Factory
    {
        fun create(@Assisted items: List<Sale>,
                   @Assisted setOnClickListener: SetOnClickListener):F2Adapter
    }

    interface SetOnClickListener
    {
        fun onClick(sale: Sale)
    }

    @Inject
    lateinit var df: DecimalFormat

    class F2ViewHolder(var binding: F2ItemBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): F2ViewHolder
    {
        val binding = F2ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = F2ViewHolder(binding)

        //--------------------------------------

        holder.binding.cardView.setOnClickListener{

            val position = holder.adapterPosition
            val item = items[position]
            setOnClickListener.onClick(item)
        }

        //--------------------------------------

        return holder
    }

    override fun getItemCount(): Int
    {
        return items.size
    }

    override fun onBindViewHolder(holder: F2ViewHolder, position: Int)
    {
        holder.setIsRecyclable(false)
        val item = items[position]

        //------------------------------------

        val row = position+1
        holder.binding.row.text = "$row"
        holder.binding.productName.text = item.productName
        holder.binding.totalPrice.text =context.resources.getString(R.string.amount1, df.format(item.unitPrice * item.number))

    }
}