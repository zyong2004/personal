package security;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ocx.AESWithJCE;

public class AESWithJCE1 {
private static Logger log = LoggerFactory.getLogger(AESWithJCE1.class);
	
	ocx.AESWithJCE aes= new ocx.AESWithJCE();
	public static void main(String[] args) throws UnsupportedEncodingException {
		 String key = "gx8j0xxacjnjh5ehxkppxnyzlxvaxxma";
		    String data = "ElubkhLyjLjrp23vVcXRpw==";
		    if (data.contains(" ")) {
		      data = data.replaceAll(" ", "+");
		    }

		System.out.println(new String(AESWithJCE.getResult(key, data).getBytes()));
			byte [] message = "lllllll".getBytes();
		 	int length = message.length;
		 	
		 	System.out.println(length);
		 	
		 	log.info("{}",length);
		 	
		    int numOfBlocks = length / 16;
		    
		    log.info("{}",numOfBlocks);
		    
		    int lengthOfLastPart = length - numOfBlocks * 16;
		    
		    log.info("{}",lengthOfLastPart);
		    System.out.println(lengthOfLastPart);
	}
}

