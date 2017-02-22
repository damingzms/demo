package net.zhping.web.util;

import java.util.Calendar;

public class SeqNoUtil {
	public static String generateSeqNo() {
		long time = Calendar.getInstance().getTime().getTime();
		long num = Math.round(Math.random() * 10000);
		String tail = String.format("%05d", num);
		return "seqno-" + time + tail;
	}
}
