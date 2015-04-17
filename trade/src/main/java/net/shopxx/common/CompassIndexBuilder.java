package net.shopxx.common;
import org.compass.gps.CompassGps;
import org.springframework.beans.factory.InitializingBean;

/**
 * 通过quartz调度定时重建索引或自动随Spring ApplicationContext启动而重建索引
 * ============================================================================
 *  。
 * ----------------------------------------------------------------------------
 *  。
 * ----------------------------------------------------------------------------
 *  
 * ----------------------------------------------------------------------------
 *  13D156FE217D4020D96236A46E1DB023
 * ============================================================================
 */

public class CompassIndexBuilder implements InitializingBean {

	private int lazyTime = 30;// 索引操作线程延时，单位:秒
	private CompassGps compassGps;

	private Thread indexThread = new Thread() {
		@Override
		public void run() {
			try {
				Thread.sleep(lazyTime * 1000);
				compassGps.index();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	};

	public void afterPropertiesSet() throws Exception {
		indexThread.setDaemon(true);
		indexThread.setName("Compass Indexer");
		indexThread.start();
	}

	public void index() {
		compassGps.index();
	}

	public void setLazyTime(int lazyTime) {
		this.lazyTime = lazyTime;
	}

	public void setCompassGps(CompassGps compassGps) {
		this.compassGps = compassGps;
	}
}