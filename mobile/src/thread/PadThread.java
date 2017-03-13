package thread;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;

import activity.BaseApplication;
import net.SocketClientManager;
import util.Constant;

public class PadThread implements Runnable {

	private Socket socket;

	private DataOutputStream out;


	private String msg;
	BaseApplication application;
	public PadThread(BaseApplication application, String msg) {
		this.msg = msg;
		this.application = application;
	}

	@Override
	public void run() {
		try {
			socket = SocketClientManager.getSocket(application.getIpAdress(), Constant.PC_PAD_CON);
			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(msg);
		} catch (Exception e) {
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}
