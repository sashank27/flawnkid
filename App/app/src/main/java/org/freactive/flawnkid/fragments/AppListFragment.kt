package org.freactive.flawnkid.fragments

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.apps_list_fragment.*
import org.freactive.flawnkid.AppDetail
import org.freactive.flawnkid.R
import org.freactive.flawnkid.adapters.AppListAdapter
import java.util.*
import kotlin.concurrent.schedule

/**
 * Created by akshat on 17/3/18.
 */

class AppListFragment: Fragment() {

    private lateinit var manager: PackageManager
    private lateinit var apps: ArrayList<AppDetail>
    private val PREFS_KEY = "org.freactive.flawnkid.PREFS"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(R.layout.apps_list_fragment,container,false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadApps()
        loadListView()
        //addClickListener()
    }

    private fun loadApps() {
        manager = activity.packageManager
        apps = ArrayList()

        val i = Intent(Intent.ACTION_MAIN, null)
        i.addCategory(Intent.CATEGORY_LAUNCHER)

        val availableActivities = manager.queryIntentActivities(i, 0)
        for (ri in availableActivities) {
            val app = AppDetail()
            app.label = ri.loadLabel(manager)
            app.name = ri.activityInfo.packageName
            app.icon = ri.activityInfo.loadIcon(manager)
            apps.add(app)
        }
    }

    private fun loadListView() {

        /*val adapter = object : ArrayAdapter<AppDetail>(activity,
                R.layout.list_item,
                apps) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
                var convertView = convertView
                if (convertView == null) {
                    convertView = activity.layoutInflater.inflate(R.layout.list_item, null)
                }

                val appIcon = convertView!!.findViewById(R.id.item_app_icon) as ImageView
                appIcon.setImageDrawable(apps[position].icon)

                val appLabel = convertView.findViewById(R.id.item_app_label) as TextView
                appLabel.text = apps[position].label

                //val appName = convertView.findViewById(R.id.item_app_name) as TextView
                //appName.text = apps[position].name

                return convertView
            }
        }*/
        appsList.layoutManager = GridLayoutManager(context,4,GridLayoutManager.VERTICAL,false)
        appsList.adapter = AppListAdapter(context, apps)
    }

    /*private fun addClickListener() {
        appsList.onItemClickListener = AdapterView.OnItemClickListener { _, _, pos, _ ->
            val i = manager.getLaunchIntentForPackage(apps[pos].name.toString())

            val sharedPref = activity.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
            val count = sharedPref.getInt(apps[pos].label.toString(),0)

            if (count == 0) {
                with (sharedPref.edit()) {
                    putInt(apps[pos].label.toString(), 1)
                    commit()
                }
                activity.startActivityForResult(i,100)

                Timer("",false).schedule(5000) {
                    val i = Intent(Intent.ACTION_MAIN)
                    i.addCategory(Intent.CATEGORY_HOME)
                    startActivity(i)
                }
            } else {
                Toast.makeText(context, "You're out of Pojos. Let's play some games to earn more!", Toast.LENGTH_SHORT).show()
            }


        }
    }*/
}