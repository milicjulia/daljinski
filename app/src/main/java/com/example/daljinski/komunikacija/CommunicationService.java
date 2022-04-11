package com.example.daljinski.komunikacija;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class CommunicationService extends Service implements STBCommunicationTask.STBTaskListenner {
	private final IBinder communicationBinder = new CommunicationBinder();
	private STBCommunication stbDriver = new STBCommunication();

	public class CommunicationBinder extends Binder {
		public STBCommunication getSTBDriver() {
			return stbDriver;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return communicationBinder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (stbDriver.isConnected()) {
			new STBCommunicationTask(this, stbDriver).execute(STBCommunication.REQUEST_DISCONNECT);
		}
	}

	@Override
	public void requestSucceed(String request, String message, String command) {
		// TODO Auto-generated method stub
	}

	@Override
	public void requestFailed(String request, String message, String command) {
		// TODO Auto-generated method stub
	}
}
