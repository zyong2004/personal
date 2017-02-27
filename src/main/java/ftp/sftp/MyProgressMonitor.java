package ftp.sftp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.SftpProgressMonitor;

public class MyProgressMonitor implements SftpProgressMonitor {

	private long total;
	private static final Logger logger = LoggerFactory.getLogger(MyProgressMonitor.class);
	@Override
	public void init(int op, String src, String dest, long max) {

		logger.debug("init()");
		
	}

	@Override
	public boolean count(long count) {
		total += count;
        logger.debug("Currently transferred total size: {} byte" ,total);
		return true;
	}

	@Override
	public void end() {
		logger.debug("end()");

	}

}
