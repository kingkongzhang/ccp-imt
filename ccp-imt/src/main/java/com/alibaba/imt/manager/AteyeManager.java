package com.alibaba.imt.manager;

import java.io.PrintStream;
import java.util.Map;

import javax.servlet.ServletContext;

import com.alibaba.imt.constants.ManagerType;

public interface AteyeManager
{
    /**
     * Manager��ʵ�ʲ�������
     * @param queryParams ���������Map
     * @return ���صĲ�������ַ���
     */
    public String service(Map<String,String> queryParams);
    
    /**
     * Manager�ĳ�ʼ������������Servlet�������Ļ���
     * @param servletContext Servlet�����Ļ���
     */
    public void init(ServletContext servletContext, PrintStream initLogger,Map<String, Object> beans);
    
    /**
     * ��ʾ��Manager��ҵ�����ͣ�����ö������
     * @return ManagerType.SWITCH | ManagerType.QUARTZ | ManagerType.LOGCONTROL
     */
    public ManagerType getType();
}
