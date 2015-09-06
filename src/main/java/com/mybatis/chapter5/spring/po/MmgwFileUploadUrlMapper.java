package com.mybatis.chapter5.spring.po;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


//@Repository("mmgwFileUploadUrlDao")
public interface MmgwFileUploadUrlMapper {

	public void save(@Param("mmgwFile")MmgwFileUploadUrlEntity mmgwFile);
	
	public void update(@Param("mmgwFile")MmgwFileUploadUrlEntity mmgwFile);
	
	public List queryMmgwFileUrls(@Param("paramMap")Map<String,String> paramMap);
	
	public MmgwFileUploadUrlEntity queryMmgwFileUploadUrlByPK(@Param("pkId")Long pkId);
	
}
