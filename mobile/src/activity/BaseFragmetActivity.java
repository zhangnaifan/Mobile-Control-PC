package activity;

import android.os.Bundle;
import manager.RemoteActivityManager;
import slidingmenu.app.SlidingFragmentActivity;

public class BaseFragmetActivity extends SlidingFragmentActivity{
	protected BaseApplication application;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application = (BaseApplication)getApplication();
	}
}
