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
package com.example.daljinski.komunikacija;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
public class STBRemoteControlCommunication {

	private static final String TAG = "STBRemoteControlCommunication"; 

	Messenger mService = null;
	boolean mIsBound;
	final Messenger mMessenger = new Messenger(new IncomingHandler());

	public static final int CMD__MOVE_UP = 16;
	public static final int CMD__MOVE_DOWN = 17;
	public static final int CMD__SELECT = 20;
	public static final int CMD__SOUND_PLUS = 25;
	public static final int CMD__SOUND_MINUS = 26;

	private static final int MSG__REGISTER_CLIENT = 1;
	private static final int MSG__UNREGISTER_CLIENT = 2;

	private boolean[] escape = {false, false};

	class IncomingHandler extends Handler {


	private ServiceConnection mConnection = new ServiceConnection() {
		public void onServiceConnected(ComponentName className, IBinder service) {
			mService = new Messenger(service);
			try {
				Message msg = Message.obtain(null, MSG__REGISTER_CLIENT);
				msg.replyTo = mMessenger;
				mService.send(msg);
			} catch (RemoteException e) {
				Log.e(TAG, "error :\n", e);
			}
		}

		public void onServiceDisconnected(ComponentName className) {
			mService = null;
		}
	};

	}
}