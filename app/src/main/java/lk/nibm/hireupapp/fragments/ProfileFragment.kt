package lk.nibm.hireupapp.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import lk.nibm.hireupapp.R
import lk.nibm.hireupapp.activities.PersonalInformation
import lk.nibm.hireupapp.activities.SignIn
import lk.nibm.hireupapp.activities.ViewAddress
import lk.nibm.hireupapp.common.AppPreferences
import lk.nibm.hireupapp.common.UserDataManager

class ProfileFragment : Fragment() {
    private lateinit var view : View
    private lateinit var personalInfoTab : LinearLayout
    private lateinit var addressTab : LinearLayout
    private lateinit var logOutTab : LinearLayout
    private lateinit var email : TextView
    private lateinit var name : TextView
    private lateinit var proPic : ImageView
    private lateinit var auth: FirebaseAuth
    private lateinit var appPreferences: AppPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        appPreferences = AppPreferences(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        view = inflater.inflate(R.layout.fragment_profile, container, false)
        initializeComponents()
        clickListeners()
        loadProfileData()
        return view
    }
    override fun onResume() {
        super.onResume()
        loadProfileData()
    }

    private fun loadProfileData() {
        val profileData = UserDataManager.getUser()
        name.text = profileData?.displayName
        email.text = profileData?.email
        val imageUrl = profileData?.photoUrl
        Glide.with(this)
            .load(imageUrl)
            .into(proPic)
    }

    private fun clickListeners() {
        personalInfoTab.setOnClickListener {
            val intent = Intent(requireContext(), PersonalInformation::class.java)
            startActivity(intent)
        }
        addressTab.setOnClickListener {
            val intent = Intent(requireContext(), ViewAddress::class.java)
            startActivity(intent)
        }

        logOutTab.setOnClickListener {
            logout()
        }
    }

    private fun initializeComponents() {
        personalInfoTab = view.findViewById(R.id.personalInfoTab)
        addressTab = view.findViewById(R.id.addressTab)
        logOutTab = view.findViewById(R.id.logOutTab)
        name = view.findViewById(R.id.logged_user_name)
        email = view.findViewById(R.id.logged_user_email)
        proPic = view.findViewById(R.id.logged_user_profile_pic)
    }

    private fun logout() {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Logout") { _, _ ->
                auth.signOut()
                appPreferences.clearUserData()
                val intent = Intent(activity, SignIn::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                activity?.finish()
            }
            .setNegativeButton("Cancel", null)
            .create()

        alertDialog.show()
    }

}
