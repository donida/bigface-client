package bigface.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	public static ClassPathXmlApplicationContext ctx;

	public static void main(String[] args) {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		RestEventSender restEventSender = (RestEventSender) ctx.getBean("restEventSender");
		while (true) {
			try {
				restEventSender.sendEvent();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
