package com.alibaba.imt.servlet;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;

import com.alibaba.imt.config.AteyeServletConfig;
import com.alibaba.imt.constants.ManagerType;
import com.alibaba.imt.log.Log4jFacility;
import com.alibaba.imt.manager.AteyeManager;
import com.alibaba.imt.manager.AteyeManagerContext;
import com.alibaba.imt.manager.BaseManagerBeansUtil;
import com.alibaba.imt.util.ManagerLoggerUtil;

/**
 * ����
    <init-param>
        <param-name>isRecordErrorLogger</param-name>
        <param-value>true</param-value>
    </init-param>
 * @author leconte
 *
 */
public class AteyeServlet extends HttpServlet {
    private static final long serialVersionUID = -4382366812508979253L;
    private static Logger logger = Logger.getLogger("ateyeClient");
    private String webx2Config=null;
    public static Date startUpTime=new Date();

    @SuppressWarnings("unchecked")
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        request.setCharacterEncoding("GBK");
        response.setCharacterEncoding("gb2312");
        response.setContentType("application/json;charset=gb2312");
        PrintWriter out = response.getWriter();
        String type = request.getParameter("type");
        ManagerType managerType = ManagerType.valueOf(type);
        if (managerType != null) {
            AteyeManager manager = AteyeManagerContext.INSTANCE.getManagerMap().get(managerType);
            if (manager != null) {
                out.println(manager.service(convertParamMap(request.getParameterMap())));
            } else {
                logger.error("δ�ҵ������������:" + type + "��Manager");
            }
        } else {
            logger.error("��Ч����������Type:" + type);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }

    @Override
    public void init() 
    {
        long tic = System.currentTimeMillis();
        try{
            String uuid = UUID.randomUUID().toString();
            MDC.put("uuid", uuid);
            MDC.put("start", System.currentTimeMillis());
            //0.��ʼ��client��Logger
            Log4jFacility.addAteyeClientLogger();
            logger.info("AteyeClient Logger��ʼ���ɹ�");
            //1.servlet������ȡ
            getPara();
            
            //���ϵͳbootstrap��Logger
            PrintStream bootstrapLogger = ManagerLoggerUtil.getBootstrapLogger();
            
            //��Ϊ�Ƴ�init������ִ�У�����Trigger���֡�double������
            try
            {
                bootstrapLogger.println("AgentServlet��ʼ�����ߣ�2s��");
                TimeUnit.SECONDS.sleep(2);
                bootstrapLogger.println("AgentServlet��������");
            } 
            catch (InterruptedException e1) 
            {
                bootstrapLogger.println("AgentServlet��ʼ�����߱�interrupt");
            }
            
            //ȫ��bean
            Map<String, Object> beans =new HashMap<String, Object>();
            //webx2��ʶ����
            webx2Config=this.getInitParameter("webx2Config");
            //��ȡ����Spring��bean
            beans = BaseManagerBeansUtil.getAllBeans(this.getServletContext(), bootstrapLogger);
            logger.info("spring������bean����:"+beans.size());
    
            //ȷ��Ϊwebx2����
            if(StringUtils.isNotBlank(webx2Config))
            {
                Map<String, Object> allBeansFromWebx2 = BaseManagerBeansUtil.getAllBeansFromWebx2(webx2Config, bootstrapLogger);
                logger.info("webx2����,webx2������bean����:"+beans.size());
                beans.putAll(allBeansFromWebx2);
            }
            else
            {
                bootstrapLogger.println("��Webx2����");
            }
            //��ateye servlet��������Ϊһ��bean�������
            beans.put("___ateye_servlet_config___", new AteyeServletConfig());
            logger.info("�������bean:___ateye_servlet_config___");
            //��ϵͳbootstrap����־stream��Ϊ��Ч
            bootstrapLogger.flush();
            ManagerLoggerUtil.invalidBootstrapStream();
            
            AteyeManagerContext.INSTANCE.init(beans, this.getServletContext());
        }catch(Throwable t){
            logger.fatal("AteyeServlet��ʼ���쳣",t);
        }finally{
            logger.info("AteyeServlet��ʼ����������ʱ"+(System.currentTimeMillis()-tic)+"ms");
        }
        logger.info("AteyeServlet End");
        
    }

    private void getPara() {
        String isRecord = getInitParameter("isRecordErrorLogger");
        if ( isRecord != null && isRecord.equals("false")){
            AteyeServletConfig.isRecordErrorLogger = Boolean.FALSE;
            logger.warn("����:isRecordErrorLogger��ֵΪFalse���رմ�����־�ռ�");
        }
        String isRecordLog4j = getInitParameter("isRecordLog4j");
        if ( isRecordLog4j != null && isRecordLog4j.equals("false")){
            AteyeServletConfig.isRecordLog4j = Boolean.FALSE;
            logger.warn("����:isRecordLog4j��ֵΪFalse���ر�Log4j������־�ռ�");
        }
        AteyeServletConfig.appName = getInitParameter("app");
        if ( StringUtils.isBlank(AteyeServletConfig.appName) ){
            logger.warn("����appû�����ã����޷�ʹ��ĳЩ����");
            AteyeServletConfig.appName = "";
        }else{
            logger.info("����app:"+AteyeServletConfig.appName);
        }
        String isRecordSql = getInitParameter("recordSql");
        if ( isRecordSql != null && isRecordSql.equals("false")){
            AteyeServletConfig.isRecordSqlStat = Boolean.FALSE;
            logger.warn("����:recordSql��ֵΪFalse���ر�Sql���");
        }
        String kvTime = getInitParameter("kvTime");
        if ( kvTime != null ){
            Long kv = Long.valueOf(kvTime);
            AteyeServletConfig.setKvAwaitTime(kv);
            logger.warn("����:kvAwaitTime�޸�Ϊ:"+kvTime);
        }
    }
    
    private Map<String,String> convertParamMap(Map<String,String[]> oriQueryMap) {
        Map<String,String> map = new HashMap<String,String>();
        for(Map.Entry<String,String[]> entry : oriQueryMap.entrySet()) {
            if(entry.getValue().length > 0) {
                try {
                    /*
                     * �԰汾1.2.0-SNAPSHOT��ateyeclient�����������������һ��decode��Ӧ�÷�������decodeһ�Σ�����ateyeƽ̨���ڵ���ʱ�Բ�����������encode
                     * �����ַ������������ı��벻ͬ��ɵ���������
                     */
                    map.put(entry.getKey(), URLDecoder.decode(entry.getValue()[0], "GBK"));
                } catch (Exception e) {
                    logger.error(e);
                }
            } else {
                map.put(entry.getKey(), "");
            }
        }
        return map;
    }
}
