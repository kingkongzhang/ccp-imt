package com.alibaba.imt.adapter.privileges;

import javax.servlet.http.HttpServletRequest;

/**
 * Ĭ��ʵ��Ȩ�޽ӿ�
 * @author �г� [2015��5��16�� ����6:01:24]
 */
public class AbstractImtPrivilege implements ImtPrivilege{

	@Override
	public boolean authUser() {
		return false;
	}

	@Override
	public boolean authUser(HttpServletRequest request) {
		return false;
	}

}
