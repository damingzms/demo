package net.zhping.web.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.zhping.web.exception.ProcessException;
import net.zhping.web.repository.CustOrderEntityRepository;
import net.zhping.web.service.OrderService;

/**
 * 
 * @author SAM
 *
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	CustOrderEntityRepository custOrderEntityRepository;
	
	@Autowired
	CrmRegionEntityRepository crmRegionEntityRepository;

	@Override
	@Transactional(readOnly = true)
	public List<OrderVo> findOrderDeliveryInfo(OrderVo vo) throws ProcessException {
		return custOrderEntityRepository.findOrderDeliveryInfo(vo);
	}

	@Override
	public void updateReceiverInfo(OrderReceiverVo receiver) throws ProcessException {
		if (receiver.getOrderId() == null || receiver.getOrderId() == 0) {
			CustOrderEntity order = custOrderEntityRepository.findIdByOrderCode(receiver.getOrderCode());
			if (order == null) {
				throw new ProcessException("订单不存在。");
			}
			receiver.setOrderId(order.getId());
		}
		if (StringUtils.isNotBlank(receiver.getReceiverAddress())) {
			String[] regionCodes = crmRegionEntityRepository.findRegionCodeByAddress(receiver.getReceiverAddress());
			if (regionCodes == null || regionCodes.length != 3) {
				throw new ProcessException("收货人地址不正确。");
			}
			receiver.setProvinceCode(regionCodes[0]);
			receiver.setCityCode(regionCodes[1]);
			receiver.setDistrictCode(regionCodes[2]);
		}
		custOrderEntityRepository.updateReceiverInfo(receiver);
	}

}
