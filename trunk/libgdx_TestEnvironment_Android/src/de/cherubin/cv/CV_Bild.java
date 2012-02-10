package de.cherubin.cv;

import de.cherubin.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CV_Bild extends LinearLayout {
	private TextView mUnterschrift;
	public ImageView mBild;

	public CV_Bild(Activity activity, int drawableID, String unterschrift) {
		super(activity.getApplicationContext());

		LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.cv_bild, this, true);

		this.setOrientation(LinearLayout.VERTICAL);

		mUnterschrift = (TextView) this.findViewById(R.id.bild_unterschrift);
		mBild = (ImageView) this.findViewById(R.id.bild);

		initView(unterschrift, activity.getResources().getDrawable(drawableID));
	}

	private void initView(String headline, Drawable drawable) {
		mUnterschrift.setText(headline);
		mBild.setImageDrawable(drawable);
	}

}
