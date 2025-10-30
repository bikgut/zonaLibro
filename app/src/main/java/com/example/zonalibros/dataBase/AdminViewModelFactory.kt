package com.example.zonalibros.dataBase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zonalibros.viewModel.AdminViewModel


class AdminViewModelFactory(private val repository: AdminRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return AdminViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}