package activity;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;
import exception.CrashException;
import util.ConfigUtil;

public class BaseApplication extends Application{
	
	private static BaseApplication instance;
	
	private ExecutorService executor = Executors.newFixedThreadPool(5);
	
	private String ipAdress;
	
	public static BaseApplication getInstance() {
		if(instance==null){
			synchronized (BaseApplication.class) {
				instance = new BaseApplication();
			}
		}
		return instance;
	}
	
	public ExecutorService getExecutor() {
		return executor;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		CrashException crashInstance = CrashException.getInstance();
		crashInstance.init();
	}
	
	/**
	 * 获取ip地址
	 * 
	 * @return
	 */
	public String getIpAdress() {
		if(ipAdress!=null){
			return ipAdress;
		}
		ipAdress = ConfigUtil.getConfig().get(ConfigUtil.CONF_IP_ADDRESS);
		Log.i("fuck", "ipAdress = "+ipAdress);
		if (TextUtils.isEmpty(ipAdress)){
			return "0.0.0.0";
		}else{
			return ipAdress;
		}
	}
	public void setIpAddress(String ip){
		ipAdress = ip;
		ConfigUtil.getConfig().set(ConfigUtil.CONF_IP_ADDRESS, ip);
	}
}
