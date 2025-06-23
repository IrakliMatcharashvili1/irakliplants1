package com.example.irakliplants1.ui

import com.example.irakliplants1.MainActivity
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.irakliplants1.App
import com.example.irakliplants1.R
import com.example.irakliplants1.data.Plant
import com.example.irakliplants1.databinding.FragmentAddPlantBinding
import java.util.*

class AddPlantFragment : Fragment() {

    private var _binding: FragmentAddPlantBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PlantViewModel

    private var selectedPhotoUri: String? = null

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = result.data?.data
            imageUri?.let {
                selectedPhotoUri = it.toString()
                binding.imagePreview.setImageURI(it)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAddPlantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val factory = PlantViewModelFactory(App.repository)
        viewModel = ViewModelProvider(this, factory)[PlantViewModel::class.java]

        binding.selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            galleryLauncher.launch(intent)
        }

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString().trim()
            val latinName = binding.latinNameEditText.text.toString().trim()
            val habitat = binding.habitatEditText.text.toString().trim()
            val blooming = binding.bloomingEditText.text.toString().trim()
            val soil = binding.soilEditText.text.toString().trim()
            val care = binding.careEditText.text.toString().trim()
            val fact = binding.factEditText.text.toString().trim()
            val wiki = binding.wikipediaEditText.text.toString().trim()

            if (name.isEmpty() || latinName.isEmpty() || habitat.isEmpty() || care.isEmpty()) {
                Toast.makeText(requireContext(), "გთხოვთ შეავსოთ სავალდებულო ველები", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val plant = Plant(
                name = name,
                latinName = latinName,
                naturalHabitat = habitat,
                bloomingSeason = blooming.ifBlank { null },
                soil = soil.ifBlank { null },
                careInstructions = care,
                interestingFact = fact.ifBlank { null },
                wikipediaUrl = wiki.ifBlank { null },
                photoUri = selectedPhotoUri
            )

            viewModel.insertPlant(plant)
            Toast.makeText(requireContext(), "მცენარე წარმატებით დაემატა", Toast.LENGTH_SHORT).show()
            (requireActivity() as MainActivity).setSelectedBottomNavItem(R.id.plantListFragment)
            findNavController().navigate(R.id.plantListFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
