package com.example.irakliplants1.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.irakliplants1.App
import com.example.irakliplants1.R
import com.example.irakliplants1.databinding.FragmentPlantDetailBinding

class PlantDetailFragment : Fragment() {

    private var _binding: FragmentPlantDetailBinding? = null
    private val binding get() = _binding!!
    private val args: PlantDetailFragmentArgs by navArgs()
    private lateinit var viewModel: PlantViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlantDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val factory = PlantViewModelFactory(App.repository)
        viewModel = ViewModelProvider(this, factory)[PlantViewModel::class.java]

        viewModel.getPlantById(args.plantId).observe(viewLifecycleOwner) { plant ->
            plant?.let {
                binding.tvPlantName.text = it.name
                binding.tvLatinName.text = it.latinName

                binding.tvNaturalHabitat.text = getStyledText("წარმომავლობა:", it.naturalHabitat)
                binding.tvBloomingSeason.text = getStyledText("ყვავილობის სეზონი:", it.bloomingSeason ?: "უცნობია")
                binding.tvSoil.text = getStyledText("ნიადაგი:", it.soil ?: "უცნობია")
                binding.tvCareInstructions.text = getStyledText("მოვლა:", it.careInstructions)
                binding.tvInterestingFact.text = getStyledText("საინტერესო ფაქტი:", it.interestingFact ?: "არ იძებნება")

                binding.tvWikipedia.apply {
                    text = plant.wikipediaUrl
                    setTextColor(resources.getColor(R.color.teal_700, null))
                    paint.isUnderlineText = true
                    setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(plant.wikipediaUrl))
                        startActivity(intent)
                    }
                }

                Glide.with(this)
                    .load(it.photoUri)
                    .placeholder(R.drawable.placeholder_image)
                    .error(R.drawable.error_image)
                    .centerCrop()
                    .into(binding.ivPlantPhoto)
            }
        }
    }


    private fun getStyledText(label: String, value: String): SpannableStringBuilder {
        val color = Color.parseColor("#388E3C")
        val spannable = SpannableStringBuilder("$label $value")
        spannable.setSpan(
            ForegroundColorSpan(color),
            0,
            label.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannable.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            label.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannable
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
