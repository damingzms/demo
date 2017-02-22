package net.zhping.web.service;

import java.util.List;

import net.zhping.web.exception.ProcessException;

/**
 * 
 * @author SAM
 *
 */
public interface OrderService {
	
	public List<OrderVo> findOrderDeliveryInfo(OrderVo vo) throws ProcessException;
	
	public void updateReceiverInfo(OrderReceiverVo receiver) throws ProcessException;

}
