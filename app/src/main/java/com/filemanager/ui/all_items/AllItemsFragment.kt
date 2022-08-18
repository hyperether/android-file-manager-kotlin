package com.filemanager.ui.all_items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.filemanager.utils.Constants.PARENT_ID
import com.filemanager.FileManagerApplication
import com.filemanager.MainActivity
import com.filemanager.R
import com.filemanager.ui.AddNewFolderDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton


class AllItemsFragment : Fragment(R.layout.fragment_all_items) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AllItemsAdapter
    private lateinit var addBtn: FloatingActionButton
    private lateinit var tvNoItems: TextView

    private val viewModel: AllItemsViewModel by viewModels {
        AllItemsViewModel.ItemsViewModelFactory(
            (requireActivity().application as FileManagerApplication).repository,
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_items, container, false)
        tvNoItems = view.findViewById(R.id.tv_no_items)

        val string = arguments?.getString(PARENT_ID).toString()

        recyclerView = view.findViewById(R.id.rvItems)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewModel.allItems(string).observe(viewLifecycleOwner) { items ->
            items.let {
                adapter = AllItemsAdapter(items, activity as MainActivity)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                if (items == null || items.isEmpty()) {
                    tvNoItems.visibility = View.VISIBLE
                } else {
                    tvNoItems.visibility = View.GONE
                }
            }
        }

        addBtn = view.findViewById(R.id.items_fab)
        addBtn.setOnClickListener {
            addFolder(string)
        }
        return view
    }

    private fun addFolder(parentID: String) {
        val dialogFragment = AddNewFolderDialogFragment.newInstance(parentID)
        dialogFragment?.show(requireActivity().supportFragmentManager, parentID)
    }
}
