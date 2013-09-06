package bigface.service;

import static com.googlecode.javacv.cpp.opencv_contrib.*;
import static com.googlecode.javacv.cpp.opencv_core.CV_AA;
import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvFlip;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSeqElem;
import static com.googlecode.javacv.cpp.opencv_core.cvLoad;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import static com.googlecode.javacv.cpp.opencv_core.cvRectangle;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_contrib.FaceRecognizer;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.MatVector;
import com.googlecode.javacv.cpp.opencv_objdetect.CvHaarClassifierCascade;

@Component
public class FaceRecognation {

	Logger log = Logger.getLogger(FaceRecognation.class);

	public static final String XML_FILE = "/usr/share/opencv/haarcascades/haarcascade_frontalface_default.xml";

	private FrameGrabber grabber;
	
	public FaceRecognation() {
		super();
		grabber = new OpenCVFrameGrabber("");
		try {
			grabber.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public File grabImageFile(String pathToFile, String filename) {
		try {
			IplImage img = grabber.grab();
			BufferedImage image = img.getBufferedImage();
			img.copyTo(image);
			File outputfile = new File(pathToFile+File.separator+filename);
			ImageIO.write(image, "jpg", outputfile);
			return outputfile;
		} catch (Exception e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void initFaceRecognation(boolean useVideoFrame) throws Exception {
		CanvasFrame canvas = null;
		if (useVideoFrame) {
			canvas = new CanvasFrame("Webcam");
			canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
		}
		IplImage img;
		while (true) { 
			img = grabber.grab();
			if (useVideoFrame)
				canvas.setCanvasSize(grabber.getImageWidth(),
						grabber.getImageHeight());
			if (img != null) {
				detect(img, useVideoFrame);
				if (useVideoFrame) {
					cvFlip(img, img, 1);
					canvas.showImage(img);
				}
			}
		}
	}

	public int getFaceCount() throws Exception {
		IplImage img = grabber.grab();
		if (img == null) {
			return -1;
		}
		int count = detect(img, false);
		return count;
	}
	
	private int detect(IplImage src, boolean markFace) {
		CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(
				cvLoad(XML_FILE));
		CvMemStorage storage = CvMemStorage.create();
		CvSeq sign = cvHaarDetectObjects(src, cascade, storage, 1.5, 3,
				CV_HAAR_DO_CANNY_PRUNING);

		cvClearMemStorage(storage);

		int total_Faces = sign.total();

		log.debug("total de faces = " + total_Faces);

		if (markFace)
			markFace(total_Faces, sign, src);

		return total_Faces;

	}

	private void markFace(int total_Faces, CvSeq sign, IplImage src) {
		for (int i = 0; i < total_Faces; i++) {
			CvRect r = new CvRect(cvGetSeqElem(sign, i));
			cvRectangle(src, cvPoint(r.x(), r.y()),
					cvPoint(r.width() + r.x(), r.height() + r.y()),
					CvScalar.RED, 2, CV_AA, 0);
		}
	}
	
//	private int detect(IplImage src, IplImage faceToFind, boolean markFace) {
//		CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(
//				cvLoad(XML_FILE));
//		CvMemStorage storage = CvMemStorage.create();
//		CvSeq sign = cvHaarDetectObjects(src, cascade, storage, 1.5, 3,
//				CV_HAAR_DO_CANNY_PRUNING);
//
//		cvClearMemStorage(storage);
//
//		int total_Faces = sign.total();
//
//		log.debug("total de faces = " + total_Faces);
//
//		if (markFace)
//			markFace(total_Faces, sign, src);
//
//		return total_Faces;
//
//	}

	public static void main(String[] args) throws java.lang.Exception {
		OpenCVFrameGrabber grabber = new OpenCVFrameGrabber("");
		grabber.start();
		
		int[] labels = new int[20];
		MatVector images = new MatVector(20);
		
		IplImage img;
		IplImage grayImg;

		int label = 10;
		System.out.println("INICIANDO TREINAMENTO RONALDO... FICA NA FRENTE DA CAMERA");
		for (int i = 0; i < 10 ; i++) { 
			img = grabber.grab();
			grayImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 1);
			cvCvtColor(img, grayImg, CV_BGR2GRAY);
			labels[i] = label;
			images.put(i, grayImg); 
			Thread.sleep(500);
		}
		System.out.println("ACABOU O TREINAMENTO RONALDO... SAI DA FRENTE...");
		Thread.sleep(1500);
		label = 24;
		System.out.println("INICIANDO TREINAMENTO RAFAEL... FICA NA FRENTE DA CAMERA");
		for (int i = 10; i < 20 ; i++) { 
			img = grabber.grab();
			grayImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 1);
			cvCvtColor(img, grayImg, CV_BGR2GRAY);
			labels[i] = label;
			images.put(i, grayImg); 
			Thread.sleep(500);
		}
		System.out.println("ACABOU O TREINAMENTO RAFAEL... SAI DA FRENTE...");
		Thread.sleep(3000);
        
        FaceRecognizer faceRecognizer = createFisherFaceRecognizer();
//      FaceRecognizer faceRecognizer = createEigenFaceRecognizer();
//      FaceRecognizer faceRecognizer = createLBPHFaceRecognizer();
		
        faceRecognizer.train(images, labels);

		System.out.println("INICIANDO BUSCA PELO LABEL...");

		for (;;) { 
			img = grabber.grab();
			grayImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 1);
			cvCvtColor(img, grayImg, CV_BGR2GRAY);
	        int predictedLabel = faceRecognizer.predict(grayImg);
	        System.out.println("Predicted label: " + predictedLabel);
			Thread.sleep(500);
		}

	}

}
