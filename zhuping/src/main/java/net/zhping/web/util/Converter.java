package net.zhping.web.util;

import org.apache.poi.ss.usermodel.Cell;

/**
 * 
 * @author SAM
 *
 */
public interface Converter {

	public Object convertValue(Cell cell,Object value);
}
