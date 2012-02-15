package de.cherubin.listener;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;
import de.cherubin.AbstractPageActivity;
import de.cherubin.cv.CV_Abschnitt;

public class MyHandler extends Handler {
	ArrayList<CV_Abschnitt> components = new ArrayList<CV_Abschnitt>();
	private AbstractPageActivity activity;

	public MyHandler(AbstractPageActivity geschichteActivity) {
		this.activity = geschichteActivity;
	}

	public void addComponent(CV_Abschnitt cv) {
		components.add(cv);
	}

	@Override
	public void handleMessage(Message msg) {
		for (CV_Abschnitt cv : components)
			activity.stopPlaying(cv);
	}

}
