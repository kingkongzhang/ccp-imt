package com.alibaba.imt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.alibaba.imt.util.ImtConstant;

/**
 * Imtע������ϵ�interface��ʽ
 * @author �г�
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Imt {
	/**
	 * ��������
	 * @return
	 */
	String mehtodDescrption();
	
	/**
	 * ���û������������ԣ�����
	 * @return
	 */
	String env() default ImtConstant.ENV_PRODUCT;
	
	/**
	 * ����
	 * @return
	 */
	String[] group() default {};
	
	/**
	 * ������������
	 * @return
	 */
	String[] paramDescrption() default {};
}
