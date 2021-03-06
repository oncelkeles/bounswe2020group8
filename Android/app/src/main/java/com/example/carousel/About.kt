package com.example.carousel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_add_address.view.*

class About : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.back_button.setOnClickListener {
            val fragment = MemberAccountPageFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_about, fragment)
                ?.commit()
        }
    }

}
