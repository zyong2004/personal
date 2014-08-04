package qrcode;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Random;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.swetake.util.Qrcode;

/**
 * 使用QRCODE
 * 
 * @author zhangyong
 * 
 */
public class QRCodeEncoderHandler {
	
	private static int HEIGHT = 60;
	private static int WIDTH = 60;
	private static int QRSIZE=295;  // 图片填充   得研究一下   总不能居中对齐  这是定义整个图片大小，包括背景和二维码信息
	public static void encodeQRcode(String msg, String dest, String imagePath,
			boolean needCompress) throws Exception {
		Qrcode code = new Qrcode();
		// L水平 7%的字码可被修正
		// M水平 15%的字码可被修正
		// Q水平 25%的字码可被修正
		// H水平 30%的字码可被修正
		// QR码有容错能力，QR码图形如果有破损，仍然可以被机器读取内容，最高可以到7%~30%面积破损仍可被读取。
		// 相对而言，容错率愈高，QR码图形面积愈大。所以一般折衷使用15%容错能力。
		code.setQrcodeErrorCorrect('H');
		/* N代表数字,A代表字符a-Z,B代表其他字符 */  
		code.setQrcodeEncodeMode('B');
		 /* 设置设置二维码版本，取值范围1-40，值越大尺寸越大，可存储的信息越大 */    
		code.setQrcodeVersion(20);/* 0-20 */
//		code.setQrcodeVersion(7);/* 0-20 */
		
		System.out.println(msg);
		byte[] contentByte = msg.getBytes("utf-8");
		BufferedImage image = new BufferedImage(QRSIZE, QRSIZE,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D gs = image.createGraphics();
		gs.setBackground(Color.WHITE);//对背景色为白色的解析最好
		gs.clearRect(0, 0, QRSIZE, QRSIZE);
		gs.setColor(Color.BLACK);
		// 设置偏移量 不设置可能导致解析出错
		int pixoff = 2;
		// 输出内容> 二维码
		if (contentByte.length > 0 && contentByte.length < 120) {
			boolean[][] codeOut = code.calQrcode(contentByte);
			for (int i = 0; i < codeOut.length; i++) {
				for (int j = 0; j < codeOut.length; j++) {
					if (codeOut[j][i]) {// 如果顺序有问题，就不能扫描
						gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
					}
				}
			}
		} else {
			System.err.println("QRCode content bytes length = "
					+ contentByte.length + " not in [ 0,120 ]. ");

		}

		Image logoimage = getLogoImage(imagePath, needCompress);
		int x = logoimage.getWidth(null);
		int y = logoimage.getHeight(null);
		System.out.println(x+"--"+y);
		gs.drawImage(logoimage, (QRSIZE-x)/2, (QRSIZE-y)/2, null);
		gs.dispose();
		image.flush();
		File file = new File(dest);
		ImageIO.write(image, "png", file);
	}

	public static Image getLogoImage(String imagePath, boolean needCompress)
			throws Exception {

		File file = new File(imagePath);
		if (!file.exists()) {
			System.err.println("图片不存在");
			throw new Exception();
		}
		Image src = ImageIO.read(file);
		int width = src.getWidth(null);
		int height = src.getHeight(null);

		System.out.println(width+"--"+height);
		if (needCompress) {// 压缩logo
			if (width > WIDTH) {
				width = WIDTH;
			}
			if (height > HEIGHT) {
				height = HEIGHT;
			}

			Image image = src.getScaledInstance(width, height,
					Image.SCALE_SMOOTH);

			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			// Graphics2D gh = tag.createGraphics();
			g.drawImage(image, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			src = image;
		}
		return src;

	}

	public static void decodeQRcode(String imagePath) throws IOException {
		File imageFile = new File(imagePath);
		String decodeData = null;

		BufferedImage image = ImageIO.read(imageFile);
		QRCodeDecoder data = new QRCodeDecoder();
		decodeData = new String(data.decode(new J2SEImage(image)), "utf-8");

		System.out.println(decodeData+"==");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String dest = "e:\\weixinlog5788891.png";
		String imagePath = "e:/Desert.jpg";
		String content = "这是测试二维码";
		try {
			// encodeByZxing();
//			decodeQRcode("e:/111.png");
			encodeQRcode(content, dest,imagePath, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * public static void encodeByZxing() throws Exception{ String text = "你好";
	 * int width = 100; int height = 100; String format = "png"; Hashtable
	 * hints= new Hashtable(); hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
	 * BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
	 * BarcodeFormat.QR_CODE, width, height,hints); File outputFile = new
	 * File("new.png"); MatrixToImageWriter.writeToFile(bitMatrix, format,
	 * outputFile);
	 * 
	 * }
	 */
}

class J2SEImage implements QRCodeImage {
	BufferedImage bufImg;

	public J2SEImage(BufferedImage bufImg) {
		this.bufImg = bufImg;
	}

	@Override
	public int getHeight() {
		return bufImg.getHeight();
	}

	@Override
	public int getPixel(int arg0, int arg1) {
		return bufImg.getRGB(arg0, arg1);
	}

	@Override
	public int getWidth() {
		return bufImg.getWidth();
	}

}