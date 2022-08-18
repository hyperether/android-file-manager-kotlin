package com.filemanager

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.filemanager.utils.Constants.PARENT_ID
import com.filemanager.utils.Constants.PARENT_ID_MAIN
import com.filemanager.ui.all_items.AllItemsFragment


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!checkPermission()) {
            requestPermission()
        }

        val allItemsFragment = AllItemsFragment()
        val bundle = Bundle()
        bundle.putString(PARENT_ID, PARENT_ID_MAIN)
        allItemsFragment.arguments = bundle

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .addToBackStack("AllItems")
                .replace(R.id.frame, allItemsFragment, "AllItems")
                .commit()
        }
        window.navigationBarColor = this.resources.getColor(R.color.dark_grey)
    }

    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            this@MainActivity,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse("package:$packageName")
                startActivityForResult(intent, 111)
            } catch (e: Exception) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
                startActivityForResult(intent, 111)
            }
        } else ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            111
        )
    }

    fun replaceFragment(fragment: Fragment, tag: String?) {
        //Get current fragment placed in container
        val currentFragment: Fragment? = supportFragmentManager.findFragmentById(R.id.frame)

        //Prevent adding same fragment on top
        if (currentFragment!!::class === fragment::class) {
            return
        }

        //If fragment is already on stack, we can pop back stack to prevent stack infinite growth
        if (supportFragmentManager.findFragmentByTag(tag) != null) {
            supportFragmentManager.popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
        val bundle = Bundle()
        bundle.putString(PARENT_ID, tag)
        fragment.arguments = bundle

        //Otherwise, just replace fragment
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(tag)
            .replace(R.id.frame, fragment, tag)
            .commit()
    }

    override fun onBackPressed() {
        val fragmentsInStack = supportFragmentManager.backStackEntryCount
        if (fragmentsInStack > 1) { // If we have more than one fragment, pop back stack
            supportFragmentManager.popBackStack()
        } else if (fragmentsInStack == 1) { // Finish activity, if only one fragment left, to prevent leaving empty screen
            finish()
        } else {
            super.onBackPressed()
        }
    }
}