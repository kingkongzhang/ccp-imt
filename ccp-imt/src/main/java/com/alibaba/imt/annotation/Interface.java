/**
 * Project: imt
 * 
 * File Created at 2012-9-18
 * $Id: Interface.java 471736 2013-03-06 08:55:11Z admin.for.perth $
 * 
 * Copyright 1999-2100 Alibaba.com Corporation Limited.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Alibaba Company. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Alibaba.com.
 */
package com.alibaba.imt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author �г�
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Interface {
	/**
	 * @Interface(datas = { 
	 * 		"data mi test",    		��������
	 * 		"product",				�Ƿ�����
	 * 		"Data Migration",   	����һ
	 * 		"correct trade data",	�����
	 * 		"param1" 				��һ������
	 * 		"param2"				�ڶ�������
	 * 		...						�����������޸�						
	 * 	})
	 * 
	 *  ��ע�ⷽʽ
	 */
	String[] datas();
}
