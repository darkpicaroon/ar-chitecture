package de.cherubin;

import de.cherubin.helper.GUITools;
import de.cherubin.helper.GUITools.ACTIONBAR_TYPE;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DashboardActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();

	}

	private void initLayout() {
		setContentView(R.layout.dashboard);
		GUITools.buildActionBar(this, ACTIONBAR_TYPE.HOME, getString(R.string.ab_dashboard));
	}

	public void onClickSemper(View v) {
		startActivity(new Intent(this, SemperActivity.class));
	}

	public void onClickGeschichte(View v) {
		startActivity(new Intent(this, GeschichteActivity.class));
	}

	public void onClickAlte(View v) {
		startActivity(new Intent(this, AlteSynagogeActivity.class));
	}

	public void onClickNeue(View v) {
		startActivity(new Intent(this, NeueSynagogeActivity.class));
	}

	public void onClickQuellen(View v) {
		startActivity(new Intent(this, QuellenActivity.class));
	}

	public void onClick3D(View v) {
		startActivity(new Intent(this, TestEnvironment_Android_2.class));
	}
}
