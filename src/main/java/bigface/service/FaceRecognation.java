package bigface.service;

import static com.googlecode.javacv.cpp.opencv_core.CV_AA;
import static com.googlecode.javacv.cpp.opencv_core.cvClearMemStorage;
import static com.googlecode.javacv.cpp.opencv_core.cvFlip;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSeqElem;
import static com.googlecode.javacv.cpp.opencv_core.cvLoad;
import static com.googlecode.javacv.cpp.opencv_core.cvPoint;
import static com.googlecode.javacv.cpp.opencv_core.cvRectangle;
import static com.googlecode.javacv.cpp.opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.googlecode.javacv.CanvasFrame;
import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.FrameGrabber.Exception;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.CvScalar;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_objdetect.CvHaarClassifierCascade;

@Component
public class FaceRecognation {

	Logger log = Logger.getLogger(FaceRecognation.class);

	public static final String XML_FILE = "/home/donida/dev/workspace/bigface-client/src/main/resources/opencv/data/haarcascades/haarcascade_frontalface_default.xml";

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

}
