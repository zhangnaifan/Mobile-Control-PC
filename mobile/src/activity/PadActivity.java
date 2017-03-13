package activity;

import com.mobile.R;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;
import manager.RemoteActivityManager;
import thread.MouseThread;
import thread.PadThread;
import util.Constant;
import view.PointerView;

public class PadActivity extends BaseActivity implements OnTouchListener{

	private TextView pad;

	private float X, Y;
	
	// 初始值的x，y坐标
	private float initX, initY;

	// 移动后的x，y坐标
	private float disX, disY;

	// 抬起的x，y坐标
	private float upX, upY;
	
	private PointerView pointerView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pad);
		initView();
	}

	/**
	 * 初始化视图
	 */
	private void initView() {

		pad = (TextView) findViewById(R.id.pad);

		pad.setOnTouchListener(this);
		pointerView = (PointerView)findViewById(R.id.ball);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		RemoteActivityManager.getInstance().finishActivity();
	}

	@Override
    public boolean onTouch(View v,final MotionEvent event) {
        // TODO Auto-generated method stub
        int action = event.getAction();
        switch (action) {
        case MotionEvent.ACTION_DOWN:
        	initX = event.getX();
			initY = event.getY();
			pointerView.setCurX(event.getX());
			pointerView.setCurY(event.getY());
			pointerView.invalidate();
			pointerView.setVisibility(View.VISIBLE);
			// 抬起记录按下的x,y
			upX = event.getX();
			upY = event.getY();
			break;
        case MotionEvent.ACTION_MOVE:
            //得到触摸点的个数
            int count = event.getPointerCount();
            
            if(count > 1){ //双指移动鼠标，不作图
            	disX = event.getX() - initX;
    			disY = event.getY() - initY;
    			pointerView.setCurX(event.getX());
    			pointerView.setCurY(event.getY());
    			pointerView.invalidate();
    			// 如果移动了
    			if (disX != 0 || disY != 0) {
    				String msg = "<" + disX + "," + disY + ";N>";
    				BaseApplication
    						.getInstance()
    						.getExecutor()
    						.execute(
    								new PadThread(BaseApplication.getInstance(),
    										msg));
    			}
    			initX = event.getX();
    			initY = event.getY();
            }else if(count == 1){ //单指作图
            	disX = event.getX() - initX;
    			disY = event.getY() - initY;
    			pointerView.setCurX(event.getX());
    			pointerView.setCurY(event.getY());
    			pointerView.invalidate();
    			// 如果移动了
    			if (disX != 0 || disY != 0) {
    				String msg = "<" + disX + "," + disY + ";P>";

    				BaseApplication
    						.getInstance()
    						.getExecutor()
    						.execute(
    								new PadThread(BaseApplication.getInstance(),
    										msg));
    			}
    			initX = event.getX();
    			initY = event.getY();
            }
            break;
            // 抬起动作
     		case MotionEvent.ACTION_UP:
 			// 如果没有移动过
 			if ((event.getX() - upX) == 0 && (event.getY() - upY) == 0) {
 					BaseApplication
 					.getInstance()
 					.getExecutor()
 					.execute(
 							new PadThread(BaseApplication.getInstance(),
 									Constant.MOUSE_LEFT));
 			}
 			pointerView.setVisibility(View.GONE);
 			break;
        }
        return true;
    }
}
