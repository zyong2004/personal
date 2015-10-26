package util;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 
 * @author xl
 *
 */
public class ImageUtil {

	private static final String IAMGE_TYPE_DEFAULT = "jpeg";
	private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtil.class);
	
	private static Boolean  DEFAULT_FORCE=false;
	private static String DEFAULT_PREFIX ="thumb_";
	
	private static String thumb_url_path = "http://localhost:8080/mmgw/thumb/image";
	private static String origin_url_path = "http://localhost:8080/mmgw/image";

	public static void storeCompress(InputStream inputFileStream, File outputFile) {
		compressPic(inputFileStream, outputFile, IAMGE_TYPE_DEFAULT);
	}

	
	public static void compressPicJpeg(BufferedImage  im, File descFile) throws Exception{
		FileOutputStream newimage = new FileOutputStream( descFile);
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);
		
		JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);
//		jep.setQuality(0.9f, true);
		encoder.encode(im, jep);
		newimage.close();
		
		
	}
	
	
	public static BufferedImage zoomImage(String src) throws IOException{
		BufferedImage result = null;
		File file =new File(src);
		if(!file.exists()){
			System.out.println("文件不存在");
		}
		BufferedImage im = ImageIO.read(file);
		int width = im.getWidth();
		int height = im.getHeight();
		
		float resizeTimes = 0.3f;
		int toWidth = (int) (width* resizeTimes);
		int toheight = (int) (height* resizeTimes);
		result = new BufferedImage(toWidth, toheight, BufferedImage.TYPE_INT_BGR);
		result.getGraphics().drawImage(im.getScaledInstance(toWidth, toheight, Image.SCALE_SMOOTH), 0, 0, null);
		return result;
	}
	
	public static boolean compressPic(InputStream srcFile, File descFile, String image_type) {
		try {
			BufferedImage src = null;
			FileOutputStream out = null;
			ImageWriter imgWriter;
			ImageWriteParam imgWriteParams;

			if (image_type == null || "".equals(image_type)) {
				image_type = IAMGE_TYPE_DEFAULT;
			}
			// 指定写图片的方式
			imgWriter = ImageIO.getImageWritersByFormatName(image_type).next();
			imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);
			// 要使用压缩，必须指定压缩方式为MODE_EXPLICIT
			imgWriteParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			// 这里指定压缩的程度，参数qality是取值0~1范围内，
			imgWriteParams.setCompressionQuality((float) 0.5);
			imgWriteParams.setProgressiveMode(ImageWriteParam.MODE_DISABLED);
			ColorModel colorModel = ColorModel.getRGBdefault();
			// 指定压缩时使用的色彩模式
			imgWriteParams.setDestinationType(
					new javax.imageio.ImageTypeSpecifier(colorModel, colorModel.createCompatibleSampleModel(16, 16)));

			try {
				if (srcFile == null) {
					return false;
				} else {
					src = ImageIO.read(srcFile);
					// src = ImageIO.read(file);

					out = FileUtils.openOutputStream(descFile);
					// out = new FileOutputStream(descFile);
					imgWriter.reset();
					// 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何
					// OutputStream构造
					imgWriter.setOutput(ImageIO.createImageOutputStream(out));
					// 调用write方法，就可以向输入流写图片
					imgWriter.write(null, new IIOImage(src, null, null), imgWriteParams);
					out.flush();
					out.close();
				}
			} catch (Exception e) {
				LOGGER.error("压缩存储出现问题", e);
				return false;
			} finally {
				IOUtils.closeQuietly(out);
			}
		} catch (Exception e) {
			LOGGER.error("压缩存储出现问题", e);
		} finally {
			IOUtils.closeQuietly(srcFile);
		}
		return true;
	}
	
	
	/**
	 * 根据图片路径生成缩略图
	 * @param imageFile    原图片
     * @param w            缩略图宽
     * @param h            缩略图高
     * @param prevfix    生成缩略图的前缀| 原图片保存不加此前缀
     * @param force        是否强制按照宽高生成缩略图(如果为false，则生成最佳比例缩略图)
     * @param h            返回原图和缩略图路径string[0]原图路径，string[1] 缩略图路径
	 * @return
	 */
	public static String [] thumbnailImage(File imageFile,int w,int h,String prevfix,boolean force,String[] savePath){
		prevfix = prevfix == null?DEFAULT_PREFIX:prevfix;
		String [] uriOriandThumbs = new String[2];
		if(savePath.length!=2){
			LOGGER.debug("original savePath  is empty ,please check !!!!");
			return uriOriandThumbs;
		}
		
		String types = Arrays.toString(ImageIO.getReaderFormatNames());
		String suffix = null;
		//获取图片后缀
		try {
			if (imageFile.getName().indexOf(".") > -1) {
				suffix = imageFile.getName().substring(imageFile.getName().lastIndexOf(".") + 1);
			}
		//类型和图片后缀变为小写后，判断后缀名是否合法，图片是否支持
		if(suffix == null || types.toLowerCase().indexOf(suffix.toLowerCase())<0){
			LOGGER.error("THE Image suffix is illegal,supports suffix is{}.",types);
		}
		
		LOGGER.debug("target image size,width:{},height:{}",w,h);

		Image img = ImageIO.read(imageFile);
		
		if(!force){
			int width = img.getWidth(null);
			int height = img.getHeight(null);
			
			if(width*1.0/w<(height*1.0)/h){
				if(width>w){
					h = Integer.parseInt(new DecimalFormat("0").format(height*w/(width*1.0)));
					LOGGER.debug("change image's width, width:{}, height:{}.",w,h);
				}
			}else{
				if(height>h){
					w = Integer.parseInt(new DecimalFormat("0").format(width*h/(height*1.0)));
					LOGGER.debug("change image's height, width:{}, height:{}.",w,h);
				}
			}
		}
		
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		
		Graphics g = bi.getGraphics();
		g.drawImage(img, 0, 0, w, h,  null);
		g.dispose();
		
		//String p = imageFile.getPath();
		String imageName = generateImageName(suffix);
		
		String origdestDir = savePath[1]+File.separator+imageName;
		String thumbDestDir = savePath[0]+File.separator+prevfix+imageName;
		
		uriOriandThumbs[0] = origin_url_path+"/"+imageName;
		uriOriandThumbs[1] = origin_url_path+"/"+savePath[0].substring(savePath[0].lastIndexOf("/")+1,savePath[0].length())+"/"+prevfix+imageName;
		for (int i = 0; i < uriOriandThumbs.length; i++) {
			LOGGER.info(uriOriandThumbs[i]);
		}
		saveOriginFile(imageFile, origdestDir);
		ImageIO.write(bi, suffix, new File(thumbDestDir));
		} catch ( IOException e) {
			LOGGER.error("generate thumbnail image failed.",e);
		}
		return uriOriandThumbs;
		
	}
	/**
	 * 保存原图
	 * @param file 源文件
	 * @param storePath 保存路径
	 * @param prevfix 前缀
	 * @param suffix 拓展名
	 * @throws IOException
	 */
	public static void saveOriginFile(File file,String imageName) throws IOException{
		Image img = ImageIO.read(file);
		int w = img.getWidth(null);
		int h = img.getHeight(null);
		
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
//		g.drawImage(bi, w, h, null);
		g.drawImage(img, 0, 0, w, h,  null);
		g.dispose();
		ImageIO.write(bi,imageName.substring(imageName.lastIndexOf(".")+1) , new File(imageName));
	}
	
	private static File createImageOutputFile(String p,String storePath,String imageName){
		return new File(p.substring(0,p.lastIndexOf(File.separator)) + File.separator+imageName);
		
	}
	private static String generateImageName(String suffix){
		return String.valueOf(UUID.randomUUID()+"."+suffix);
	}
	public static void main(String[] args) throws IOException, Exception {
		/*System.out.println( Arrays.toString(ImageIO.getReaderFileSuffixes()));
		System.out.println( Arrays.toString(ImageIO.getReaderFormatNames()));
		System.out.println( Arrays.toString(ImageIO.getReaderMIMETypes()));*/
//		File f = new File("d:\\test/11.jpeg");
//		String []savePath = new String[]{"d:/test/test","d:\\test"};
//		thumbnailImage(f, 118, 88, "tt", false,savePath);
//		System.out.println(savePath[0].lastIndexOf("/"));
//		System.out.println(savePath[0].substring(savePath[0].lastIndexOf("/")+1,savePath[0].length()));
	String srcFile ="d:/123.jpeg";
	String descFile ="d:/test11.png";
	compressPic(new FileInputStream(srcFile), new File(descFile), "png");
//	compressPicJpeg(zoomImage(srcFile),new File(descFile));
	}
}
