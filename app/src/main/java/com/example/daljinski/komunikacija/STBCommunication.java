package com.example.daljinski.komunikacija;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import android.util.Log;

public class STBCommunication {
	
	private static final String TAG = STBCommunication.class.getSimpleName();
	
	public static final int COMMUNICATION_PORT = 2000;
	public static final String REQUEST_SCAN = "scan";
	public static final String REQUEST_CONNECT = "connect";
	public static final String REQUEST_DISCONNECT = "disconnect";
	public static final String REQUEST_COMMAND = "command";
	public static final String REQUEST_UNKNOWN = "unknown";
	
	private Socket sock;
	private PrintWriter out;
	
	public String scanSubnet(String localAddr, int port) {
		String subnet = localAddr.substring(0, localAddr.lastIndexOf(".") + 1);
		Socket socket = null;
		PrintWriter out = null;
	    BufferedReader in = null;
	    String ipServer = null;
		for (int d = 1 ; d < 255 ; d ++) {
			String ip = subnet + d;
			SocketAddress address = new InetSocketAddress(ip, port);
			socket = new Socket();
			try {
				socket.connect(address, 20);
				socket.setSoTimeout(100);
				out = new PrintWriter(socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out.println("Hello");
				if ("World".equals(in.readLine())) {
					ipServer = ip;
					out.println("CLIENT_DISCONNECT");
					in.close();
					out.close();
					socket.close();
					break;
				}
				in.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
			}
		}
		Log.i(TAG, "scan ip server: " + ipServer);
		return ipServer;
	}
	
	public boolean stbConnect(final String ip, final int port) {
        try {
            sock = new Socket(ip, port);
            sock.setReuseAddress(true);
			out = new PrintWriter(sock.getOutputStream(), true);
            Log.i(TAG, "Connected to the STB");
            return true;
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
			return false;
		}
	}
	
	public boolean stbDisconnect() {
		if (isConnected()) {
			out.println("CLIENT_DISCONNECT");
			try {
				out.close();
				sock.close();
				Log.i(TAG, "Disconnected from the STB");
				return true;
			} catch (IOException e) {
				Log.e(TAG, e.getMessage());
				return false;
			}
		}
		return false;
	}

	public boolean stbSend(final String msg) {
		if (isConnected()) {
			try {
				out.println(msg);
				return true;
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
				return false;
			}
		}
		return false;
	}

	public boolean isConnected () {
		if (sock != null && out != null) {
			out.println("Test");
			return !out.checkError();
		}
		return false;
	}
}
