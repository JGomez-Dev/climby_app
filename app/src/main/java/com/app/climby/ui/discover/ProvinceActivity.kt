package com.app.climby.ui.discover

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.climby.R
import com.app.climby.data.model.province.ProvinceModel
import com.app.climby.databinding.ActivityProvinceBinding
import com.app.climby.ui.discover.adapter.ProvinceAdapter
import com.app.climby.ui.discover.viewmodel.ProvinceViewModel
import com.app.climby.view.router.MainRouter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProvinceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProvinceBinding
    private lateinit var provinceViewModel: ProvinceViewModel

    private lateinit var provinceAdapter: ProvinceAdapter
    private var province: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provinceViewModel = ViewModelProvider(this)[ProvinceViewModel::class.java]
        binding = ActivityProvinceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        init()

        binding.IVBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.anim.slide_in_down);
    }

    private fun init() {
        provinceViewModel.getProvince()
        provinceViewModel.provincesModel.observe(this, Observer { provinceList ->
            binding.RVProvince.layoutManager = LinearLayoutManager(this)
            provinceAdapter = ProvinceAdapter(provinceList, province, this)
            binding.RVProvince.adapter = provinceAdapter
            var position = 0

            provinceList.forEach { p ->
                if (p.name == province) {
                    binding.RVProvince.scrollToPosition(position - 5)
                }
                position++
            }
            provinceAdapter.setOnItemClickListener(object : ProvinceAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    goToMainActivity(provinceList[position])
                }
            })
        })
    }

    private fun goToMainActivity(province: ProvinceModel) {
        MainRouter().launch(this, province, null, isEdit = false)
        finish()
    }

    private fun getData() {
        val bundle = intent.extras
        province = bundle?.getString("province")
    }
}