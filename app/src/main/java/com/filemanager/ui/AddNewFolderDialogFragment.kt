package com.filemanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.filemanager.utils.Constants
import com.filemanager.utils.Constants.PARENT_ID
import com.filemanager.FileManagerApplication
import com.filemanager.R
import com.filemanager.repository.db.item.Item
import com.filemanager.ui.all_items.AllItemsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class AddNewFolderDialogFragment : DialogFragment() {

    private val viewModel: AllItemsViewModel by viewModels {
        AllItemsViewModel.ItemsViewModelFactory(
            (requireActivity().application as FileManagerApplication).repository,
            requireContext()
        )
    }

    companion object {
        fun newInstance(
            parentID: String
        ): AddNewFolderDialogFragment? {
            val fragment = AddNewFolderDialogFragment()
            val bundle = Bundle()
            bundle.putString(PARENT_ID, parentID)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.dialog_create_new, container, false)
        val parentID: String? = arguments?.getString(PARENT_ID)
        if (parentID != null) {
            initSubViews(rootView, parentID)
        }
        return rootView
    }

    private fun initSubViews(rootView: View, parentID: String) {
        val editText = rootView.findViewById<View>(R.id.item_name) as TextView
        val btnOk = rootView.findViewById<View>(R.id.btn_ok) as Button
        val btnCancel = rootView.findViewById<View>(R.id.btn_cancel) as Button

        btnCancel.setOnClickListener {
            dismiss()
        }

        btnOk.setOnClickListener {
            val folderName = editText.text.toString()
            val id = UUID.randomUUID().toString()
            val item = Item(id, Constants.TYPE_FOLDER, folderName, "", parentID)
            CoroutineScope(Dispatchers.Main).launch(Dispatchers.IO) {
                viewModel.insertItem(item)
            }
            dismiss()
        }
    }
}

