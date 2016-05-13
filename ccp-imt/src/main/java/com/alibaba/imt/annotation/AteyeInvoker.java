package com.alibaba.imt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.alibaba.imt.constants.InvokerType;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AteyeInvoker 
{   

    /**
     * �����Ĺ�������
     * @return
     */
    public String description() default "";
    /**
     * ������������Ϣ[�����û�в����ģ�����Ĭ��ֵ�մ�������в�����д��һ���ַ������м��÷ָ���&�ֿ�]
     * ���������������ķ���travel(String startPoint,String endPoint,Date time)
     * ���ӳɵ��ַ������磺���&�յ�&ʱ��
     * @return
     */
    public String paraDesc() default "";
    /**
     * �������ͣ���ֻ������д��Dailyֻ����Daily��д��
     * @return
     */
    public InvokerType type() default InvokerType.READ_ONLY;
}
