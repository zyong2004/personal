package qrcode;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.Random;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class QRCodeUtil {

	private static final String CHARSET = "UTF-8";
	private static final String FORMAT_NAME = "jpg";
	private static final int QRCODE_SIZE = 300;
	private static final int WIDTH = 60;
	private static final int HEIGHT = 60;

	/**
	 * 创建图片
	 * 
	 * @param content
	 * @param imagePath
	 * @param needCompredd
	 * @return
	 * @throws WriterException
	 */
	private static BufferedImage createImage(String contents, String imagePath,
			boolean needCompress) throws Exception {
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
		hints.put(EncodeHintType.MARGIN, 1);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
				BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
		int width = bitMatrix.getWidth();
		int height = bitMatrix.getHeight();
		BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < height; x++) {
			for (int y = 0; y < width; y++) {
				image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
						: 0xFFFFFFFF);
			}
		}
		if (imagePath == null || "".equals(imagePath)) {
			return image;
		}

		QRCodeUtil.insertLogoImage(image, imagePath, needCompress);
		return image;
	}

	/**
	 * 插入LOGO
	 * 
	 * @param source
	 * @param imagePath
	 * @param needCompress
	 * @throws IOException
	 */
	private static void insertLogoImage(BufferedImage source, String imagePath,
			boolean needCompress) throws IOException {
		File file = new File(imagePath);
		if (!file.exists()) {
			System.err.println("图片不存在");
			return;
		}
		Image src = ImageIO.read(file);
		int width = src.getWidth(null);
		int height = src.getHeight(null);

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
		// 插入LOGO
		Graphics2D grap = source.createGraphics();
		int x = (QRCODE_SIZE - width) / 2;
		int y = (QRCODE_SIZE - height) / 2;
		grap.drawImage(src, x, y, null);
		Shape shap = new RoundRectangle2D.Float(x, y, width, height, 6, 6);
		grap.setStroke(new BasicStroke(2f));
		grap.draw(shap);
		grap.dispose();
	}

	/**
	 * 生成二维码(内嵌LOGO)
	 * 
	 * @param content
	 * @param imagePath
	 * @param destPath
	 * @param needCompress
	 * @throws Exception
	 */
	public static void encode(String content, String imagePath,
			String destPath, boolean needCompress) throws Exception {

		BufferedImage image = createImage(content, imagePath, needCompress);
		mkdir(destPath);
		String file = new Random().nextInt(9999999) + ".jpg";
		ImageIO.write(image, FORMAT_NAME, new File(destPath + "/" + file));

	}

	/**
	 * 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
	 * 
	 * @param dest
	 */
	private static void mkdir(String dest) {
		File file = new File(dest);
		if (!file.exists() && !file.isDirectory()) {
			// 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
			file.mkdirs();
		}
	}

	public static void encode(String content, String imagePath, String destPath)
			throws Exception {
		encode(content, imagePath, destPath, false);
	}

	public static void encode(String content, String destPath,
			Boolean needCompress) throws Exception {
		encode(content, null, destPath, needCompress);
	}

	public static void encode(String content, String destPath) throws Exception {
		encode(content, null, destPath, false);
	}

	public static void encode(String content, String imgPath,
			OutputStream output, boolean needCompress) throws Exception {
		BufferedImage image = QRCodeUtil.createImage(content, imgPath,
				needCompress);
		ImageIO.write(image, FORMAT_NAME, output);
	}

	public static void encode(String content, OutputStream output)
			throws Exception {
		QRCodeUtil.encode(content, null, output, false);
	}

	/**
	 * 解析二维码
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	public static String decode(File file) throws Exception {
		BufferedImage image = ImageIO.read(file);
		if (image == null) {
			return null;
		}
		BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(
				image);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
		Result result = new MultiFormatReader().decode(bitmap, hints);
		return result.getText();
	}

	public static String decode(String path) throws Exception {
		return QRCodeUtil.decode(new File(path));
	}

	public static void main(String[] args) throws Exception {
		String text = "薯　灯可分列式本上楞珂要瓜熟蒂落！000000000000000";
		QRCodeUtil.encode(text, "e:/logo.png", "e:/", true);
	}

}
