package com.alibaba.imt.adapter.privileges;

import javax.servlet.http.HttpServletRequest;

/**
 * Ȩ�޻ص������ڻ�ȡ��½�û�
 * Ӧ�÷���ʵ�ִ˽ӿ�
 * û��ʵ��imt����Ȩ�޿���
 * @author �г�
 *
 */
public interface ImtPrivilege {
	
	boolean authUser();
	
	boolean authUser(HttpServletRequest request);
}
