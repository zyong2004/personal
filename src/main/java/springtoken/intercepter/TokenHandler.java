package springtoken.intercepter;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenHandler {

	private static Logger LOG = LoggerFactory.getLogger(TokenHandler.class);
	static Map<String,String> tokenMap = new HashMap<String,String>(64);
	static String SESSION_TOKENT_ATTR ="session.token.attr";
	static String DEFAULT_TOKEN_NAME="springmvc.token";
	static String DEFAULT_TOKEN_FILED="springmvc.token.name";
	
	public static String getToken(HttpSession session){
		
		return (String) session.getAttribute(DEFAULT_TOKEN_NAME);
	}
	
	@SuppressWarnings("unchecked")
	public synchronized static String generateUUID(HttpSession session){
		
		String token ="";
		
		Object obj = session.getAttribute(SESSION_TOKENT_ATTR);
	
		if(obj !=null){
			LOG.info(obj.toString());
			tokenMap = (Map<String, String>) obj;
			
		}else{
			LOG.info("get null token list from session");
		}
		token = new BigInteger(165, new Random()).toString();
		tokenMap.put(DEFAULT_TOKEN_NAME+"."+token, token);
		session.setAttribute(SESSION_TOKENT_ATTR, tokenMap);
		session.setAttribute(DEFAULT_TOKEN_NAME, token);
		return token;
	}
	
	
	@SuppressWarnings("unchecked")
	public static boolean validToken(HttpServletRequest req){
		String inputTokenName = getInputTokenName(req);
		String inputToken = getInputToken(req);
		if(inputTokenName == null){
			LOG.warn("token is not valid!inputToken is NULL");
			return false;
		}
		LOG.debug(inputTokenName);
		HttpSession session = req.getSession();
		
		Map<String,String> tokenMap_i = (Map<String, String>) session.getAttribute(SESSION_TOKENT_ATTR);
		if(tokenMap_i!=null){
			String sessionToken = tokenMap_i.get(DEFAULT_TOKEN_NAME+"."+inputToken);
			String token = getToken(inputTokenName, req);
			
			if(inputToken.equals(sessionToken)){
				tokenMap.remove(DEFAULT_TOKEN_NAME+"."+inputToken);
				session.setAttribute(SESSION_TOKENT_ATTR, tokenMap_i);
				return true;
			}else{
				LOG.warn("token is not valid!inputToken='" + inputToken
	                    + "',sessionToken = '" + sessionToken + "'");
			}
		}else{
			LOG.warn("token is not valid!sessionToken is NULL");
			return false;
		}
		return false;
	}


	private static String getInputToken(HttpServletRequest req){
		Map<String, String[]> reqParam = req.getParameterMap();
		
		if(!reqParam.containsKey(DEFAULT_TOKEN_NAME)){
			LOG.warn("Could not find token name in params.");
			return null;
		}
		String [] tokens = reqParam.get(DEFAULT_TOKEN_NAME);
		if(tokens == null || tokens.length<1){
			LOG.warn("get a null or empty token name");
			return null;
		}
		return tokens[0];
	}
	
	
	private static String getInputTokenName(HttpServletRequest req) {
		
		Map<String, String[]> reqParam = req.getParameterMap();
		
		if(!reqParam.containsKey(DEFAULT_TOKEN_FILED)){
			LOG.warn("Could not find token name in params.");
			return null;
		}
		String [] tokens = reqParam.get(DEFAULT_TOKEN_FILED);
		if(tokens == null || tokens.length<1){
			LOG.warn("get a null or empty token name");
			return null;
		}
		return tokens[0];
	}
	
	
	private static String getToken(String tokenName,HttpServletRequest req){
		
		if(tokenName == null){
			return null;
		}
		
		Map<String, String[]> reqParam = req.getParameterMap();
		
		if(!reqParam.containsKey(tokenName)){
			LOG.warn("Could not find token  in params. the tokenName"+tokenName);
			return null;
		}
		String [] tokens = reqParam.get(tokenName);
		if(tokens == null || tokens.length<1){
			LOG.warn("get a null or empty token  . the tokenName"+tokenName);
			return null;
		}
		return tokens[0];
		
		
	}
	
}
