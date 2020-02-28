package com.trw.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("all")
public class BeanUtil {
	private final static Logger logger = LoggerFactory.getLogger(BeanUtil.class);
	 
	/**
	 * 对于继承com.pactera.icrm.saas.framecore.po.PersistantObject的po将不会拷贝createUserId,createDt
	 * @param desc
	 * @param orig
	 */
	public static void copyPropertiesNotForceWithoutCreateOperInfo(Object desc, Object orig) {
		String excludeFields = "createUserId,createDt";
		copyPropertiesNotForce(desc, orig, excludeFields, true);
	}
	
	/**
	 * 两对象之间的拷贝(在目标对象中存在的所有set方法，如果在源对象中存在对应的get方法，不管源对象的get方法的返回值是否为null,都进行拷贝)
	 * 仅拷贝方法名及方法返回类型完全一样的属性值
	 * @param desc 	目标对象
	 * @param orig 	源对象
	 * @param excludeFields 不拷贝的field（多个用逗号隔开）
	 */
	public static void copyPropertiesNotForce(Object desc, Object orig,String excludeFields) {
		copyPropertiesNotForce(desc, orig, excludeFields, true);
	}
	/**
	 * 两对象之间的拷贝(在目标对象中存在的所有set方法，如果在源对象中存在对应的get方法，不管源对象的get方法的返回值是否为null,都进行拷贝)
	 * 仅拷贝方法名及方法返回类型完全一样的属性值
	 * @param desc 	目标对象
	 * @param orig 	源对象
	 * @param includeFields 拷贝包含的field（多个用逗号隔开）
	 */
	public static void copyPropertiesNotForceInclude(Object desc, Object orig,String includeFields) {
		copyPropertiesNotForceInclude(desc, orig, includeFields, true);
	}
	/**
	 * 两对象之间的拷贝(在目标对象中存在的所有set方法，如果在源对象中存在对应的get方法，不管源对象的get方法的返回值是否为null,都进行拷贝)
	 * 仅拷贝方法名及方法返回类型完全一样的属性值
	 * @param desc 	目标对象
	 * @param orig 	源对象
	 * @param excludeFields 	源对象
	 * @param isCopyNull 	为null的属性是否拷贝(true拷贝null属性;false不拷贝null属性)
	 * @param excludeFields 不拷贝的field（多个用逗号隔开）
	 */
	public static void copyPropertiesNotForce(Object desc, Object orig,String excludeFields, boolean isCopyNull) {
		Class<?> origClass = orig.getClass();
		Class<?> descClass = desc.getClass();

		Method[] descMethods = descClass.getMethods();
		Method[] origMethods = origClass.getMethods();

		boolean needExclude = false;					//是否需要过滤部分字段
		if(StrKit.notBlank(excludeFields)){
			needExclude = true;
			excludeFields = "," + excludeFields.toLowerCase() + ",";
		}
		
		Map<String,Method> methodMap = new HashMap<String,Method>();
		for (int i = 0; i < origMethods.length; i++) {
			Method method = origMethods[i];
			String methodName = method.getName();
			if (!methodName.equals("getClass")
					&& methodName.startsWith("get")
					&& (method.getParameterTypes() == null || method
							.getParameterTypes().length == 0)) {
				if(needExclude && excludeFields.indexOf(","+(methodName.substring(3).toLowerCase())+",") > -1){
					continue;
				}
				methodMap.put(methodName, method);
			}
		}
		setVal(desc, orig, isCopyNull, descClass, descMethods, methodMap);
	}
	
