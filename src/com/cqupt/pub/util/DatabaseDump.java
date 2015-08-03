package com.cqupt.pub.util;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

public class DatabaseDump extends HttpServlet implements Runnable {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(this.getClass());

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		System.out.println("initial database dump utils ...");
		new Thread(new DatabaseDump()).start();
		System.out.println("initial database dump utils finished");
	}

	// 每55分钟唤醒一次程序，如果时间在凌晨零点到一点之间，则备份数据库
	public void run() {
		while (true) {
			try {
				Thread.sleep(1000 * 60 * 55);
				if (Tools.getNowTime("HH").equals("23")) {
					Runtime.getRuntime().exec(
							"/usr/local/apache-tomcat-7.0.42/qdzc-database-dump.sh qdzc-database-dump-"
									+ Tools.getNowTime("yyyy-MM-dd") + ".sql");
					logger.info("database dump completed " + Tools.getNowTime());
				} else {
					logger.info("database dump abort " + Tools.getNowTime());
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
