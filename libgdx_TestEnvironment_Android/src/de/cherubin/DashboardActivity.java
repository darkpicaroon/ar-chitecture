package de.cherubin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;

public class DashboardActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();

	}

	private void initLayout() {
		setContentView(R.layout.dashboard);
		// btn_play_person = (Button) findViewById(R.id.play_semper_person);
		// btn_play_zeit1 = (Button) findViewById(R.id.play_semper_zeit1);
		// btn_play_zeit2 = (Button) findViewById(R.id.play_semper_zeit2);
		// btn_play_person.setOnClickListener(this);
		// btn_play_zeit1.setOnClickListener(this);
		// btn_play_zeit2.setOnClickListener(this);
	}

	public void onClickSemper(View v) {
		startActivity(new Intent(this, SemperActivity.class));
	}

	public void onClickGeschichte(View v) {
		startActivity(new Intent(this, GeschichteActivity.class));
	}

	public void onClickAlte(View v) {

	}

	public void onClickNeue(View v) {

	}

	public void onClickEinstellungen(View v) {

	}

	public void onClickQuellen(View v) {

	}

}
