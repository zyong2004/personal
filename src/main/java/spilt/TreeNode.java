package spilt;

import java.util.Map;

import org.hsqldb.lib.HashMap;

/**
 * 构建内存词典的TreeNode树结点
 *       
 */
public class TreeNode {

	public TreeNode() {
		// TODO Auto-generated constructor stub
	}
	/** 结点关键字，其值为中文词中的一个字 */
	public char key='\0';
	 /** 如果该字在词语的末尾，则bound=true */
	public boolean bound =true;
	/** 指向下一个结点的指针结构，用来存放当前字在词中的下一个字的位置 */
	public Map<Character, TreeNode> child = (Map<Character, TreeNode>) new HashMap();
	
	
	
	public TreeNode(char key){
		this.key = key;
	}
}
