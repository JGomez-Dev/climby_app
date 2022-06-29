package com.app.climby.ui.publish.router

import com.app.climby.ui.publish.PublishFragment
import com.app.climby.util.base.BaseFragmentRouter

class PublishRouter : BaseFragmentRouter {

    private var instance: PublishFragment? = null

    override fun fragment(): PublishFragment {
        if(instance == null) {
            instance = PublishFragment.fragment()
        }
        return instance!!
    }

}