package de.cherubin;

import de.cherubin.cv.CV_Abschnitt;
import de.cherubin.cv.CV_Bild;
import de.cherubin.listener.MyHandler;
import android.os.Bundle;
import android.widget.LinearLayout;

public class SemperActivity extends AbstractPageActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	void initData(LinearLayout container, MyHandler mHandler) {
		CV_Abschnitt mA1 = new CV_Abschnitt(this, getString(R.string.semper_h1), getString(R.string.semper_t1), R.drawable.ic_play);
		CV_Abschnitt mA2 = new CV_Abschnitt(this, getString(R.string.semper_h2), getString(R.string.semper_t2), R.drawable.ic_play);
		CV_Abschnitt mA3 = new CV_Abschnitt(this, getString(R.string.semper_h3), getString(R.string.semper_t3), R.drawable.ic_play);
		CV_Bild b1 = new CV_Bild(this, R.drawable.semper_bild, getString(R.string.semper_bu1));
		CV_Bild b2 = new CV_Bild(this, R.drawable.semper_opernhaus, getString(R.string.semper_bu2));
		CV_Bild b3 = new CV_Bild(this, R.drawable.semper_galerie, getString(R.string.semper_bu3));
		container.addView(mA1);
		container.addView(b1);
		container.addView(mA2);
		container.addView(b2);
		container.addView(mA3);
		container.addView(b3);
		mHandler.addComponent(mA1);
		mHandler.addComponent(mA2);
		mHandler.addComponent(mA3);
	}

}
