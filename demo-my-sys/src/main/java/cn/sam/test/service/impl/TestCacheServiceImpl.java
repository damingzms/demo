package cn.sam.test.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import cn.sam.test.service.TestCacheService;

@Service("testCacheService")
public class TestCacheServiceImpl implements TestCacheService {

	@Override
	public String getDateStr() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

}
