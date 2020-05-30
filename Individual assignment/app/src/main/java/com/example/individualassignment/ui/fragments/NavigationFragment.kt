package com.example.individualassignment.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.individualassignment.R
import com.example.individualassignment.ui.RetrievedPrayersActivity
import kotlinx.android.synthetic.main.fragment_navigation.*

/**
 * A simple [Fragment] subclass.
 */
class NavigationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false)
        initNavigationButtons()
    }


    fun  initNavigationButtons()    {

        var tempButton: Button = btnListRetrievePrayers
        tempButton.setOnClickListener {
            val intent = Intent(activity, RetrievedPrayersActivity::class.java)
            startActivity(intent)
        }

    }

}
