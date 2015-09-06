package com.mybatis.chapter5.spring.po;

import com.mybatis.chapter5.spring.po.base.BaseEntity;

/**
 * 
 * @author xl
 *
 */
public class MmgwFileUploadUrlEntity  extends BaseEntity{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7648540891975367806L;

	private String fileType;//文件类型
	
	private String storeUrl;//保存路径
	private String seqNum;//上传序号
	private String custId;//客户号
	private String preUUIDTxnId;//预申请号
	private String prodId;//产品号
	private String imageName;//图片名称
	
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getStoreUrl() {
		return storeUrl;
	}
	public void setStoreUrl(String storeUrl) {
		this.storeUrl = storeUrl;
	}
	public String getSeqNum() {
		return seqNum;
	}
	public void setSeqNum(String seqNum) {
		this.seqNum = seqNum;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getPreUUIDTxnId() {
		return preUUIDTxnId;
	}
	public void setPreUUIDTxnId(String preUUIDTxnId) {
		this.preUUIDTxnId = preUUIDTxnId;
	}
	public String getProdId() {
		return prodId;
	}
	public void setProdId(String prodId) {
		this.prodId = prodId;
	}
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	
	
}
