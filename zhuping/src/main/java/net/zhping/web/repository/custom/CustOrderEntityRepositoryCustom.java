package net.zhping.web.repository.custom;

import java.util.List;
import java.util.Map;

public interface CustOrderEntityRepositoryCustom {
	
	public Map<String, Long> findOrderIdsByExternalIds(List<String> externalIds);

}
