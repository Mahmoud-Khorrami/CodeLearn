package com.mahapp1397.codelearn1.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mahapp1397.codelearn1.R
import com.mahapp1397.codelearn1.databinding.F0LoginBinding
import com.mahapp1397.codelearn1.general.AddTextChange
import com.mahapp1397.codelearn1.general.AppUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class F0Login : Fragment()
{
    private var _binding: F0LoginBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var appUtils: AppUtils
    @Inject
    lateinit var addTextChangeFactory: AddTextChange.Factory
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = F0LoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        //-------------------------------------------

        binding.cardView.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim0))

        //-------------------------------------------

        val phoneNumber = sharedPreferences.getString("phoneNumber", null)

        //-------------------------------------------

        binding.phoneNumber.addTextChangedListener(addTextChangeFactory.create(binding.til1))

        //-------------------------------------------

        binding.enter.setOnClickListener {

            when
            {
                binding.phoneNumber.text!!.isEmpty() ->
                    binding.til1.error = requireContext().resources.getString(R.string.enter_phone_number)

                binding.phoneNumber.text.toString().length < 11 ->
                    binding.til1.error = requireContext().resources.getString(R.string.enter_correct_phone_number)

                else ->
                {
                    appUtils.hideKeyboard(requireActivity())

                    if (phoneNumber == null)
                        binding.til1.error = requireContext().resources.getString(R.string.phone_number_not_registered)

                    else if (binding.phoneNumber.text.toString() != phoneNumber)
                        binding.til1.error = requireContext().resources.getString(R.string.phone_number_not_registered)

                    else
                        findNavController().navigate(R.id.action_f0Login_to_f2Sale)

                }
            }

        }


        //-------------------------------------------

        binding.register.setOnClickListener {
            findNavController().navigate(R.id.action_f0Login_to_f1Register)
        }
    }

    override fun onDestroy()
    {
        super.onDestroy()
        _binding = null
    }
}