package de.cherubin;

import de.cherubin.cv.CV_Abschnitt;
import de.cherubin.cv.CV_Abschnitt.ZUSTAND;

public interface PlaybackInterface {
	public void click(CV_Abschnitt abschnitt, ZUSTAND zustand);
}
