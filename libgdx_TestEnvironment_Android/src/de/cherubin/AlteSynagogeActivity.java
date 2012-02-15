package de.cherubin;

import de.cherubin.cv.CV_Abschnitt;
import de.cherubin.cv.CV_Bild;
import de.cherubin.listener.MyHandler;
import android.os.Bundle;
import android.widget.LinearLayout;

public class AlteSynagogeActivity extends AbstractPageActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	void initData(LinearLayout container, MyHandler mHandler) {
		CV_Abschnitt mA1 = new CV_Abschnitt(this, getString(R.string.alte_h1), getString(R.string.alte_t1), R.drawable.ic_play);
		CV_Abschnitt mA2 = new CV_Abschnitt(this, getString(R.string.alte_h2), getString(R.string.alte_t2), R.drawable.ic_play);
		CV_Abschnitt mA3 = new CV_Abschnitt(this, getString(R.string.alte_h3), getString(R.string.alte_t3), R.drawable.ic_play);
		CV_Abschnitt mA4 = new CV_Abschnitt(this, getString(R.string.alte_h4), getString(R.string.alte_t4), R.drawable.ic_play);
		CV_Bild b1 = new CV_Bild(this, R.drawable.alte_synagoge, getString(R.string.alte_bu1));
		CV_Bild b2 = new CV_Bild(this, R.drawable.alte_grundriss, getString(R.string.alte_bu2));
		CV_Bild b3 = new CV_Bild(this, R.drawable.alte_querschnitt, getString(R.string.alte_bu3));
		CV_Bild b4 = new CV_Bild(this, R.drawable.alte_innen, getString(R.string.alte_bu4));
		container.addView(mA1);
		container.addView(b1);
		container.addView(mA2);
		container.addView(b2);
		container.addView(mA3);
		container.addView(b3);
		container.addView(mA4);
		container.addView(b4);
		mHandler.addComponent(mA1);
		mHandler.addComponent(mA2);
		mHandler.addComponent(mA3);
		mHandler.addComponent(mA4);
	}
}
