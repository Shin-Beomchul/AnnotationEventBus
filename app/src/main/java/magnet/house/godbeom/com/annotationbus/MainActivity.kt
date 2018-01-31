package magnet.house.godbeom.com.annotationbus

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import magnet.house.godbeom.com.annotationbus.bus.impl.BrodCastPacket
import magnet.house.godbeom.com.annotationbus.bus.impl.MagnetEvent
import magnet.house.godbeom.com.annotationbus.fragments.TopRightFmt
import magnet.house.godbeom.com.annotationbus.mto.yourObject
import magnet.house.godbeom.com.myapplication.fragments.BottomLeftFmt
import magnet.house.godbeom.com.myapplication.fragments.BottomRightFmt
import magnet.house.godbeom.com.myapplication.fragments.TopLeftFmt


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        /*Listen All @BrodCastEvent*/
        btnBrodCast.setOnClickListener { MagnetEvent.getInstance().postBrodCast(BrodCastPacket().setEvent(EVENT_HI_FRAGMENT))}

        /*Listen All @BrodCastEvent(BrodCastPacket)*/
        btnclean.setOnClickListener { _ ->MagnetEvent.getInstance().postBrodCast(BrodCastPacket().setEvent(EVENT_CLEAN_FRAGMENT))}


        /*Listen @TypeMagnetEvent of match T*/
        btnObjectMatch.setOnClickListener {
            val typeObject = yourObject()
            typeObject.data = "@TypeMagnetEvent가 붙은 메소드중 RightFmtMTO와 일치하는 메소드"
            MagnetEvent.getInstance().postTypeChannel(typeObject)
        }
    }


    private fun init() {

        val topRight = TopRightFmt.newInstance("val1", "val2")
        val topLeft = TopLeftFmt.newInstance("val1", "val2")
        val bottomLeft = BottomLeftFmt.newInstance("val1", "val2")
        val bottomRight = BottomRightFmt.newInstance("val1", "val2")


        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(R.id.fragmentHome1, topLeft, topLeft.tag)
        ft.replace(R.id.fragmentHome2, topRight, topRight.tag)
        ft.replace(R.id.fragmentHome3, bottomLeft, bottomLeft.tag)
        ft.replace(R.id.fragmentHome4, bottomRight, bottomRight.tag)
        ft.commitAllowingStateLoss()
    }

    companion object {

        const  val EVENT_HI_FRAGMENT = 1
        const  val EVENT_CLEAN_FRAGMENT = 2

    }

    fun tl(text:String):Unit{
        Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
    }
}