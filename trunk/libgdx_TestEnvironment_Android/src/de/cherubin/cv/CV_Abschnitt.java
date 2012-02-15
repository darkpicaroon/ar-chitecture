package de.cherubin.cv;

import de.cherubin.AbstractPageActivity;
import de.cherubin.PlaybackInterface;
import de.cherubin.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CV_Abschnitt extends LinearLayout implements OnClickListener {
	private TextView mHeadline;
	public TextView mText;
	public Button mPlayButton;
	private PlaybackInterface mPbi;

	public static enum ZUSTAND {
		IS_PLAYING, NOT_PLAYING
	};

	public ZUSTAND mZustand;

	public CV_Abschnitt(AbstractPageActivity pbi, String headline, String text, int drawableID) {
		super(pbi.getApplicationContext());

		LayoutInflater inflater = (LayoutInflater) pbi.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.cv_abschnitt, this, true);

		this.setOrientation(LinearLayout.VERTICAL);

		mPbi = pbi;
		mHeadline = (TextView) this.findViewById(R.id.headline);
		mText = (TextView) this.findViewById(R.id.text);
		mPlayButton = (Button) this.findViewById(R.id.play_button);

		mZustand = ZUSTAND.NOT_PLAYING;

		initView(headline, text, drawableID);
	}

	private void initView(String headline, String text, int drawableID) {
		mHeadline.setText(headline);
		mText.setText(text);
		mPlayButton.setBackgroundResource(drawableID);
		mPlayButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View btn) {
		mPbi.click(this, mZustand);
	}



}
