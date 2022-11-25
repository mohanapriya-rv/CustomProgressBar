package com.mpcoding.customprogressbar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mpcoding.custom.datamodel.CustomVerticalIndicator
import com.mpcoding.customprogressbar.databinding.FragmentFirstBinding

/**
 *Created by Mohanapriya R
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
        customVerticalIndicatorList.add(
            CustomVerticalIndicator(
                0f,
                450f,
                resources.getColor(R.color.white)
            )
        )
        customVerticalIndicatorList.add(
            CustomVerticalIndicator(
                450f,
                590F,
                resources.getColor(R.color.teal_200)
            )
        )
        customVerticalIndicatorList.add(
            CustomVerticalIndicator(
                590F,
                650F,
                resources.getColor(R.color.teal_700)
            )
        )

        binding.textviewFirst.setPercentage(customVerticalIndicatorList)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}