package net.zhping.web.util.excel;

import net.zhping.web.util.Converter;

/**
 * 
 * @author SAM
 *
 */
public class ExcelMapping {
	
	private String name;
	
	/** 列索引 0开始 */
	private int cloumnIndex;
	
	/** 对应javaBean中属性的类型 */
	private Class<?> clazz = null;
	
	/** 转换器 */
	private Converter converter = null;
	
	private boolean nullable = true;
	
	private Integer lengthMax;
	
	private Integer lengthMin;
	
	private Long max;
	
	private Long min;
	
	/**
	 * 整数位数
	 */
	private Integer integer;
	
	/**
	 * 小数位数
	 */
	private Integer fraction;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCloumnIndex() {
		return cloumnIndex;
	}

	public void setCloumnIndex(int cloumnIndex) {
		this.cloumnIndex = cloumnIndex;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public Converter getConverter() {
		return converter;
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public Integer getLengthMax() {
		return lengthMax;
	}

	public void setLengthMax(Integer lengthMax) {
		this.lengthMax = lengthMax;
	}

	public Integer getLengthMin() {
		return lengthMin;
	}

	public void setLengthMin(Integer lengthMin) {
		this.lengthMin = lengthMin;
	}

	public Long getMax() {
		return max;
	}

	public void setMax(Long max) {
		this.max = max;
	}

	public Long getMin() {
		return min;
	}

	public void setMin(Long min) {
		this.min = min;
	}

	public Integer getInteger() {
		return integer;
	}

	public void setInteger(Integer integer) {
		this.integer = integer;
	}

	public Integer getFraction() {
		return fraction;
	}

	public void setFraction(Integer fraction) {
		this.fraction = fraction;
	}
	
}
