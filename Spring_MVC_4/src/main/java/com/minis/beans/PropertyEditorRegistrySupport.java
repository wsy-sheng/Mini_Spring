package com.minis.beans;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PropertyEditorRegistrySupport {
	private Map<Class<?>, PropertyEditor> defaultEditors;
	private Map<Class<?>, PropertyEditor> customEditors;

	public PropertyEditorRegistrySupport() {
		registerDefaultEditors();
	}

	/**
	 * 注册默认的转换器editor
	 */
	protected void registerDefaultEditors() {
		createDefaultEditors();
	}

	/**
	 * 获取默认的转换器editor
	 */
	public PropertyEditor getDefaultEditor(Class<?> requiredType) {
		return this.defaultEditors.get(requiredType);
	}

	/**
	 * 创建默认的转换器editor，对每一种数据类型规定一个默认的转换器
	 */
	private void createDefaultEditors() {
		this.defaultEditors = new HashMap<>(64);

		// Default instances of collection editors.
		this.defaultEditors.put(int.class, new CustomNumberEditor(Integer.class, false));
		this.defaultEditors.put(Integer.class, new CustomNumberEditor(Integer.class, true));
		this.defaultEditors.put(long.class, new CustomNumberEditor(Long.class, false));
		this.defaultEditors.put(Long.class, new CustomNumberEditor(Long.class, true));
		this.defaultEditors.put(float.class, new CustomNumberEditor(Float.class, false));
		this.defaultEditors.put(Float.class, new CustomNumberEditor(Float.class, true));
		this.defaultEditors.put(double.class, new CustomNumberEditor(Double.class, false));
		this.defaultEditors.put(Double.class, new CustomNumberEditor(Double.class, true));
		this.defaultEditors.put(BigDecimal.class, new CustomNumberEditor(BigDecimal.class, true));
		this.defaultEditors.put(BigInteger.class, new CustomNumberEditor(BigInteger.class, true));
		this.defaultEditors.put(String.class, new StringEditor(String.class, true));

	}

	/**
	 * 注册客户化转换器
	 */
	public void registerCustomEditor( Class<?> requiredType,  PropertyEditor propertyEditor) {
		if (this.customEditors == null) {
			this.customEditors = new LinkedHashMap<>(16);
		}
		this.customEditors.put(requiredType, propertyEditor);
	}

	/**
	 * 查找客户化转换器
	 */
	public PropertyEditor findCustomEditor( Class<?> requiredType) {
		Class<?> requiredTypeToUse = requiredType;
		return getCustomEditor(requiredTypeToUse);
	}

	public boolean hasCustomEditorForElement( Class<?> elementType) {
		// No property-specific editor -> check type-specific editor.
		return (elementType != null && this.customEditors != null && this.customEditors.containsKey(elementType));
	}

	/**
	 * 获取客户化转换器
	 */
	public PropertyEditor getCustomEditor( Class<?> requiredType) {
		if (requiredType == null || this.customEditors == null) {
			return null;
		}
		// Check directly registered editor for type.
		PropertyEditor editor = this.customEditors.get(requiredType);

		return editor;
	}

}
