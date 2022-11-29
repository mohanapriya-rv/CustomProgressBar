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
        val total = 60
        val first = 5
        val second = 15
        val third = 20
        val fourth = 20

        initComponent(60f, listOf<Float>(5f, 1f, 44f, 10f))

        super.onViewCreated(view, savedInstanceState)
    }

    private fun initComponent(
        total: Float,
        values: List<Float>,
    ) {
        val perCount = (900 / total)
        val customVerticalIndicatorList: MutableList<CustomVerticalIndicator> = mutableListOf()
        val colorsList = mutableListOf(
            resources.getColor(R.color.purple_500),
            resources.getColor(R.color.teal_200),
            resources.getColor(R.color.teal_700),
            resources.getColor(R.color.black)
        )
        for (i in 0..values.size - 2) {
            if (i == 0) {
                customVerticalIndicatorList.add(
                    CustomVerticalIndicator(
                        50f, perCount * values.first()
                    )
                )
            } else {
                customVerticalIndicatorList.add(
                    CustomVerticalIndicator(
                        getStopPosition(i, values, perCount),
                        getStopPosition(i + 1, values, perCount)
                    )
                )
            }

        }

        binding.textviewFirst.setPercentage(customVerticalIndicatorList,colorsList)
    }

    private fun getStopPosition(endIndex: Int, values: List<Float>, perCount: Float): Float {
        var stopPosition = 0f
        for (i in 0 until endIndex) {
            stopPosition += perCount * values[i]
        }
        return stopPosition
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}