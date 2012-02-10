package de.cherubin;

import java.util.HashMap;

import de.cherubin.cv.CV_Abschnitt;
import de.cherubin.cv.CV_Abschnitt.ZUSTAND;
import de.cherubin.cv.CV_Bild;
import de.cherubin.listener.MyHandler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.widget.LinearLayout;

public class GeschichteActivity extends Activity implements OnInitListener, PlaybackInterface, OnUtteranceCompletedListener {
	private static final int MY_DATA_CHECK_CODE = 0;

	private Drawable dStop;
	private MyHandler mHandler;
	private Drawable dPlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();

		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

	}

	private void initLayout() {
		setContentView(R.layout.page);
		LinearLayout container = (LinearLayout) findViewById(R.id.container);
		
		CV_Abschnitt mA1 = new CV_Abschnitt(this, getString(R.string.geschichte_h1), getString(R.string.geschichte_t1), R.drawable.ic_play);
		CV_Abschnitt mA2 = new CV_Abschnitt(this, getString(R.string.geschichte_h2), getString(R.string.geschichte_t2), R.drawable.ic_play);
		CV_Abschnitt mA3 = new CV_Abschnitt(this, getString(R.string.geschichte_h3), getString(R.string.geschichte_t3), R.drawable.ic_play);
		CV_Abschnitt mA4 = new CV_Abschnitt(this, getString(R.string.geschichte_h4), getString(R.string.geschichte_t4), R.drawable.ic_play);
		CV_Bild b1 = new CV_Bild(this, R.drawable.geschichte_alte_synagoge, getString(R.string.geschichte_bu1));
		CV_Bild b2 = new CV_Bild(this, R.drawable.geschichte_friedrich_synagoge, getString(R.string.geschichte_bu2));
		CV_Bild b3 = new CV_Bild(this, R.drawable.geschichte_denkmal, getString(R.string.geschichte_bu3));
		container.addView(mA1);
		container.addView(mA2);
		container.addView(b1);
		container.addView(mA3);
		container.addView(b2);
		container.addView(mA4);
		container.addView(b3);
		
		mHandler = new MyHandler(this);
		mHandler.addComponent(mA1);
		mHandler.addComponent(mA2);
		mHandler.addComponent(mA3);
		mHandler.addComponent(mA4);
		
		dStop = getResources().getDrawable(R.drawable.ic_stop);
		dPlay = getResources().getDrawable(R.drawable.ic_play);

	}

	private TextToSpeech mTts;
	private HashMap<String, String> mParams;

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == MY_DATA_CHECK_CODE) {
			if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
				mTts = new TextToSpeech(this, this);
			} else {
				// missing data, install it
				Intent installIntent = new Intent();
				installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
				startActivity(installIntent);
			}
		}
	}

	@Override
	public void onInit(int status) {
		mTts.setOnUtteranceCompletedListener(this);
		mParams = new HashMap<String, String>();
		mParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "stringId");
	}

	public void click(CV_Abschnitt abschnitt, ZUSTAND zustand) {
		if (zustand == ZUSTAND.IS_PLAYING)
			stopPlaying(abschnitt);
		else
			startPlaying(abschnitt);
	}

	private void startPlaying(CV_Abschnitt abschnitt) {
		mTts.speak(abschnitt.mText.getText().toString(), TextToSpeech.QUEUE_FLUSH, mParams);
		abschnitt.mPlayButton.setBackgroundDrawable(dStop);
		abschnitt.mZustand = ZUSTAND.IS_PLAYING;
	}

	public void stopPlaying(CV_Abschnitt abschnitt) {
		mTts.stop();
		abschnitt.mPlayButton.setBackgroundDrawable(dPlay);
		abschnitt.mZustand = ZUSTAND.NOT_PLAYING;
	}

	@Override
	public void onUtteranceCompleted(String utteranceId) {
		mHandler.sendEmptyMessage(0);
	}
}
