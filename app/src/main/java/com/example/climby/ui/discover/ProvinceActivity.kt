package com.example.climby.ui.discover

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climby.R
import com.example.climby.databinding.ActivityProvinceBinding
import com.example.climby.ui.discover.adapter.ProvinceAdapter
import com.example.climby.ui.discover.viewmodel.ProvinceViewModel
import com.example.climby.utils.IOnBackPressed
import com.example.climby.view.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProvinceActivity : AppCompatActivity(), IOnBackPressed {

    private lateinit var binding: ActivityProvinceBinding
    private lateinit var provinceViewModel: ProvinceViewModel

    private lateinit var provinceAdapter: ProvinceAdapter
    private var province: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provinceViewModel = ViewModelProvider(this).get(ProvinceViewModel::class.java)
        binding = ActivityProvinceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        init()

        binding.IVBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun init() {
        provinceViewModel.getProvince()
        provinceViewModel.provincesModel.observe(this, Observer {it->
            binding.RVProvince.layoutManager = LinearLayoutManager(this)
            provinceAdapter = ProvinceAdapter(it, province, this)
            binding.RVProvince.adapter = provinceAdapter
            var position = 0
            it.forEach {
                if (it.name == province) {
                    binding.RVProvince.scrollToPosition(position - 5)
                }
                position++
            }
            provinceAdapter.setOnItemClickListener(object : ProvinceAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(applicationContext.applicationContext, MainActivity::class.java).apply {
                        putExtra("province", it[position].name)
                    }

                    startActivity(intent)
                    overridePendingTransition(0, R.anim.slide_in_down);
                }
            })
        })
    }

    private fun getData() {
        val bundle = intent.extras
        province = bundle?.getString("province")
    }
}