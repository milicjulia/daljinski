
package com.example.daljinski.komunikacija;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class GestureManager extends SimpleOnGestureListener {

	private static final int SLIDE_MIN_DISTANCE = 120;
    private static final int SLIDE_VELOCITY_THRESHOLD = 200;
	
	private GestureHandler receiver;
	
	public GestureManager(GestureHandler receiver) {
		this.receiver = receiver;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		if (e1.getY() - e2.getY() > SLIDE_MIN_DISTANCE && Math.abs(velocityY) > SLIDE_VELOCITY_THRESHOLD) {
			receiver.slidingUp();
		} else if (e2.getY() - e1.getY() > SLIDE_MIN_DISTANCE && Math.abs(velocityY) > SLIDE_VELOCITY_THRESHOLD) {
			receiver.slidingDown();
		}
		return true;
	}


}
