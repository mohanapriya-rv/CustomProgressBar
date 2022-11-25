package com.mpcoding.customprogressbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mpcoding.custom.datamodel.CustomVerticalIndicator
import com.mpcoding.customprogressbar.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val customVerticalIndicatorList: MutableList<CustomVerticalIndicator> = mutableListOf()
        customVerticalIndicatorList.add(CustomVerticalIndicator(0f, 250f, R.color.purple_200))
        customVerticalIndicatorList.add(CustomVerticalIndicator(250f, 450F, R.color.teal_200))
        customVerticalIndicatorList.add(CustomVerticalIndicator(450F, 650F, R.color.white))

        binding.textviewFirst.setPercentage(customVerticalIndicatorList)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}