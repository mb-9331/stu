package com.jt.order.job;

import java.util.Date;

import org.joda.time.DateTime;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.jt.order.mapper.OrderMapper;

public class PaymentOrderJob extends QuartzJobBean{

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//删除2天天的恶意订单(修改状态status)
		ApplicationContext applicationContext = 
				(ApplicationContext) context.getJobDetail().getJobDataMap().get("applicationContext");
		OrderMapper orderMapper = 
				applicationContext.getBean(OrderMapper.class);
		//DateTime().minusDays(2).toDate()  当前时间减去2天
		Date dateAge = new DateTime().minusDays(2).toDate();
		orderMapper.updateStatus(dateAge);
		System.out.println("定时任务执行完成！");
		
		
		//通过spring容器调用Order接口文件执行方法
		//applicationContext.getBean(OrderMapper.class).paymentOrder(new DateTime().minusDays(2).toDate());
	}
	
	
}
