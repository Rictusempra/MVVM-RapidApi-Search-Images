package com.foodsoul.test.ui.image_search

//noinspection SuspiciousImport
import android.R
import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView.OnEditorActionListener
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.foodsoul.test.App
import com.foodsoul.test.data.image_search.model.Value
import com.foodsoul.test.databinding.FragmentImageSearchBinding
import com.foodsoul.test.navigation.BackButtonListener
import com.foodsoul.test.utils.hideKeyboard
import com.github.terrakok.cicerone.Router
import javax.inject.Inject


class ImagesSearchFragment : Fragment(),
    BackButtonListener,
    ImagesListAdapter.OnItemClickListener {

    companion object {
        fun getNewInstance() = ImagesSearchFragment()
    }

    private lateinit var binding: FragmentImageSearchBinding

    lateinit var imagesListAdapter: ImagesListAdapter

    private val suggestionsAdapter by lazy {
        ArrayAdapter<String>(
            requireContext(),
            R.layout.simple_dropdown_item_1line
        )
    }

    @Inject
    lateinit var router: Router

    @Inject
    lateinit var viewModel: ImagesSearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        App.INSTANCE.appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentImageSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSearchEditText()
        initImagesListAdapter()
        initSuggestionsAdapter()
        initListeners()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initListeners() {
        viewModel.getImagesList().observe(viewLifecycleOwner) { list ->
            list.value.let {
                imagesListAdapter.setNewList(it)
                binding.emptyListText.isVisible = it.isEmpty()
            }
        }

        viewModel.getSuggestions().observe(viewLifecycleOwner) { list ->
            updateSuggestionsList(list)
        }

        viewModel.getIsLoading().observe(viewLifecycleOwner) { isVisible ->
            isVisible.let {
                binding.loadingIndicator.isVisible = it
            }
        }

        viewModel.getError().observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initSearchEditText() {
        binding.searchField.setOnEditorActionListener(OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchImages(binding.searchField.text.toString())
                viewModel.saveSuggestion(binding.searchField.text.toString())
                hideKeyboard(requireActivity(), binding.searchField)
                true
            } else false
        })

        binding.searchField.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(text: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                viewModel.updateSuggestionsList(text.toString())
            }
            override fun beforeTextChanged(text: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun afterTextChanged(arg0: Editable) {}
        })

        binding.searchField.onItemClickListener = AdapterView.OnItemClickListener { _, _, pos, _ ->
            viewModel.searchImages(suggestionsAdapter.getItem(pos).toString())
            hideKeyboard(requireActivity(), binding.searchField)
        }
    }

    private fun initImagesListAdapter() {
        binding.imagesRecyclerView.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
        binding.imagesRecyclerView.setHasFixedSize(true)
        imagesListAdapter = ImagesListAdapter(this)
        binding.imagesRecyclerView.adapter = imagesListAdapter
    }

    private fun initSuggestionsAdapter() {
        binding.searchField.setAdapter(suggestionsAdapter)
        suggestionsAdapter.setNotifyOnChange(true)
    }

    private fun updateSuggestionsList(suggestions: List<String>) {
        suggestionsAdapter.clear()
        suggestionsAdapter.addAll(suggestions)
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    override fun onItemClick(item: Value) {
        // TODO: отображение полной версии изображения
    }

}