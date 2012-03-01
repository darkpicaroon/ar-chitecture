package de.cherubin.helper;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.IntentAction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import de.cherubin.DashboardActivity;
import de.cherubin.R;

public class GUITools {

	public static enum ACTIONBAR_TYPE {
		HOME, PAGE
	}

	public static void buildActionBar(Activity activity, ACTIONBAR_TYPE type, String string) {
		ActionBar actionBar = (ActionBar) activity.findViewById(R.id.actionbar);
		actionBar.setTitle(string);
		switch (type) {
		case HOME:

			actionBar.setHomeAction(new IntentAction(activity, createIntent(activity), R.drawable.ab_home));
			actionBar.setDisplayHomeAsUpEnabled(false);
			break;
		case PAGE:
			// actionBar.setHomeAction(new IntentAction(activity,
			// createIntent(activity), R.drawable.ab_home));

			break;
		default:
			actionBar.setHomeAction(new IntentAction(activity, createIntent(activity), R.drawable.ab_home));
			break;
		}

	}

	public static Intent createIntent(Context context) {
		Intent i = new Intent(context, DashboardActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return i;
	}

}
