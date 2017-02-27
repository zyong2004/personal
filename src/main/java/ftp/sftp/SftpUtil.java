package ftp.sftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpException;

/**
 * 本工具基于jsch <br/>
 * JSch是Java Secure
 * Channel的缩写。JSch是一个SSH2的纯Java实现。它允许你连接到一个SSH服务器，并且可以使用端口转发，X11转发，文件传输等。
 * 
 * @author zhangyong
 * 
 */
public class SftpUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(SftpUtil.class);

	private static String[] defaultArray = new String[0];
	
	private ChannelSftp channelSftp;

	private SFTPChannel channel;

	public SftpUtil() {
		channel = new SFTPChannel();
		channelSftp = channel.getChannel();
	}

	/**
	 * 上传文件，
	 * 
	 * @param f
	 *            文件路径
	 * @param path
	 *            储存路径，必须是存在的路径
	 * @return
	 */
	public boolean upload(File f, String path) {

		String currentDir = currentDir();

		if (!changeDirectory(path)) {
			return false;
		}

		// String dest = path.concat("/").concat(f.getName());
		try {
			channelSftp.put(new FileInputStream(f), f.getName(),
					ChannelSftp.OVERWRITE);

			if (!existsFile(f.getName())) {
				logger.debug("upload failed...");
				return false;
			}
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			changeDirectory(currentDir);
		}
		return false;

	}

	/**
	 * 下载文件
	 * 
	 * @param remotePath
	 *            远程服务目录
	 * @param localPath
	 *            本地目录，全路径
	 * @return
	 */
	public boolean downLoadFile(String remotePath, String localPath) {
		String currentPath = currentDir();

		if (!changeDirectory(remotePath)) {
			return false;
		}

		try {
			channelSftp.get(remotePath, localPath);
			return true;
		} catch (SftpException e) {
			e.printStackTrace();
		} finally {
			changeDirectory(currentPath);
		}
		return false;

	}

	/**
	 * 创建目录
	 * <p>
	 * 使用示例，SFTP服务器上的目录结构如下：/testA/testA_B/
	 * <table border="1">
	 * <tr>
	 * <td>当前目录</td>
	 * <td>方法</td>
	 * <td>参数(绝对路径/相对路径)</td>
	 * <td>创建成功后的目录</td>
	 * </tr>
	 * <tr>
	 * <td>/testA/testA_B/</td>
	 * <td>makeDir("testA_B_C")</td>
	 * <td>相对路径</td>
	 * <td>/testA/testA_B/testA_B_C/</td>
	 * </tr>
	 * <tr>
	 * <td>/</td>
	 * <td>makeDir("/testA/testA_B/testA_B_D")</td>
	 * <td>绝对路径</td>
	 * <td>/testA/testA_B/testA_B_D/</td>
	 * </tr>
	 * </table>
	 * <br/>
	 * <b>注意</b>，当<b>中间目录不存在</b>的情况下，不能够使用绝对路径的方式期望创建中间目录及目标目录。
	 * 例如makeDir("/testNOEXIST1/testNOEXIST2/testNOEXIST3")，这是错误的。
	 * </p>
	 * TODO 循环进行创建目录
	 * 
	 * @param path
	 *            目录
	 * @return boolean
	 */
	public boolean mkdir(String path) {
		logger.debug("make dir :{}", path);
		try {
			channelSftp.mkdir(path);
			return true;
		} catch (SftpException e) {
			e.printStackTrace();
			logger.error("failed makedir :{}", path);
		}
		return false;

	}

	public boolean delDir(String pathName) {
		if (!changeDirectory(pathName)) {
			return false;
		}

		try {
			Vector<LsEntry> vector = channelSftp.ls(channelSftp.pwd());

			if (vector != null) {
				for (LsEntry entry : vector) {

					String fileName = entry.getFilename();

					if (!".".equals(fileName) && !"..".equals(fileName)) {
						if (entry.getAttrs().isDir()) {
							delDir(fileName);
						} else if (entry.getAttrs().isReg()) {
							delFile(fileName);
						}
					}
				}
			}

			if (!changeParentDir()) {
				return false;
			}
			channelSftp.rmdir(pathName);
			return true;
		} catch (SftpException e) {
			e.printStackTrace();
			logger.error("del dir :{} failed", pathName);
		}
		return false;

	}

	public boolean changeParentDir() {
		return changeDirectory("..");
	}

	public boolean delFile(String fileName) {
		if (fileName == null || "".equals(fileName)) {
			return false;
		}

		try {
			channelSftp.rm(fileName);
			return true;
		} catch (SftpException e) {
			e.printStackTrace();
			logger.error("delFile  :{} failed", fileName);
		}
		return false;
	}

	/**
	 * 当前文件是否存在名称为name的文件或者 文件家
	 * 
	 * @param name
	 * @return
	 */
	public boolean exists(String name) {
		return exist(ls(), name);
	}

	public boolean exists(String path, String name) {
		return exist(ls(path), name);
	}

	public boolean existsDir(String name) {
		return exist(lsDirs(), name);
	}

	public boolean existsDir(String path, String name) {
		return exist(lsDirs(path), name);
	}

	public boolean existsFile(String name) {
		return exist(lsFiles(), name);
	}

	/**
	 * 指定目录下是否存在名称为name的文件
	 * 
	 * @param path
	 * @param name
	 * @return
	 */
	public boolean existsFile(String path, String name) {
		return exist(lsFiles(path), name);
	}

	/**
	 * 返回当前目录下所有文件夹和文件
	 * 
	 * @return
	 */
	public String[] ls() {
		return listFilter(Filter.ALL);
	}

	/**
	 * 返回当前目录下所有的<strong>文件夹<strong>
	 * 
	 * @return
	 */
	public String[] lsDirs() {
		return listFilter(Filter.DIR);
	}

	public String[] lsDirs(String path) {
		if (!changeDirectory(path)) {
			return defaultArray;
		}
		return listFilter(Filter.DIR);
	}

	/**
	 * 返回当前目录下所有的<strong>文件<strong>
	 * 
	 * @return
	 */
	public String[] lsFiles() {
		return listFilter(Filter.FILE);
	}

	/**
	 * 返回指定路径下所有的文件
	 * 
	 * @param pathName
	 * @return
	 */
	public String[] lsFiles(String pathName) {
		String currentDir = currentDir();

		String[] result = listFilter(Filter.FILE);

		if (!changeDirectory(currentDir)) {
			return new String[0];
		}
		return result;
	}

	/**
	 * 返回指定路径下所有的文件及文件夹
	 * 
	 * @param pathName
	 * @return
	 */
	public String[] ls(String pathName) {
		if (!changeDirectory(pathName)) {
			return new String[0];
		}
		String currentDir = currentDir();

		String[] result = listFilter(Filter.ALL);

		if (!changeDirectory(currentDir)) {
			return new String[0];
		}
		return result;
	}

	/**
	 * 全路径创建，排除文件名，只创建文件夹
	 * @param pathName
	 * @return
	 */
	public boolean mkDirFull(String pathName) {
		if (pathName == null || "".equals(pathName)) {
			return false;
		}
		if(pathName.startsWith("/")){
			pathName = pathName.substring(1);
			toRootDir();
		}

		if(pathName.lastIndexOf(".") > 0){
			pathName = pathName.substring(0, pathName.lastIndexOf("/"));
		}
		pathName = pathName.replaceAll("\\\\", "/");

		String [] path_array = pathName.split("\\/");

		for (String path : path_array) {
			if (existsDir(path)){
				changeDirectory(path);
				logger.debug("path:{}",currentDir());
				continue;
			}else{
				logger.debug("not exist path:{},mkdir new ",currentDir().concat("/").concat(path));
				if(mkdir(path)){
					changeDirectory(path);
				}
				
			}
				
		}
		return true;

	}

	/**
	 * 切换文件夹路径
	 * 
	 * TODO 切换文件夹时，所在的文件夹必须存在，不存在则报错,要进行判断
	 * 
	 * @param pathName
	 * @return
	 */
	public boolean changeDirectory(String pathName) {
		if (pathName == null || "".equals(pathName)) {
			return false;
		}
		try {
			channelSftp.cd(pathName.replaceAll("\\\\", "/"));
			logger.debug("directory successfully changed,current dir={}",
					channelSftp.pwd());
		} catch (SftpException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String[] listFilter(Filter filter) {

		Vector<LsEntry> lsvector = null;

		try {
			lsvector = channelSftp.ls(channelSftp.pwd());
			List<String> fileNames = new ArrayList<>();
			if (lsvector != null) {
				for (LsEntry entry : lsvector) {
					if (filter(entry, filter)) {
						fileNames.add(entry.getFilename());
					}
				}
			}
			return fileNames.toArray(new String[] {});
		} catch (SftpException e) {
			e.printStackTrace();
		}
		return new String[0];
	}
	
	/**
	 * 没有池 不能使用
	 * public String [] lists(final Filter filter){
		return this.execute(new JschOperation<String[]>() {
			@Override
			public String[] execute(ChannelSftp channel) {
				Vector<LsEntry> lsvector = null;
				try {
					lsvector = channelSftp.ls(channelSftp.pwd());
					List<String> fileNames = new ArrayList<>();
					if (lsvector != null) {
						for (LsEntry entry : lsvector) {
							if (filter(entry, filter)) {
								fileNames.add(entry.getFilename());
							}
						}
					}
					return fileNames.toArray(new String[] {});
				} catch (SftpException e) {
					e.printStackTrace();
				}
				return new String[0];
				//return channel.;
			}
		});
	}
	
	
	private <T>T execute(JschOperation<T> opertion){
		ChannelSftp channelSftp = null;
		T result = null;
		try {
			channelSftp = this.channel.getChannel();
			result = opertion.execute(channelSftp);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.close();
		}
		return result;
	}
	
	*//**
	 * jsch操作方法定义
	 * @author zhangyong
	 *
	 *//*
	private interface JschOperation<T>{
		T execute(ChannelSftp channel);
	}*/

	/** 枚举，用于过滤文件和文件夹 */
	private enum Filter {
		/** 文件及文件夹 */
		ALL, /** 文件 */
		FILE, /** 文件夹 */
		DIR
	};

	/***
	 * S_ISLNK(st_mode):是否是一个连接. S_ISREG是否是一个常规文件. S_ISDIR是否是一个目录
	 * S_ISCHR是否是一个字符设备. S_ISBLK是否是一个块设备 S_ISFIFO是否是一个FIFO文件.
	 * S_ISSOCK是否是一个SOCKET文件.
	 * 
	 * @return
	 */
	public boolean filter(LsEntry ls, Filter filter) {
		/*logger.debug("blk {},dir:{},reg:{},{},{},{},{}", ls.getAttrs().isBlk(),
				ls.getAttrs().isDir(), ls.getAttrs().isReg(), ls.getAttrs()
						.isFifo(), ls.getAttrs().isChr(), ls.getAttrs()
						.isLink());*/

		if (Filter.ALL.equals(filter)) {
			return !ls.getFilename().equals(".")
					&& !ls.getFilename().equals("..");
		} else if (Filter.DIR.equals(filter)) {
			return !ls.getFilename().equals(".")
					&& !ls.getFilename().equals("..") && ls.getAttrs().isDir();
		} else if (Filter.FILE.equals(filter)) {
			return !ls.getFilename().equals(".")
					&& !ls.getFilename().equals("..") && ls.getAttrs().isReg();
		}
		return true;

	}

	/**
	 * 当前路径
	 * 
	 * @return
	 */
	public String currentDir() {

		try {
			return channelSftp.pwd();
		} catch (SftpException e) {
			e.printStackTrace();
			logger.error("failed to get current dir", e);
			return toHomeDir();
		}

	}
	/**
	 * 返回系统根目录，root代表根  不是root用户
	 * @return
	 */
	public void toRootDir(){
		try {
			channelSftp.cd("/");
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 返回用户根目录
	 * 
	 * @return
	 */
	public String toHomeDir() {
		try {
			return channelSftp.getHome();
		} catch (SftpException e) {
			logger.error("change to homedir error,change to '/'", e);
			return "/";
		}
	}

	/**
	 * 判断指定文件数组中是否存在指定name的文件
	 * 
	 * @param files
	 * @param name
	 * @return
	 */
	public boolean exist(String[] files, String name) {

		if (files == null || files.length == 0 || name == null
				|| "".equals(name.trim())) {
			return false;
		}

		for (String file : files) {
			if (file.equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 登出
	 */
	public void close() {
		channel.closeChannel();
	}
}
