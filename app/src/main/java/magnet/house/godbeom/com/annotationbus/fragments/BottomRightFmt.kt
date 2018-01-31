package magnet.house.godbeom.com.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fmt_bottom_right.*
import magnet.house.godbeom.com.annotationbus.MainActivity
import magnet.house.godbeom.com.annotationbus.R
import magnet.house.godbeom.com.annotationbus.bus.annotations.BrodCastEvent
import magnet.house.godbeom.com.annotationbus.bus.annotations.TypeMagnetEvent
import magnet.house.godbeom.com.annotationbus.bus.impl.BrodCastPacket
import magnet.house.godbeom.com.annotationbus.fragments.BaseFragment
import magnet.house.godbeom.com.annotationbus.mto.yourObject

class BottomRightFmt : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fmt_bottom_right, container, false)
    }


    @BrodCastEvent
    fun listenEvent(brodCastPacket: BrodCastPacket) {
        when (brodCastPacket.eventType) {
            MainActivity.EVENT_HI_FRAGMENT -> godTv.text="hi.... I'am [BOTTOM Right] Fragment ^^"
            MainActivity.EVENT_CLEAN_FRAGMENT -> godTv.text="Listen Clean"
        }
    }
    @TypeMagnetEvent
    fun typeMagent( channelright: yourObject){
        godTv.text=channelright.data;
    }






    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): BottomRightFmt {
            val fragment = BottomRightFmt()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}