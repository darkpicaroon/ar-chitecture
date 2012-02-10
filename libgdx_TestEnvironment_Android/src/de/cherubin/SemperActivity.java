package de.cherubin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SemperActivity extends Activity implements OnInitListener, OnClickListener, OnUtteranceCompletedListener {
	private static final int MY_DATA_CHECK_CODE = 0;
	private Button btn_play_person;
	private Button btn_play_zeit1;
	private Button btn_play_zeit2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();

		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

	}

	private void initLayout() {
		setContentView(R.layout.showtext);
		btn_play_person = (Button) findViewById(R.id.play_semper_person);
		btn_play_zeit1 = (Button) findViewById(R.id.play_semper_zeit1);
		btn_play_zeit2 = (Button) findViewById(R.id.play_semper_zeit2);
		btn_play_person.setOnClickListener(this);
		btn_play_zeit1.setOnClickListener(this);
		btn_play_zeit2.setOnClickListener(this);
	}

	private TextToSpeech mTts;
	private int activeBtnID = 0;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				// success, create the TTS instance
				mTts = new TextToSpeech(this, this);
				mTts.setOnUtteranceCompletedListener(this);
			} else {
				// missing data, install it
				Intent installIntent = new Intent();
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}

	@Override
	public void onClick(View btn) {
		if (mTts.isSpeaking()) {
			mTts.stop();
			setAllButtonImage();
			if (activeBtnID != btn.getId()) {
				activeBtnID=btn.getId();
				startPlay(btn);
			}
		} else {
			startPlay(btn);
		}

	}

	@Override
	public void onUtteranceCompleted(String arg0) {
		setAllButtonImage();
	}

	private void startPlay(View btn) {
		if (btn.getId() == R.id.play_semper_person)
			mTts.speak(getString(R.string.semper_person_text), TextToSpeech.QUEUE_FLUSH, null);
		if (btn.getId() == R.id.play_semper_zeit1)
			mTts.speak(getString(R.string.semper_zeit_text1), TextToSpeech.QUEUE_FLUSH, null);
		if (btn.getId() == R.id.play_semper_zeit2)
			mTts.speak(getString(R.string.semper_zeit_text2), TextToSpeech.QUEUE_FLUSH, null);
		setButtonImage(btn, R.drawable.ic_stop);
	}

	private void setButtonImage(View btn, int id) {
		btn.setBackgroundDrawable(getResources().getDrawable(id));
	}

	private void setAllButtonImage() {
		btn_play_person.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_play));
		btn_play_zeit1.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_play));
		btn_play_zeit2.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_play));
	}

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub

	}

}