	/**
	 * 两对象之间的拷贝(在目标对象中存在的所有set方法，如果在源对象中存在对应的get方法，不管源对象的get方法的返回值是否为null,都进行拷贝)
	 * 仅拷贝方法名及方法返回类型完全一样的属性值
	 * @param desc 	目标对象
	 * @param orig 	源对象
	 * @param excludeFields 	源对象
	 * @param isCopyNull 	为null的属性是否拷贝(true拷贝null属性;false不拷贝null属性)
	 * @param includeFields 需要拷贝的field（多个用逗号隔开）
	 */
	public static void copyPropertiesNotForceInclude(Object desc, Object orig,String includeFields, boolean isCopyNull) {
		Class<?> origClass = orig.getClass();
		Class<?> descClass = desc.getClass();
		
		Method[] descMethods = descClass.getMethods();
		Method[] origMethods = origClass.getMethods();
		
		boolean needInclude = false;					//是否需要包含部分字段
		if(StrKit.notBlank(includeFields)){
			needInclude = true;
			includeFields = "," + includeFields.toLowerCase() + ",";
		}
		
		Map<String,Method> methodMap = new HashMap<String,Method>();
		for (int i = 0; i < origMethods.length; i++) {
			Method method = origMethods[i];
			String methodName = method.getName();
			if (!methodName.equals("getClass")
					&& methodName.startsWith("get")
					&& (method.getParameterTypes() == null || method
					.getParameterTypes().length == 0)) {
				if(needInclude && includeFields.indexOf(","+(methodName.substring(3).toLowerCase())+",") > -1){
					methodMap.put(methodName, method);
				}
			}
		}
		setVal(desc, orig, isCopyNull, descClass, descMethods, methodMap);
	}
	
