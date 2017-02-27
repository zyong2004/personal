package ftp.sftp.example;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.SftpProgressMonitor;

public class FileTransferProgressMonitor implements Runnable,
		SftpProgressMonitor {

	private static final Logger logger = LoggerFactory
			.getLogger(FileTransferProgressMonitor.class);
	private static long total = 0;
	private static final int interval_time = 5;// 5秒打印一次
	private Boolean end = false;

	private long fileSize;// 文件大小

	public FileTransferProgressMonitor() {
	}

	public FileTransferProgressMonitor(long fileSize) {
		this.fileSize = fileSize;
	}

	private Boolean schedualed = false;

	public void stop() {
		logger.debug("monitor stop()");
		setEnd(true);
		setSchedualed(false);
		if(service!=null){
			service.shutdown();
		}
		logger.debug("monitor stop() end");
	}

	private synchronized void add(long count) {
		total = total + count;
	}
	ScheduledExecutorService service ;
	
	public void start() {
		service =  Executors.newScheduledThreadPool(10);
		// service.scheduleAtFixedRate(new PrintThread("atFix"), 2,
		// interval_time, TimeUnit.SECONDS);
		service.scheduleWithFixedDelay(this, 2, interval_time, TimeUnit.SECONDS);
		schedualed = true;
	}

	@Override
	public void run() {
		if (!getEnd()) {
			logger.debug("Transfering is in progress");
			long tranferTotal = total;

			if (tranferTotal != fileSize) {
				logger.debug("Current transfed:{} byte", tranferTotal);
				sendProgressMessage(tranferTotal);
			} else {
				logger.debug("File has transfered all.");
				setEnd(true);
			}
		} else {
			System.out.println("Transfering done. Cancel timer.");
			stop(); // 如果传输结束，停止timer记时器
			return;
		}
	}

	@Override
	public void init(int op, String src, String dest, long max) {
		logger.debug("monitor init()");

	}

	@Override
	public boolean count(long count) {
		if (getEnd()) {
			return false;
		}
		if (!getSchedualed()) {
			start();
		}
		add(count);
		return true;
	}

	@Override
	public void end() {

		logger.debug("end()");
	}

	public Boolean getEnd() {
		return end;
	}

	public void setEnd(Boolean end) {
		this.end = end;
	}

	public Boolean getSchedualed() {
		return schedualed;
	}

	public void setSchedualed(Boolean schedualed) {
		this.schedualed = schedualed;
	}

	/**
	 * 打印progress信息
	 * 
	 * @param transfered
	 */
	private void sendProgressMessage(long transfered) {
		if (fileSize != 0) {
			double d = ((double) transfered * 100) / (double) fileSize;
			DecimalFormat df = new DecimalFormat("#.##");
			System.out.println("Sending progress message: " + df.format(d)
					+ "%");
		} else {
			System.out.println("Sending progress message: " + transfered);
		}
	}

}

class PrintThread implements Runnable {

	String name;
	private FileTransferProgressMonitor monitor;

	PrintThread(String name) {
		this.name = name;
	}

	PrintThread(FileTransferProgressMonitor monitor) {
		this.monitor = monitor;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(name + "--" + new Date() + " ----");
	}
}
