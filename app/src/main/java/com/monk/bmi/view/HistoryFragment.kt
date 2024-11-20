package com.monk.bmi.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.monk.bmi.R
import com.monk.bmi.adapter.HistoryAdapter
import com.monk.bmi.adapter.OnHistoryItemClickListener
import com.monk.bmi.databinding.FragmentHistoryBinding
import com.monk.bmi.dialog.DialogDeleteItem
import com.monk.bmi.model.History
import com.monk.bmi.model.HistoryDatabaseHelper
import com.monk.bmi.utils.AppConstants
import com.monk.bmi.viewmodel.BMIViewModelFactory
import com.monk.bmi.viewmodel.HistoryViewModel

class HistoryFragment : Fragment(), OnHistoryItemClickListener {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var historyDatabaseHelper: HistoryDatabaseHelper
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var viewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        setupViewModel()
        setupView()
        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            BMIViewModelFactory(
                requireContext().applicationContext.getSharedPreferences(
                    AppConstants.KEY_BMI_APP_PREFS,
                    Context.MODE_PRIVATE
                )
            )
        )[HistoryViewModel::class.java]
    }

    private fun setupView() {
        val sharedPreferences = requireContext().applicationContext.getSharedPreferences(
            AppConstants.KEY_BMI_APP_PREFS,
            Context.MODE_PRIVATE
        )

        historyDatabaseHelper = HistoryDatabaseHelper(requireContext())
        historyAdapter = HistoryAdapter(
            historyDatabaseHelper.getAllHistory(),
            this@HistoryFragment,
            sharedPreferences,
            requireContext()
        )

        binding.rcHistoryBmi.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = historyAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        refreshDataItemHistory()
    }

    override fun onClick(history: History) {
        val dialog = DialogDeleteItem()
        dialog.onCreateDialog(requireContext(), history, viewModel.getGender())
        dialog.setDialogResult(object : DialogDeleteItem.DialogResult {
            override fun finish() {
                deleteItemHistoryBy(history.id)
                refreshDataItemHistory()
            }
        })
    }

    fun deleteItemHistoryBy(id: Int) {
        historyDatabaseHelper.deleteHistory(id)
        Toast.makeText(requireContext(), getString(R.string.History_A_03), Toast.LENGTH_SHORT)
            .show()
    }

    fun refreshDataItemHistory() {
        historyAdapter.refreshDataItemHistory(historyDatabaseHelper.getAllHistory())
        if (historyDatabaseHelper.getAllHistory().isEmpty()) {
            binding.tvNoData.visibility = View.VISIBLE
        } else {
            binding.tvNoData.visibility = View.GONE
        }

        historyDatabaseHelper.getAllHistory().forEach {
            println("AAAAAAA: ${it.note}- ${it.bmi}")
        }


    }
}