	protected static void setVal(Object desc, Object orig, boolean isCopyNull, Class<?> descClass, Method[] descMethods, Map<String, Method> methodMap) {
		for (int i = 0; i < descMethods.length; i++) {
			Method method = descMethods[i];
			String methodName = method.getName();
			if (!methodName.equals("getClass")
					&& methodName.startsWith("get")
					&& (method.getParameterTypes() == null || method
							.getParameterTypes().length == 0)) {
				if (methodMap.containsKey(methodName)) {
					Method origMethod = (Method) methodMap.get(methodName);
					try {
						if (method.getReturnType().equals(origMethod.getReturnType())) {
							Object returnObj = origMethod.invoke(orig, null);
							if(!isCopyNull && returnObj == null){
								continue;
							}
							String field = methodName.substring(3);
							String setMethodName = "set" + field;
							try {
								Method setMethod = descClass.getMethod(setMethodName, new Class[] { method.getReturnType() });
								setMethod.invoke(desc, new Object[] { returnObj });
							}catch(NoSuchMethodException e) {
								logger.error(e.getMessage());
							}
						}
					} catch (IllegalArgumentException e) {
						logger.error(e.getMessage());
					} catch (IllegalAccessException e) {
						logger.error(e.getMessage());
					} catch (InvocationTargetException e) {
						logger.error(e.getMessage());
					} catch (SecurityException e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
	}
	
	/**
	 * 两对象之间的拷贝(在目标对象中存在的所有set方法，如果在源对象中存在对应的get方法，不管源对象的get方法的返回值是否为null,都进行拷贝)
	 * 仅拷贝方法名及方法返回类型完全一样的属性值
	 * @param desc 	目标对象
	 * @param orig 	源对象
	 */
	public static void copyPropertiesNotForce(Object desc, Object orig) {
		copyPropertiesNotForce(desc, orig, null);
	}
	
	/**
	 * 两对象之间的拷贝(在目标对象中存在的所有set方法，如果在源对象中存在对应的get方法，源对象的get方法的返回值为null的不拷贝)
	 * 仅拷贝方法名及方法返回类型完全一样的属性值
	 * @param desc 	目标对象
	 * @param orig 	源对象
	 * @param excludeFields 不拷贝的field（多个用逗号隔开）
	 */
	public static void copyPropertiesNotNull(Object desc, Object orig) {
		copyPropertiesNotForce(desc, orig, null, false);
	}
	
	public static void copyPropertiesNotNull(Object desc, Object orig,String excludeFields) {
		copyPropertiesNotForce(desc, orig, excludeFields, false);
	}

	/**
	 * 比较两个Bean相同字段的值（以源对象的值为基准，json串中显示目标对象中的值）【仅比较可转换为string的类型】
	 * @param desc 目标对象
	 * @param orig 源对象		
	 * @return String 不一致的字段json串
	 * @throws  ServiceException
	 */
	public static String compareBeanProperties(Object desc, Object orig){
		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> origClass = orig.getClass();
		Class<?> descClass = desc.getClass();

		Method[] descMethods = descClass.getMethods();
		Method[] origMethods = origClass.getMethods();

		Map<String,Method> methodMap = new HashMap<String,Method>();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < origMethods.length; i++) {
			Method method = origMethods[i];
			String methodName = method.getName();
			if (!methodName.equals("getClass")
					&& methodName.startsWith("get")
					&& (method.getParameterTypes() == null || method
							.getParameterTypes().length == 0)) {
				methodMap.put(methodName, method);
			}
		}
		for (int i = 0; i < descMethods.length; i++) {
			Method method = descMethods[i];
			String methodName = method.getName();
			if (!methodName.equals("getClass")
					&& methodName.startsWith("get")
					&& (method.getParameterTypes() == null || method
							.getParameterTypes().length == 0)) {
				if (methodMap.containsKey(methodName)) {
					Method origMethod = (Method) methodMap.get(methodName);
					try {
						if (method.getReturnType().equals(origMethod.getReturnType())) {
							Object origObj = origMethod.invoke(orig, null);
							origObj = origObj == null ? "" : origObj;

							Method descMethod = descClass.getMethod(methodName,null);
							Object descObj = descMethod.invoke(desc, null);
							descObj = descObj == null ? "" : descObj;

							if (!origObj.equals(descObj)) {
								map.put(methodName.substring(3), descObj);
								sb.append(",{'field':'");
								sb.append(methodName.substring(3));
								sb.append("','msg':'");
								sb.append(descObj.toString().replaceAll("\'",
										""));
								sb.append("'}");
							}
						}
					} catch (IllegalArgumentException e) {
						logger.error(e.getMessage());
					} catch (IllegalAccessException e) {
						logger.error(e.getMessage());
					} catch (InvocationTargetException e) {
						logger.error(e.getMessage());
					} catch (SecurityException e) {
						logger.error(e.getMessage());
					} catch (NoSuchMethodException e) {
						logger.error(e.getMessage());
					}
				}
			}
		}
		String str = "[";
		if (sb.length() > 0) {
			str += sb.substring(1);
		}
		return str + "]";
	}
	  
	/**
	 * 通过字段名获取方法数组
	 * @param beanClass			Class<?>
	 * @param fieldNameArray	要输出的所有字段名数组
	 * @return				Method[]
	 */
	public static Method[] getMethods(Class<?> beanClass,String[] fieldNameArray){
		Method[] methodArray = new Method[fieldNameArray.length];
		
		String methodName;
		String fieldName;
		for (int i=0;i<fieldNameArray.length;i++) {
			Method method = null;
			fieldName = fieldNameArray[i];
			methodName = fieldName.substring(0,1).toUpperCase()+fieldName.substring(1);
			try {
				method = beanClass.getMethod("get"+methodName, null);
			} catch (SecurityException e) {
				logger.error(e.getMessage());
			} catch (NoSuchMethodException e) {
				try {
					method = beanClass.getMethod("is"+methodName, null);
				} catch (SecurityException e1) {
					logger.error(e1.getMessage());
				} catch (NoSuchMethodException e1) {
					logger.error(e1.getMessage());
				}
			}
			methodArray[i] = method;
		}
		
		return methodArray;
	}
	
	public static String quote(String s){
	    int slashEIndex = s.indexOf("\\E");
	    if (slashEIndex == -1)
	      return "\\Q" + s + "\\E";

	    StringBuffer sb = new StringBuffer(s.length() * 2);
	    sb.append("\\Q");
	    slashEIndex = 0;
	    int current = 0;
	    while ((slashEIndex = s.indexOf("\\E", current)) != -1) {
	      sb.append(s.substring(current, slashEIndex));
	      current = slashEIndex + 2;
	      sb.append("\\E\\\\E\\Q");
	    }
	    sb.append(s.substring(current, s.length()));
	    sb.append("\\E");
	    return sb.toString();
	}
	
	/**
	 * map转换成javabean
	 * @param map  需要转换的map
	 * @param beanClass 目标对象
	 * @return
	 * @throws Exception
	 */
	public static <T> T mapToObject(Map map, Class<T> beanClass){    
        if (map == null)   
            return null;    
        Object obj=null;
		try {
			obj = beanClass.newInstance();
			BeanUtils.populate(obj, map);
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
        return (T) obj;  
    }    
    
	/**
	 * bean转换成map
	 * @param obj
	 * @return
	 * @throws AppException
	 */
	public static <K, V> Map<K, V> objectToMap(Object obj){
		if(obj == null){  
            return null;  
        }          
		Map<K, V> ret = new HashMap<K, V>();
        try {  
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
            for (PropertyDescriptor property : propertyDescriptors) {  
                String fieldName = property.getName();  
                if(StrKit.equals("persistentState",fieldName)||
                		StrKit.equals("class",fieldName)) {
                	continue;
                }
                // 得到property对应的getter方法  
                Method getter = property.getReadMethod();  
                Object value = getter.invoke(obj);  
                ret.put((K) fieldName, (V) (null == value ? "" : value));
            }  
        } catch (Exception e) {  
        	logger.error("bean to  map  error", e);
        }  
        return ret;
//		try {
//			// 获取实体类的所有属性，返回Field数组 
//			Method[] methods = obj.getClass().getDeclaredMethods();
//	    	//超类字段信息
//			Method[] supMethods = obj.getClass().getSuperclass().getDeclaredMethods();
//	    	
//			Method[] allMethod=(Method[])CommUtil.arrayJoin(methods, supMethods);
//			for (Method method : allMethod) {
//				if (method.getName().startsWith("get")) {
//					String field = method.getName();
//					field = field.substring(field.indexOf("get") + 3);
//					field = field.toLowerCase().charAt(0) + field.substring(1);
//					Object value = method.invoke(obj, (Object[]) null);
//					ret.put((K) field, (V) (null == value ? "" : value));
//				}
//			}
//		} catch (Exception e) {
//			logger.error("bean to  map  error", e);
//			throw new AppException(StaticCode.ERROR_NORMAL,e);
//		}
//		return ret;
	}
	/**
	 * 将bean对象转换为Map<String, String>集合，属性值为空的属性将不会放入返回的集合中
	 * @param obj
	 * @return
	 * @throws AppException
	 */
	public static Map<String, String> objectToStringMap(Object obj)  {
		Map<String, String> ret = new HashMap<String, String>();
		try {
			Method[] methods = obj.getClass().getDeclaredMethods();
			for (Method method : methods) {
				if (method.getName().startsWith("get")) {
					String field = method.getName();
					field = field.substring(field.indexOf("get") + 3);
					field = field.toLowerCase().charAt(0) + field.substring(1);
					Object value = method.invoke(obj, (Object[]) null);
					if(value!=null){
					ret.put(field,value.toString());
					}
				}
			}
		} catch (Exception e) {
			logger.error("objectToStringMap  error", e);
		}
		return ret;
	}
	
	/**
	 * 对list去重复，T 需要重写equal和hashCode方法
	 * @param list
	 */
	public static <T> void removeDuplicateWithOrder(List<T> list) {
		Set<T> set = new HashSet<T>();
		List<T> newList = new ArrayList<T>();
		for (T element : list) {
			if (set.add(element))
				newList.add(element);
		}
		list.clear();
		list.addAll(newList);
	}
}
