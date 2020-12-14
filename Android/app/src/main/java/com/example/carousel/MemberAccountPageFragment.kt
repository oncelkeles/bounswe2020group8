package com.example.carousel

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.fragment.app.Fragment
import com.example.carousel.R.drawable
import com.example.carousel.application.ApplicationContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.android.synthetic.main.fragment_acount_page.*
import kotlinx.android.synthetic.main.fragment_acount_page.view.*
import java.io.*


class MemberAccountPageFragment : Fragment() {
    private var prefs : SharedPreferences? = null
    private lateinit var mAdapter: CustomAdapter
    var login = 0
    private var mGoogleSignInClient: GoogleSignInClient? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        prefs = activity?.getSharedPreferences("userInfo", Context.MODE_PRIVATE)
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleSignInClient = activity?.let { GoogleSignIn.getClient(it, gso) }
        return inflater.inflate(R.layout.fragment_acount_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        login = if (ApplicationContext.instance.isUserAuthenticated()) {
            1
        } else {
            0
        }


        mAdapter = CustomAdapter(context as Context)
        mAdapter.addSectionHeaderItem("Account")
        mAdapter.addItem("User Information", drawable.ic_person)
        mAdapter.addItem("Change Password", drawable.ic_key)
        mAdapter.addItem("Settings", drawable.ic_settings)
        mAdapter.addItem("Logout", drawable.ic_exit)
        mAdapter.addSectionHeaderItem("Carousel")
        mAdapter.addItem("About", drawable.ic_info)
        mAdapter.addItem("Legals", drawable.ic_file)
        mAdapter.addItem("Contact", drawable.ic_contact)
        listView.adapter = (mAdapter)
        if (login == 0) {
            view.guest.visibility = View.VISIBLE
            val intent = Intent(activity, LoginActivity::class.java)
            startActivityForResult(intent, 11)
        } else {
            view.login_user.visibility = View.VISIBLE
        }
        view.login_button.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivityForResult(intent, 11)
        }
        listView.onItemClickListener = OnItemClickListener { adapterView, view, pos, l ->
            if (pos == 4) {
                mGoogleSignInClient?.signOut()
                view?.guest?.visibility = View.VISIBLE
                view?.login_user?.visibility = View.INVISIBLE
                ApplicationContext.instance.terminateAuthentication()
                prefs!!.edit().clear().apply()
                (activity as DashboardActivity).refresh()
            }
            //TicketList Object
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (ApplicationContext.instance.isUserAuthenticated()) {
            login = 1
            view?.guest?.visibility = View.INVISIBLE
            view?.login_user?.visibility = View.VISIBLE
        }
    }

}