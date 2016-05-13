package com.alibaba.imt.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.imt.constants.ManagerType;

public class ManagerLoggerUtil
{
    //��־�������ƣ�1M
    public static final int _MEM_LOG_LIMIT = 1024*1024;
    
    //����Manager����־�����
    public static final Map<ManagerType,AteyeOutputStream> streamMap = new HashMap<ManagerType,AteyeOutputStream>();
    //Bootstrap����־�����
    private static AteyeOutputStream bootstrapStream = null;
    //����Manager��������־
    public static final Map<ManagerType,String> initLogs = new HashMap<ManagerType,String>();
    //ϵͳ������־�����bean������־��
    public static String bootstrapLog = null;
    
    //���һ��Manager��������־Logger
    public static PrintStream getManagerLogger(ManagerType managerType)
    {
        AteyeOutputStream ateyeOutputStream = new AteyeOutputStream(new ByteArrayOutputStream(), _MEM_LOG_LIMIT);
        streamMap.put(managerType, ateyeOutputStream);
        return new PrintStream(ateyeOutputStream, true);
    }
    
    
    //���ϵͳ������־Logger
    public static synchronized PrintStream getBootstrapLogger()
    {
        if(bootstrapStream ==null)
        {
            bootstrapStream = new AteyeOutputStream(new ByteArrayOutputStream(), _MEM_LOG_LIMIT);
        }
        return new PrintStream(bootstrapStream, true);
    }
    
    //��ϵͳ������־Logger��Ϊ��Ч
    public static void invalidBootstrapStream()
    {
        if(bootstrapStream!=null)
        {
            bootstrapStream.invalid();
            bootstrapLog = new String(bootstrapStream.getTarget().toByteArray()).replaceAll("\n", "<br/>");
            //�ͷſռ�
            bootstrapStream.target = null;
            bootstrapStream = null;
        }
    }
    
    //��һ��Manager��Logger��Ϊ��Ч
    public static void invalidInitLogger(ManagerType managerType)
    {
        AteyeOutputStream ateyeOutputStream = streamMap.remove(managerType);
        if(ateyeOutputStream!=null)
        {
            ateyeOutputStream.invalid();
            initLogs.put(managerType, new String(ateyeOutputStream.getTarget().toByteArray()).replaceAll("\n", "<br/>"));
            //�ͷſռ�
            ateyeOutputStream.target = null;
        }
    }
    
    //��Throwable��ӡΪString
    public static String convertThrowableToString(Throwable t)
    {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        PrintWriter out=new PrintWriter(bo);
        t.printStackTrace(out);
        out.close();
        return new String(bo.toByteArray()).replaceAll("\n", "<br/>");
    }
}
