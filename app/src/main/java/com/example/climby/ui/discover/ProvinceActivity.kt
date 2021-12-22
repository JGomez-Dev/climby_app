package com.example.climby.ui.discover

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climby.R
import com.example.climby.databinding.ActivityProvinceBinding
import com.example.climby.ui.discover.adapter.DiscoverAdapter
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
        provinceViewModel.provincesModel.observe(this, Observer {
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
/*            binding.RVProvince.scrollToPosition(15)*/
            provinceAdapter.setOnItemClickListener(object : ProvinceAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    /*Toast.makeText(applicationContext.applicationContext, it[position].name, Toast.LENGTH_SHORT).show()*/
                    val intent = Intent(applicationContext.applicationContext, MainActivity::class.java).apply {
                        putExtra("province", it[position].name)
                    }
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_out_down, R.anim.slide_in_down);
                }
            })
        })
    }

    private fun getData() {
        val bundle = intent.extras
        province = bundle?.getString("province")
    }
}