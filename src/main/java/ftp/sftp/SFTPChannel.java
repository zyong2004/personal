package ftp.sftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Proxy;
import com.jcraft.jsch.ProxyHTTP;
import com.jcraft.jsch.ProxySOCKS4;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SocketFactory;

/**
 * 没有缓存池，每次都是新的对象
 * 不能使用单例
 * @author zhangyong
 *
 */
public class SFTPChannel {

	
	Session session = null;

	Channel channel = null;
	
	public static final int default_timeOut = 20000;

	private static final Logger logger = LoggerFactory
			.getLogger(SFTPChannel.class);

	public ChannelSftp getChannel() {
		return getSftpChannel(default_timeOut);
	}
	/**
	 * 获取sftpChannel
	 * 
	 * @param timeOut
	 * @return
	 */
	public ChannelSftp getSftpChannel(int timeOut) {
		return (ChannelSftp) getChannel(SftpConstant.hosts, SftpConstant.port,
				SftpConstant.userName, SftpConstant.passwd, timeOut, null,
				SftpConstant.OPEN_TYPE);
	}

	/**
	 * 设置代理
	 * 
	 * @param type
	 * @return
	 */
	public Proxy getProxy(String type) {
		String proxyHost = null;
		int proxyPort = 0;
		if ("http".equals(type)) {
			return new ProxyHTTP(proxyHost, proxyPort);
		} else if ("socks4".equals(type)) {
			return new ProxySOCKS4(proxyHost, proxyPort);
		} else if ("socks5".equals(type)) {
			return new ProxySOCKS4(proxyHost, proxyPort);
		}
		return null;
	}

	/**
	 * 
	 * @param host
	 *            主机
	 * @param port
	 *            端口
	 * @param userName
	 *            用户
	 * @param password
	 *            密码
	 * @param timeOut
	 *            超时时间
	 * @param proxy
	 *            代理类型
	 * @param connectType
	 *            连接类型
	 * @return
	 */
	public Channel getChannel(String host, Integer port, String userName,
			String password, Integer timeOut, String proxy, String connectType) {
		int defaultPort = SftpConstant.port;
		if (port != null && !"".equals(port)) {
			defaultPort = Integer.valueOf(port);
		}
		try {
			session = getSession(userName, host, defaultPort,password, timeOut, proxy);
			logger.debug("session connected");
			logger.debug("open channel start");
			
			channel = session.openChannel(connectType);

			channel.connect(timeOut == null ? default_timeOut : timeOut);

			logger.debug("connect successfully to host:{},port:{},user:{}",
					host, port, userName);

			return channel;

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("open channel error", e);
		}
		return null;
	}

	/**
	 * 
	 * @param userName
	 * @param host
	 * @param defaultPort
	 * @param String password
	 * @return
	 * @throws JSchException
	 */
	public Session getSession(String userName, String host, int defaultPort,String password,
			Integer timeOut, String proxy) throws JSchException {
		JSch jsch = new JSch();
		session = jsch.getSession(userName, host, defaultPort);
		//session.setProxy(getProxy(proxy));
		session.setConfig(SftpConstant.ig_config, "no");
		session.setTimeout(default_timeOut);
		session.setPassword(password);
		session.connect(timeOut == null ? default_timeOut : timeOut);
		return session;
	}

	/**
	 * 创建sftp<br/>
	 * 当参数中包含代理服务器和端口时，使用代理
	 * 
	 * @param sftpDetail
	 * @param timeOut
	 * @return
	 */
	public ChannelSftp getChannel(Map<String, String> sftpDetail, int timeOut) {

		String sftpHost = sftpDetail.get(SftpConstant.SFTP_HOST);
		String sftpPort = sftpDetail.get(SftpConstant.SFTP_PORT);
		String sftpUser = sftpDetail.get(SftpConstant.SFTP_USER);
		String sftppasswd = sftpDetail.get(SftpConstant.SFTP_PASSWD);
		int port = SftpConstant.port;

		if (sftpPort != null && !"".equals(sftpPort)) {
			port = Integer.valueOf(sftpPort);
		}
		JSch jsch = new JSch();
		try {
			session = jsch.getSession(sftpUser, sftpHost, port);
			logger.debug("session created");
			session.setConfig(SftpConstant.ig_config, "no");
			session.setPassword(sftppasswd);

			session.setTimeout(timeOut);
			session.connect();
			logger.debug("session connected");

			logger.debug("open channel start");

			channel = session.openChannel(SftpConstant.OPEN_TYPE);

			channel.connect(2000);

			logger.debug("connect successfully to host:{},port:{},user:{}",
					sftpHost, port, sftpUser);

			return (ChannelSftp) channel;

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public void closeChannel() {
		if (channel != null) {
			channel.disconnect();
		}
		if (session != null) {
			session.disconnect();
		}
	}

	@Deprecated 
	//TODO 暂未使用
	public Session getSocketChannel(Map<String, String> sftpDetail) {
		JSch jsch = new JSch();
		String sftpHost = sftpDetail.get(SftpConstant.SFTP_HOST);
		String sftpPort = sftpDetail.get(SftpConstant.SFTP_PORT);
		String sftpUser = sftpDetail.get(SftpConstant.SFTP_USER);
		String sftppasswd = sftpDetail.get(SftpConstant.SFTP_PASSWD);
		int port = SftpConstant.port;

		if (sftpPort != null && !"".equals(sftpPort)) {
			port = Integer.valueOf(sftpPort);
		}
		try {
			session = jsch.getSession(sftpUser, sftpHost, port);
			logger.debug("session created");
			session.setConfig(SftpConstant.ig_config, "no");
			SocketFactory socketFactory = new SocketFactoryImpl();
			session.setSocketFactory(socketFactory);
			session.setPassword(sftppasswd);
			// session.setTimeout(timeOut);
			session.connect();
		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return session;

	}
}

class SocketFactoryImpl implements SocketFactory {

	@Override
	public Socket createSocket(String host, int port) throws IOException,
			UnknownHostException {
		Socket socket = new Socket(host, port);
		return socket;
	}

	@Override
	public InputStream getInputStream(Socket socket) throws IOException {
		return socket.getInputStream();
	}

	@Override
	public OutputStream getOutputStream(Socket socket) throws IOException {
		return socket.getOutputStream();
	}

}