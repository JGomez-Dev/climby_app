package com.app.climby.ui.discover.router

import com.app.climby.ui.discover.DiscoverFragment
import com.app.climby.util.base.BaseFragmentRouter

class DiscoverRouter : BaseFragmentRouter {

    private var instance: DiscoverFragment? = null

    override fun fragment(): DiscoverFragment {
        if(instance == null) {
            instance = DiscoverFragment.fragment()
        }
        return instance!!
    }

}