package com.monk.bmi.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.monk.bmi.databinding.FragmentPolicyBinding

class PolicyFragment : Fragment() {
    private lateinit var binding: FragmentPolicyBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPolicyBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        val url = "https://www.google.com"
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(url)
    }
}