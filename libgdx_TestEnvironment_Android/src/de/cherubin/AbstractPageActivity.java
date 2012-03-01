package de.cherubin;

import java.util.HashMap;

import de.cherubin.cv.CV_Abschnitt;
import de.cherubin.cv.CV_Abschnitt.ZUSTAND;
import de.cherubin.helper.GUITools;
import de.cherubin.helper.GUITools.ACTIONBAR_TYPE;
import de.cherubin.listener.MyHandler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;
import android.widget.LinearLayout;

abstract public class AbstractPageActivity extends Activity implements OnInitListener, PlaybackInterface, OnUtteranceCompletedListener {
	private static final int MY_DATA_CHECK_CODE = 0;

	private static final String TAG = "AbstractPageActivity";

	private Drawable dStop;
	private MyHandler mHandler;
	private Drawable dPlay;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.page);
		LinearLayout container = (LinearLayout) findViewById(R.id.container);
		GUITools.buildActionBar(this, ACTIONBAR_TYPE.HOME, getString(R.string.ab_dashboard));
		dStop = getResources().getDrawable(R.drawable.ic_stop);
		dPlay = getResources().getDrawable(R.drawable.ic_play);

		mHandler = new MyHandler(this);

		initData(container, mHandler);

		Intent checkIntent = new Intent();
		checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
		startActivityForResult(checkIntent, MY_DATA_CHECK_CODE);

	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d(TAG, "onPause");
		if (mTts != null)
			mTts.stop();
	}

	abstract void initData(LinearLayout container, MyHandler mHandler);

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
