package com.alibaba.imt.config;

import com.alibaba.imt.annotation.Switch;

/**
 * ���ڿ���AteyeServlet����Ϊ��bean
 * ��Щ�������������޷��޸ģ�������Ϊ����
 * 
 * 
 * @author leconte
 *
 */
public class AteyeServletConfig {
    /*����*/
    public static Boolean isRecordErrorLogger = Boolean.TRUE;//�Ƿ��¼������־
    public static Boolean isRecordLog4j = Boolean.TRUE;//��¼log4j
    public static String appName = "";
    @Switch(description="�Ƿ��¼sql���ͳ��")
    public static Boolean isRecordSqlStat = Boolean.TRUE;//�Ƿ��¼sql��־���
    @Switch(description="�Ƿ���ڴ���")
    public static Boolean isOpenMemoryWatch = Boolean.TRUE;//�Ƿ���ڴ���
    @Switch(description="�����ڴ�����ʱ�Ĳ�������")
    public static Integer memSampleNumbers = 10000;//�����ڴ�����ʱ�Ĳ�������
    @Switch(description="KV���ɼ����(��λs)")
    private static Long kvAwaitTime = 60l;//KV���ʱ����дһ����־��Ĭ��1����
    @Switch(description="�Ƿ��KV�������(����,����)")
    public static Boolean isOpenKVDetail=false;//�Ƿ��KV�������

    
    public Boolean getIsRecordErrorLogger() {
        return isRecordErrorLogger;
    }
    public void setIsRecordErrorLogger(Boolean isRecordErrorLogger) {
        AteyeServletConfig.isRecordErrorLogger = isRecordErrorLogger;
    }
    public Boolean getIsRecordSqlStat() {
        return isRecordSqlStat;
    }
    public void setIsRecordSqlStat(Boolean isRecordSqlStat) {
        AteyeServletConfig.isRecordSqlStat = isRecordSqlStat;
    }
    public void setIsOpenMemoryWatch(Boolean isOpenMemoryWatch) {
        AteyeServletConfig.isOpenMemoryWatch = isOpenMemoryWatch;
    }
    public Boolean getIsOpenMemoryWatch() {
        return isOpenMemoryWatch;
    }
    public void setMemSampleNumbers(Integer memSampleNumbers) {
        AteyeServletConfig.memSampleNumbers = memSampleNumbers;
    }
    public Integer getMemSampleNumbers() {
        return memSampleNumbers;
    }
    public static Long getKvAwaitTime() {
        return kvAwaitTime;
    }
    public static void setKvAwaitTime(Long kv) {
        if ( kv == null || kv < 60l ){
            kv = 60l;
        }
        AteyeServletConfig.kvAwaitTime = kv ;
    }
    public void setIsOpenKVDetail(Boolean isOpenKVDetail) {
        AteyeServletConfig.isOpenKVDetail = isOpenKVDetail;
    }
    public Boolean getIsOpenKVDetail() {
        return isOpenKVDetail;
    }
    public Boolean getIsRecordLog4j() {
        return isRecordLog4j;
    }
    public void setIsRecordLog4j(Boolean isRecordLog4j) {
        AteyeServletConfig.isRecordLog4j = isRecordLog4j;
    }
}

