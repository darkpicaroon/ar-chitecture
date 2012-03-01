package de.cherubin;

import de.cherubin.helper.GUITools;
import de.cherubin.helper.GUITools.ACTIONBAR_TYPE;
import android.app.Activity;
import android.os.Bundle;

public class QuellenActivity extends Activity {

	private static final String TAG = "QuellenActivity";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.quellen);
		GUITools.buildActionBar(this, ACTIONBAR_TYPE.HOME, getString(R.string.ab_dashboard));

	}

}
