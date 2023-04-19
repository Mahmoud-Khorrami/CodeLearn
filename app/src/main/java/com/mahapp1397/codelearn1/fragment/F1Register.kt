package com.mahapp1397.codelearn1.fragment

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mahapp1397.codelearn1.R
import com.mahapp1397.codelearn1.databinding.F1RegisterBinding
import com.mahapp1397.codelearn1.general.AddTextChange
import com.mahapp1397.codelearn1.general.AppUtils
import com.mahapp1397.codelearn1.viewModel.F1RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class F1Register : Fragment()
{
    private var _binding: F1RegisterBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var addTextChangeFactory: AddTextChange.Factory
    @Inject
    lateinit var appUtils: AppUtils
    private lateinit var phoneNumber: String
    private var vCode = 0
    private val f1RegisterViewModel: F1RegisterViewModel by viewModels()
    @Inject
    lateinit var sharedPreferencesEditor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        _binding = F1RegisterBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)

        //-------------------------------------------

        binding.cardView1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim2))

        //-------------------------------------------

        binding.phoneNumber.addTextChangedListener(addTextChangeFactory.create(binding.til1))
        binding.validationCode.addTextChangedListener(addTextChangeFactory.create(binding.til2))

        //-------------------------------------------

        binding.approve1.setOnClickListener {

            when
            {
                binding.phoneNumber.text!!.isEmpty() ->
                    binding.til1.error = requireContext().resources.getString(R.string.enter_phone_number)

                binding.phoneNumber.text.toString().length < 11 ->
                    binding.til1.error = requireContext().resources.getString(R.string.enter_correct_phone_number)

                else ->
                {
                    phoneNumber = binding.phoneNumber.text.toString()
                    appUtils.hideKeyboard(requireActivity())
                    vCode = (1000..9999).random()
                    Toast.makeText(context, getString(R.string.v_code, vCode.toString()), Toast.LENGTH_SHORT).show()

                    //---------------------------------

                    binding.resend.visibility = View.GONE
                    f1RegisterViewModel.startTimer()
                    val anim = AnimationUtils.loadAnimation(context, R.anim.anim3)
                    anim.setAnimationListener(object : Animation.AnimationListener
                    {
                        override fun onAnimationStart(p0: Animation?)
                        {

                        }

                        override fun onAnimationEnd(p0: Animation?)
                        {
                            binding.cardView1.visibility = View.GONE
                            binding.cardView2.visibility = View.VISIBLE
                            binding.cardView2.startAnimation(
                                AnimationUtils.loadAnimation(context, R.anim.anim2))
                        }

                        override fun onAnimationRepeat(p0: Animation?)
                        {

                        }

                    })

                    binding.cardView1.startAnimation(anim)
                }
            }

        }

        //-------------------------------------------

        f1RegisterViewModel.time.observe(viewLifecycleOwner) {

            binding.timer.text = (if(((it / 1000) - 1)< 0) 0 else ((it / 1000) - 1)).toString()

            if (it < 2000)
                binding.resend.visibility = View.VISIBLE
        }

        //-------------------------------------------

        binding.resend.setOnClickListener{

            vCode = (1000..9999).random()
            Toast.makeText(context, getString(R.string.v_code, vCode.toString()), Toast.LENGTH_SHORT).show()
            binding.til2.error = ""
            binding.resend.visibility = View.GONE
            f1RegisterViewModel.startTimer()
        }

        //-------------------------------------------

        binding.approve2.setOnClickListener {

            if (binding.validationCode.text.toString() == vCode.toString())
            {
                sharedPreferencesEditor.putString("phoneNumber", phoneNumber)
                sharedPreferencesEditor.apply()
                Toast.makeText(context, getString(R.string.success_register), Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }

            else
                binding.til2.error = getString(R.string.incorrect_v_code)
        }

    }

    override fun onDestroy()
    {
        super.onDestroy()
        _binding = null
    }
}