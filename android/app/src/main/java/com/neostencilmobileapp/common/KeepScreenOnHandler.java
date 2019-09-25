package com.neostencilmobileapp.common;

import android.view.Window;
import android.view.WindowManager;

import com.longtailvideo.jwplayer.JWPlayerView;
import com.longtailvideo.jwplayer.events.CompleteEvent;
import com.longtailvideo.jwplayer.events.ErrorEvent;
import com.longtailvideo.jwplayer.events.PauseEvent;
import com.longtailvideo.jwplayer.events.PlayEvent;
import com.longtailvideo.jwplayer.events.listeners.VideoPlayerEvents;

/**
 * Sets the FLAG_KEEP_SCREEN_ON flag during playback - disables it when playback is stopped
 */
public class KeepScreenOnHandler implements VideoPlayerEvents.OnPlayListener,
											VideoPlayerEvents.OnPauseListener,
											VideoPlayerEvents.OnCompleteListener,
											VideoPlayerEvents.OnErrorListener{

	/**
	 * The application window
	 */
	private Window mWindow;

	public KeepScreenOnHandler(JWPlayerView jwPlayerView, Window window) {
		jwPlayerView.addOnPlayListener(this);
		jwPlayerView.addOnPauseListener(this);
		jwPlayerView.addOnCompleteListener(this);
		jwPlayerView.addOnErrorListener(this);
		mWindow = window;
	}

	private void updateWakeLock(boolean enable) {
		if (enable) {
			mWindow.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		} else {
			mWindow.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}

	@Override
	public void onError(ErrorEvent errorEvent) {
		updateWakeLock(false);
	}

	@Override
	public void onComplete(CompleteEvent completeEvent) {
		updateWakeLock(false);
	}

	@Override
	public void onPause(PauseEvent pauseEvent) {
		updateWakeLock(false);

	}

	@Override
	public void onPlay(PlayEvent playEvent) {
		updateWakeLock(true);

	}
}
