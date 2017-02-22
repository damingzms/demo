package net.zhping.web.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author SAM
 *
 */
public final class JsonUtil {

	private static JsonFactory jsonFactory = new JsonFactory();

	private static ObjectMapper mapper = null;

	static {
		jsonFactory.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		jsonFactory.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper = new ObjectMapper(jsonFactory);
	}

	/**
	 * 获取jackson json lib的ObjectMapper对象
	 * 
	 * @return -- ObjectMapper对象
	 */
	public static ObjectMapper getMapper() {
		return mapper;
	}

	/**
	 * 获取jackson json lib的JsonFactory对象
	 * 
	 * @return -- JsonFactory对象
	 */
	public static JsonFactory getJsonFactory() {
		return jsonFactory;
	}

	/**
	 * 将json转成java bean
	 * 
	 * @param <T>
	 *            -- 多态类型
	 * @param json
	 *            -- json字符串
	 * @param clazz
	 *            -- java bean类型(Class)
	 * @return -- java bean对象
	 */
	public static <T> T toBean(String json, Class<T> clazz) {

		T rtv = null;
		try {
			rtv = mapper.readValue(json, clazz);
		} catch (Exception ex) {
			throw new RuntimeException("json字符串转成java bean异常", ex);
		}
		return rtv;
	}

	/**
	 * 把字符串的集合对象转json
	 * 
	 * @param <A>
	 * 
	 * @param json
	 *            -- json字符串
	 * @param listClass
	 *            集合类
	 * @param beanClass
	 *            集合对象类
	 * @return
	 */
	public static <T> T toBean(String json, Class<T> parametrized, Class<?>... parameterClasses) {
		T rtv = null;
		try {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(parametrized, parameterClasses);
			rtv = mapper.readValue(json, javaType);
		} catch (Exception ex) {
			throw new RuntimeException("json字符串转成java bean异常", ex);
		}
		return rtv;
	}

	/**
	 * 将java bean转成json
	 * 
	 * @param bean
	 *            -- java bean
	 * @return -- json 字符串
	 */
	public static String toJson(Object bean) {

		String rtv = null;
		try {
			rtv = mapper.writeValueAsString(bean);
		} catch (Exception ex) {
			throw new RuntimeException("java bean转成json字符串异常", ex);
		}
		return rtv;
	}
}
