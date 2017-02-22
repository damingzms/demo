package net.zhping.web.repository.custom.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;

import net.zhping.web.repository.custom.CustOrderEntityRepositoryCustom;

public class CustOrderEntityRepositoryImpl implements CustOrderEntityRepositoryCustom {
	
	private EntityManager em;
	
	public CustOrderEntityRepositoryImpl(EntityManager em) {
		this.em = em;
	}

	@Override
	public Map<String, Long> findOrderIdsByExternalIds(List<String> externalIds) {
		Map<String, Long> result = new HashMap<>();
		if (CollectionUtils.isEmpty(externalIds)) {
			return result;
		}
		StringBuilder sb = new StringBuilder("select o.id, o.externalOrderId from CustOrderEntity o where o.orderStatusCode <> 1 and o.orderStatusCode <> 2 and o.orderStatusCode <> 8 and o.externalOrderId in (");
		for (int i = 0; i < externalIds.size(); i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append("?" + (i + 1));
		}
		sb.append(")");
		Query query = em.createQuery(sb.toString());
		for (int i = 0; i < externalIds.size(); i++) {
			query.setParameter(i + 1, externalIds.get(i));
		}
		List<Object[]> list = query.getResultList();
		if (CollectionUtils.isNotEmpty(list)) {
			for (Object[] arr : list) {
				result.put((String) arr[1], (long) arr[0]);
			}
		}
		return result;
	}

}
