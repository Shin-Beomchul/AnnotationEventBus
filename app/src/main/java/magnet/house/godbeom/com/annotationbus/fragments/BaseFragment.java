package magnet.house.godbeom.com.annotationbus.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import magnet.house.godbeom.com.annotationbus.bus.impl.MagnetEvent;


/**
 * Created by Administrator on 2018-01-31.
 */

public class BaseFragment extends Fragment {

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		MagnetEvent.getInstance().register(this);
		super.onCreate(savedInstanceState);

	}

	@Override
	public void onDestroy() {
		MagnetEvent.getInstance().unregister(this);
		super.onDestroy();

	}
}
