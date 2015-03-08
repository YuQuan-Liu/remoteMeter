package com.xdkj.yccb.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

@SuppressWarnings("unchecked")
public final class ReflectUtils {
	

	/**
	 * constructor
	 */
	private ReflectUtils() {
		super();
	}

	/**
	 * @param clazz class
	 * @param <T> <T>
	 * @return <T> result
	 *
	 */
	@SuppressWarnings("rawtypes")
	public static <T> Class<T> getClassGenricType(final Class clazz) {
		return getClassGenricType(clazz, 0);
	}

	/**
	 *
	 * @param clazz class
	 * @param index int index
	 * @return Class result
	 *
	 */
	@SuppressWarnings("rawtypes")
	public static Class getClassGenricType(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();
		if (!(genType instanceof ParameterizedType)) {
			return Object.class;
		}
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		if (index >= params.length || index < 0) {
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			return Object.class;
		}
		return (Class) params[index];
	}
}
