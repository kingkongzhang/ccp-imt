package com.alibaba.imt.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class AteyeOutputStream extends OutputStream
{
    //Ĭ���������ƣ�1M
    public static final int _DEFAULT_LIMIT = 1024*1024;
    public final int limit;
    public ByteArrayOutputStream target;
    private boolean valid = true;
    
    public AteyeOutputStream(ByteArrayOutputStream target)
    {
        this.target = target;
        limit = _DEFAULT_LIMIT;
    }
    
    public AteyeOutputStream(ByteArrayOutputStream target, int limit)
    {
        if(target==null||limit<=0)
            throw new IllegalArgumentException("target is null or limit is invalid.");
        this.target = target;
        this.limit = limit;
    }
    
    
    //�ڴ���־�Ĵ�С����Ϊ1M
    @Override
    public void write(int b) throws IOException
    {
        if(valid && target.size()<=limit)
        {
            target.write(b);
        }
    }
    public ByteArrayOutputStream getTarget() 
    {
        return target;
    }
    //����OutputStream��Ϊ��Ч������Ч��OutputSteam������������κν��
    public void invalid()
    {
        valid=false;
    }
}
