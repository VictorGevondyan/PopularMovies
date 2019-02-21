package am.victor.popularmovies.adapters

import android.widget.BaseAdapter
import com.arellomobile.mvp.MvpDelegate


abstract class MvpBaseAdapter(
    private val parentDelegate: MvpDelegate<*>,
    private val childId: String
) : BaseAdapter() {

    private var mvpDelegate: MvpDelegate<out MvpBaseAdapter>? = null
        get() {
            if (field == null) {

                mvpDelegate = MvpDelegate(this)
                field!!.setParentDelegate(parentDelegate, childId)

            }
            return field
        }

    init {
        mvpDelegate!!.onCreate()
    }

}