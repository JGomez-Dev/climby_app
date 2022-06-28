package com.app.climby.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.app.climby.R
import com.app.climby.data.model.user.UserModel
import com.app.climby.databinding.FragmentProfileBinding
import com.app.climby.ui.profile.adapter.ViewPagerAdapter
import com.app.climby.ui.profile.viewmodel.ProfileViewModel
import com.app.climby.utils.Commons
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var  adapter: ViewPagerAdapter
    private lateinit var prefs: SharedPreferences
    private var userSession: UserModel = Commons.userSession!!

    private var experience : String? = null
    private var viewPager : Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val view: View = binding.root


        val navBar: BottomNavigationView = activity?.findViewById(R.id.nav_view)!!
        navBar.isVisible = true

        /*navBar.getOrCreateBadge(R.id.navigation_profile).apply {
            isVisible = false
            backgroundColor = (ContextCompat.getColor(requireContext(), R.color.primary))
        }
*/
        init()
        getData()

        adapter = ViewPagerAdapter(parentFragmentManager, lifecycle)
        binding.viewPager2.adapter = adapter

        TabLayoutMediator(binding.TLProfile, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Mis salidas"
                }
                1 -> {
                    tab.text = "Próximas salidas"
                }
            }
        }.attach()

        if(Commons.isInternetAvailable(requireContext().applicationContext)){
            binding.ETEditProfile.setOnClickListener {
                loadActivity()
            }
        }

        binding.viewPager2.currentItem = viewPager
        return view
    }

    @SuppressLint("SetTextI18n")
    private fun init(){
        prefs = activity?.getSharedPreferences(getString(R.string.prefs_file), AppCompatActivity.MODE_PRIVATE)!!
        binding.TVUserName.text = prefs.getString("displayName", "").toString().split(" ")[0]
        Glide.with(this).load(prefs.getString("photoUrl", "...")).error(R.mipmap.user).into(binding.CVImageProfile)
        binding.TVUserExperience.text = userSession.experience
        binding.TVUserOutputs.text = userSession.outings.toString() + if (userSession.outings.toString() == "1") " salida" else " salidas"
        setStart()
    }

    private fun loadActivity(){
        activity?.let {
            val intent = Intent(activity, EditProfileActivity::class.java)
            it.startActivity(intent)
            it.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }

    private fun getData() {
        val bundle = activity?.intent?.extras
        if (bundle != null) {
           /* experience = bundle.getString("exprienceProfile", "")*/
            viewPager = bundle.getInt("viewPager", 0)
            binding.TVUserExperience.text = Commons.userSession?.experience
        }
    }

    private fun setStart() {
        when {
            userSession.score / userSession.ratings  > 2.75 -> {
                binding.IVStartProfile1.setImageResource(R.mipmap.star)
                binding.IVStartProfile2.setImageResource(R.mipmap.star)
                binding.IVStartProfile3.setImageResource(R.mipmap.star)
            }
            userSession.score / userSession.ratings in 2.25..2.75 -> {
                binding.IVStartProfile1.setImageResource(R.mipmap.star)
                binding.IVStartProfile2.setImageResource(R.mipmap.star)
                binding.IVStartProfile3.setImageResource(R.mipmap.medstart)
            }
            userSession.score / userSession.ratings in 1.75..2.25 -> {
                binding.IVStartProfile1.setImageResource(R.mipmap.star)
                binding.IVStartProfile2.setImageResource(R.mipmap.star)
                binding.IVStartProfile3.setImageResource(R.mipmap.withoutstart)
            }
            userSession.score / userSession.ratings in 1.25..1.75 -> {
                binding.IVStartProfile1.setImageResource(R.mipmap.star)
                binding.IVStartProfile2.setImageResource(R.mipmap.medstart)
                binding.IVStartProfile3.setImageResource(R.mipmap.withoutstart)
            }
            userSession.score / userSession.ratings in 0.75..1.25 -> {
                binding.IVStartProfile1.setImageResource(R.mipmap.star)
                binding.IVStartProfile2.setImageResource(R.mipmap.withoutstart)
                binding.IVStartProfile3.setImageResource(R.mipmap.withoutstart)
            }
            userSession.score / userSession.ratings <= 0.75 -> {
                binding.IVStartProfile1.setImageResource(R.mipmap.medstart)
                binding.IVStartProfile2.setImageResource(R.mipmap.withoutstart)
                binding.IVStartProfile3.setImageResource(R.mipmap.withoutstart)
            }
        }
    }
}