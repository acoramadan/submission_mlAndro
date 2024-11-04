package com.dicoding.asclepius.ui.view.fragments

import com.dicoding.asclepius.ui.viewmodel.InformationNewsViewModel
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.asclepius.databinding.FragmentInformationBinding
import com.dicoding.asclepius.ui.adapter.InformationListAdapter
import com.dicoding.asclepius.ui.view.DetailInformationActivity

class InformationFragment : Fragment() {

    private var _binding: FragmentInformationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: InformationNewsViewModel by viewModels()
    private lateinit var informationAdapter: InformationListAdapter
    private var position = 0
    private val apiKey = "aa33e3377a004b8e972f4b2b91bd7c6a"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformationBinding.inflate(inflater, container, false)

        arguments?.let {
            position = it.getInt("EXTRA_ID", 0)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        informationAdapter = InformationListAdapter(requireContext()) { information ->
            val intent = Intent(requireContext(), DetailInformationActivity::class.java)
            intent.putExtra("EXTRA_TITLE", information)
            startActivity(intent)
        }

        binding.rv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = informationAdapter
        }
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
        viewModel.newsResponse.observe(viewLifecycleOwner) { informationList ->
            informationList?.let {
                informationAdapter.submitList(it.information)
            }
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
        viewModel.fetchCancerHealthNews(apiKey)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
