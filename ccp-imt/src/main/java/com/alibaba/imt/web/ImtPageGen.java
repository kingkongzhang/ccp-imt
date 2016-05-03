package com.alibaba.imt.web;

import static com.alibaba.imt.util.ResourceUtil.isContent;
import static com.alibaba.imt.util.ResourceUtil.isCssResource;
import static com.alibaba.imt.util.ResourceUtil.isImgResource;
import static com.alibaba.imt.util.ResourceUtil.isInitPage;
import static com.alibaba.imt.util.ResourceUtil.isJsResource;
import static com.alibaba.imt.util.ResourceUtil.isMethodInvoke;
import static com.alibaba.imt.util.ResourceUtil.renderCssResource;
import static com.alibaba.imt.util.ResourceUtil.renderImgResource;
import static com.alibaba.imt.util.ResourceUtil.renderJsResource;
import static com.alibaba.imt.util.ResourceUtil.renderMethodInvoke;
import static com.alibaba.imt.util.ResourceUtil.renderPage;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.List;

import org.springframework.context.ApplicationContext;

import com.alibaba.imt.InterfaceManagementTool;
import com.alibaba.imt.adapter.privileges.ImtPrivilege;
import com.alibaba.imt.bean.ImtGroup;
import com.alibaba.imt.support.spring.BeanUtils;

/**
 * ҳ�����ɹ���
 * @author hongwei.quhw
 *
 */
public class ImtPageGen {
	
	public static void process(ImtWebContext imtWebContext) throws IOException {
		initInterfaceManagementTool(imtWebContext);
		
		if (isInitPage(imtWebContext)) {
			//��ʼ��ҳ��
			initGroup(imtWebContext);
			renderPage(imtWebContext);
		} else if (isContent(imtWebContext)) {
			//��Ⱦ����ҳ��
			initContent(imtWebContext);
			renderPage(imtWebContext);	
		} else if (isMethodInvoke(imtWebContext)){
			//������
			renderMethodInvoke(imtWebContext);
		} else if (isJsResource(imtWebContext)) {
			//����js�ļ�
			renderJsResource(imtWebContext);
		} else if (isCssResource(imtWebContext)) {
			//����css�ļ�
			renderCssResource(imtWebContext);
		} else if (isImgResource(imtWebContext)) {
			//����ͼƬ
			renderImgResource(imtWebContext);
		} else {
			throw new RuntimeException("��������:" + imtWebContext);
		}
	}
	
	private static void initGroup(ImtWebContext imtWebContext) {
		imtWebContext.put("url", imtWebContext.getUrl());
		imtWebContext.put("encoding", imtWebContext.getEncoding());
		
		//��֤Ȩ��
		if(!authUser(imtWebContext)) {
			return;
		}
		
		InterfaceManagementTool tool = imtWebContext.getInterfaceManagementTool();
		tool.initGroups();
		
		imtWebContext.put("groups", tool.getImtGroups());
	}
	
	private static void initContent(ImtWebContext imtWebContext) {
		initGroup(imtWebContext);
		
		List<ImtGroup> imtGroups = imtWebContext.getInterfaceManagementTool().getImtGroups();
		imtWebContext.put("group", getGroupByUuid(imtGroups, imtWebContext.getUuid()));
		imtWebContext.put("uuid", imtWebContext.getUuid());
	}

	private static ImtGroup getGroupByUuid(List<ImtGroup> imtGroups, String uuid) {
		ImtGroup targetGroup = null;
		for (ImtGroup imtGroup : imtGroups) {
			if (uuid.equals(imtGroup.getUuid())) {
				targetGroup = imtGroup;
				break;
			}
			
			if (null != imtGroup.getNexts()) {
				targetGroup = getGroupByUuid(imtGroup.getNexts(), uuid);
			}
			
			if (null != targetGroup) {
				break;
			}
		}
		
		return targetGroup;
	}
	
	private static boolean authUser(ImtWebContext imtWebContext) {
		
		ImtPrivilege imtPrivilege = imtWebContext.getInterfaceManagementTool().getImtPrivilege();
		
		boolean authed = true;
		
		if (null != imtPrivilege) {
			authed = (imtPrivilege.authUser() || imtPrivilege.authUser(imtWebContext.getRequest()));
		} else { 
			//Ĭ�ϲ�У��
		}
		
		imtWebContext.put("authed", authed);
		
		return authed;
	}
	
	private static void initInterfaceManagementTool(ImtWebContext imtWebContext) {
		InterfaceManagementTool interfaceManagementTool = null;
		
		ApplicationContext applicationContext =  BeanUtils.findWebApplicationContext(imtWebContext.getServletContext(), imtWebContext.getContextAttribute());
	
		
		// �����Webx2,Ҫ��Webx2�������µ�Service
		//<service name="BeanFactoryService" class="com.alibaba.service.spring.DefaultBeanFactoryService">
        //  <property name="bean.descriptors">
        //    <value>classpath/applicationContext.xml</value>
        //  </property>
        //</service>
        if (null == applicationContext) {
            WeakReference ref = (WeakReference) imtWebContext.getServletContext().getAttribute("webx.controller");
            Object webController = ref.get();

            try {
                Class webControllerClass = webController.getClass();
                Method getServiceMangerMethod = webControllerClass.getMethod("getServiceManager", null);
                Object serviceManager = getServiceMangerMethod.invoke(webController, null);

                Class erviceManagerClass = serviceManager.getClass();
                Method getServiceMethod = erviceManagerClass.getMethod("getService", String.class);

                Object beanFactory = getServiceMethod.invoke(serviceManager, "BeanFactoryService");
                Class beanFactoryClass = beanFactory.getClass();
                Method getBeanFactory = beanFactoryClass.getMethod("getBeanFactory", null);
                applicationContext = (ApplicationContext) getBeanFactory.invoke(beanFactory, null);
            } catch (Exception e) {
                throw new RuntimeException("ApplicationContext ��ȡ����" + imtWebContext);
            }
        }
		
		if (null != applicationContext) {
			List<InterfaceManagementTool> tools = BeanUtils.getBeanByType(InterfaceManagementTool.class, applicationContext);
			if (null != tools && tools.size() > 1) {
				throw new RuntimeException("�����˶��InterfaceManagementTool�� ����");
			}
			
			interfaceManagementTool= tools.get(0);
		}
		
		if (null == interfaceManagementTool) {
			throw new RuntimeException("InterfaceManagementTool,Ŀǰ��֧�ֻ���spring�����ã������ĵȺ�!");
		}
		
		imtWebContext.setInterfaceManagementTool(interfaceManagementTool);
	}
}
