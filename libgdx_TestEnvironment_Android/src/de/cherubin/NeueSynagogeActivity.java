package de.cherubin;

import de.cherubin.cv.CV_Abschnitt;
import de.cherubin.cv.CV_Bild;
import de.cherubin.listener.MyHandler;
import android.os.Bundle;
import android.widget.LinearLayout;

public class NeueSynagogeActivity extends AbstractPageActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	void initData(LinearLayout container, MyHandler mHandler) {
		CV_Abschnitt mA1 = new CV_Abschnitt(this, getString(R.string.neue_h1), getString(R.string.neue_t1), R.drawable.ic_play);
		CV_Abschnitt mA2 = new CV_Abschnitt(this, getString(R.string.neue_h2), getString(R.string.neue_t2), R.drawable.ic_play);
		CV_Abschnitt mA3 = new CV_Abschnitt(this, getString(R.string.neue_h3), getString(R.string.neue_t3), R.drawable.ic_play);
		CV_Abschnitt mA4 = new CV_Abschnitt(this, getString(R.string.neue_h4), getString(R.string.neue_t4), R.drawable.ic_play);
		CV_Bild b1 = new CV_Bild(this, R.drawable.neue_schrift, getString(R.string.neue_bu1));
		CV_Bild b2 = new CV_Bild(this, R.drawable.neue_gemeindezentrum, getString(R.string.neue_bu2));
		CV_Bild b3 = new CV_Bild(this, R.drawable.neue_synagoge, getString(R.string.neue_bu3));
		CV_Bild b4 = new CV_Bild(this, R.drawable.neue_innen2, getString(R.string.neue_bu4));
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
