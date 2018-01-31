package magnet.house.godbeom.com.annotationbus.bus.interfaces;


import magnet.house.godbeom.com.annotationbus.bus.impl.BrodCastPacket;

/**
 * Created by BeomChul on 2017-04-30.
 *
 * BrodCastPacket 타입의 단일 파라미터 함수형태를 강제 하기 위해 존재합니다.(필수아님)
 */

public interface BrodCastListener {
    /*To listen to the broadcast, you need to add @BrodCastEvent Annotation on Method*/
   //  if java8 ? Attach @Default
    void onBrodCastEvent(BrodCastPacket bcp);

}
