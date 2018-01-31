package magnet.house.godbeom.com.annotationbus.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import magnet.house.godbeom.com.annotationbus.MainActivity;
import magnet.house.godbeom.com.annotationbus.R;
import magnet.house.godbeom.com.annotationbus.bus.annotations.BrodCastEvent;
import magnet.house.godbeom.com.annotationbus.bus.annotations.TypeMagnetEvent;
import magnet.house.godbeom.com.annotationbus.bus.impl.BrodCastPacket;
import magnet.house.godbeom.com.annotationbus.mto.yourObject;


public class TopRightFmt extends BaseFragment {

	TextView tvGod;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fmt_top_right, container, false);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		tvGod = (TextView)getActivity().findViewById(R.id.tvtopRight);
	}


	@BrodCastEvent
	public void listenEvent(BrodCastPacket brodCastPacket){
		switch (brodCastPacket.getEventType()){
			case MainActivity.EVENT_HI_FRAGMENT:
				tvGod.setText("hi!! I'am [TOP Right] Fragment ....");
				break;


			case MainActivity.EVENT_CLEAN_FRAGMENT:
				tvGod.setText("Listen Clean");
				break;


		}
	}

	@TypeMagnetEvent
	public void listenTypeEvent(yourObject channelRight){
		tvGod.setText(channelRight.getData());
	}






	final static  String ARG_PARAM1 ="param1";
	final static  String ARG_PARAM2 ="param2";
	public static TopRightFmt newInstance(String param1, String param2) {
		TopRightFmt fragment = new TopRightFmt();
		Bundle args = new Bundle();
		args.putString(ARG_PARAM1, param1);
		args.putString(ARG_PARAM2, param2);
		fragment.setArguments(args);
		return fragment;
	}

}
