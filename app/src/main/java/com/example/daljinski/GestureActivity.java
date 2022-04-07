/**
 * Copyright (C) 2012 Sylvain Bilange, Fabien Fleurey <fabien@fleurey.com>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.daljinski;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import com.example.daljinski.komunikacija.CommunicationService;
import com.example.daljinski.komunikacija.CommunicationServiceConnection;
import com.example.daljinski.komunikacija.GestureManager;
import com.example.daljinski.komunikacija.STBCommunication;
import com.example.daljinski.komunikacija.STBCommunicationTask;

public class GestureActivity extends Activity implements STBCommunicationTask.STBTaskListenner {

	private static final String TAG = GestureActivity.class.getSimpleName();
	private static final int DISPLAY_TIME = 1000;
	
	private final CommunicationServiceConnection serviceConnection = new CommunicationServiceConnection();
	private final Handler handler = new Handler();
	
	private GestureManager gestureManager;
	private GestureDetector gestureDetector;
	private ImageView image;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(getApplicationContext(), CommunicationService.class), serviceConnection, Context.BIND_AUTO_CREATE);
    }

	@Override
    protected void onStop(){
    	super.onStop();
    	if (serviceConnection.isBound()) {
    		unbindService(serviceConnection);
    		serviceConnection.setBound(false);
    	}
    }
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return gestureDetector.onTouchEvent(event);
	}




	
	private void sendMessageToSTB(String msg) {
		if (serviceConnection.isBound()) {
			new STBCommunicationTask((STBCommunicationTask.STBTaskListenner) this, serviceConnection.getSTBDriver()).execute(STBCommunication.REQUEST_COMMAND, msg);
		}
	}

	private Runnable cancelDisplay = new Runnable() {
		@Override
		public void run() {

		}
	};

	@Override
	public void requestSucceed(String request, String message, String command) {

	}

	@Override
	public void requestFailed(String request, String message, String command) {

	}
}
