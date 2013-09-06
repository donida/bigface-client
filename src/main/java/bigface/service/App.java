package bigface.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	public static ClassPathXmlApplicationContext ctx;

	public static void main(String[] args) {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		RestEventSender restEventSender = (RestEventSender) ctx.getBean("restEventSender");
		FaceRecognation faceRecognation = (FaceRecognation) ctx.getBean("faceRecognation");
//		faceRecognation.grabImageFile("/home/donida", "rafael2.jpg");
		while (true) {
			try {
				int faceCount = faceRecognation.getFaceCount();
				if (faceCount <= 0)
					continue;
				for (int i = 0; i < faceCount; i++)
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
