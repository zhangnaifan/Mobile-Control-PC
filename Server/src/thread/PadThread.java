package thread;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import util.Constant;

public class PadThread implements Runnable {
	
	private Socket socket;

	private float nowX, nowY;

	private String moveXStr, moveYStr, isPaint;

	private float moveX, moveY;

	private DataInputStream in;

	public PadThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		try {
			// 读取客户端传过来信息的dataInputStream
			in = new DataInputStream(socket.getInputStream());

			// 读取来自客户端的信息
			String accept = in.readUTF();

			System.out.println(accept);

			Robot robot = new Robot();

			if (accept.contains("<")) {

				// 截取x方向的移动距离
				moveXStr = accept.substring(accept.indexOf("<") + 1,
						accept.indexOf(","));

				// 截取y方向的移动距离
				moveYStr = accept.substring(accept.indexOf(",") + 1,
						accept.indexOf(";"));
				
				isPaint = accept.substring(accept.indexOf(";") + 1,
						accept.indexOf(">"));

				moveX = Float.parseFloat(moveXStr);

				moveY = Float.parseFloat(moveYStr);

				Point point = MouseInfo.getPointerInfo().getLocation();

				nowX = point.x;

				nowY = point.y;

				if (isPaint.equals("P") && moveX < 15 && moveX > -15
						&& moveY < 15 && moveY > -15) {
					robot.mousePress(InputEvent.BUTTON1_MASK);
				}
				
				robot.mouseMove((int) (nowX + moveX), (int) (nowY + moveY));
				
				if (isPaint.equals("P")) {
					robot.mouseRelease(InputEvent.BUTTON1_MASK);
				}
			} else if (accept.equals(Constant.MOUSE_LEFT)) {

				// 左键按下
				robot.mousePress(InputEvent.BUTTON1_MASK);

				robot.mouseRelease(InputEvent.BUTTON1_MASK);

			} else if (accept.equals(Constant.MOUSE_RIGHT)) {

				// 右键按下
				robot.mousePress(InputEvent.BUTTON3_MASK);

				robot.mouseRelease(InputEvent.BUTTON3_MASK);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
