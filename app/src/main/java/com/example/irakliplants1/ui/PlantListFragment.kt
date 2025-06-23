package com.example.irakliplants1.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.irakliplants1.App
import com.example.irakliplants1.data.Plant
import com.example.irakliplants1.databinding.FragmentPlantListBinding
import com.google.android.material.snackbar.Snackbar

class PlantListFragment : Fragment() {

    private var _binding: FragmentPlantListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PlantViewModel
    private lateinit var adapter: PlantAdapter
    private var recentlyDeletedPlant: Plant? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlantListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val factory = PlantViewModelFactory(App.repository)
        viewModel = ViewModelProvider(this, factory)[PlantViewModel::class.java]

        adapter = PlantAdapter { plant ->
            val action = PlantListFragmentDirections.actionPlantListFragmentToPlantDetailFragment(plant.id)
            findNavController().navigate(action)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.plants.observe(viewLifecycleOwner) { plants ->
            adapter.submitList(plants)
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(rv: androidx.recyclerview.widget.RecyclerView, vh: androidx.recyclerview.widget.RecyclerView.ViewHolder, target: androidx.recyclerview.widget.RecyclerView.ViewHolder) = false

            override fun onSwiped(viewHolder: androidx.recyclerview.widget.RecyclerView.ViewHolder, direction: Int) {
                recentlyDeletedPlant = adapter.currentList[viewHolder.adapterPosition]
                recentlyDeletedPlant?.let { plant ->
                    viewModel.delete(plant)
                    Snackbar.make(binding.root, "${plant.name} წაიშალა", Snackbar.LENGTH_LONG)
                        .setAction("გაუქმება") {
                            viewModel.insert(plant)
                        }
                        .show()
                }
            }
        })

        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
