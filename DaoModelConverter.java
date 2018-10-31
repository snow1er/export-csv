package com.arcserve.jpa.base.contract;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.arcserve.jpa.base.annotation.DaoConvertField;
import com.arcserve.jpa.base.annotation.DaoConvertSkipField;

/**
 * 
 * @author neo.li
 *
 */
public class DaoModelConverter {

	private static Logger logger = Logger.getLogger(DaoModelConverter.class);
	
	enum ConvertRule{
		
		NORULE {
			@Override
			void doConvert(Object sourceObj, Object targetObj,
					Field sourceField, Field targetField) throws Exception {
				targetField.set(targetObj, sourceField.get(sourceObj));
			}
		},
		
		INT_TO_ENUM {
			@Override
			void doConvert(Object sourceObj, Object targetObj,
					Field sourceField, Field targetField) throws Exception {
				int fieldValue = (Integer)sourceField.get(sourceObj);
				int enumSize = targetField.getType().getEnumConstants().length;
		        if(fieldValue >= enumSize || fieldValue < 0) 
		        	throw new RuntimeException("invalid Enum ordinal : "+fieldValue +" for "+ targetField.getType().getName());
				Object en = targetField.getType().getEnumConstants()[fieldValue];
				targetField.set(targetObj, en);
			}
		},
		ENUM_TO_INT {
			@Override
			void doConvert(Object sourceObj, Object targetObj,
					Field sourceField, Field targetField) throws Exception {
				int fieldValue = -1;
				for(int i = 0; i < sourceField.getType().getEnumConstants().length; i++){
					if(sourceField.getType().getEnumConstants()[i] == sourceField.get(sourceObj)){
						fieldValue = i;
						break;
					}
				}
				if(fieldValue < 0){
					throw new RuntimeException("invalid Enum ordinal : "+ fieldValue +" for "+ sourceField.getType().getName());
				}
				targetField.setInt(targetObj, fieldValue);
			}
		},
		INT_TO_BOOLEAN {
			@Override
			void doConvert(Object sourceObj, Object targetObj,
					Field sourceField, Field targetField) throws Exception {
				int fieldValue = (Integer)sourceField.get(sourceObj);
				targetField.setBoolean(targetObj, fieldValue == 1 ? true : false);
			}
		},
		BOOLEAN_TO_INT {
			@Override
			void doConvert(Object sourceObj, Object targetObj,
					Field sourceField, Field targetField) throws Exception {
				boolean fieldValue = (Boolean)sourceField.get(sourceObj);
				targetField.setInt(targetObj, fieldValue ? 1 : 0);
			}
		},
		STRING_TO_INT {
			@Override
			void doConvert(Object sourceObj, Object targetObj,
					Field sourceField, Field targetField) throws Exception {
				String fieldValue = (String)sourceField.get(sourceObj);
				targetField.setInt(targetObj, Integer.valueOf(fieldValue));
			}
		},
		INT_TO_STRING {
			@Override
			void doConvert(Object sourceObj, Object targetObj,
					Field sourceField, Field targetField) throws Exception {
				int fieldValue = (Integer)sourceField.get(sourceObj);
				targetField.set(targetObj, String.valueOf(fieldValue));
			}
		};
		
		abstract void doConvert(Object sourceObj, Object targetObj, Field sourceField, Field targetField) throws Exception;
	}
	
	/**
	 * Convert Model to JPA Model, Using Annotation DaoConvertField from JPA Model
	 * Not deal with static variable
	 * @param sourceO
	 * @param jpaModel
	 */
	public static void convertToJpaModel(Object sourceObj, Object jpaModel){
		if(sourceObj == null || jpaModel == null)
			return;
		Map<String, Field> jpaFieldMap = getJpaFieldMap(jpaModel, sourceObj.getClass().getName());
		
		Class<?> sourceClass = sourceObj.getClass();
		while(sourceClass != null){
			Field[] fields = sourceClass.getDeclaredFields();
			for (Field sourceField : fields) {
				String fieldName = sourceField.getName();
			    if(!jpaFieldMap.containsKey(fieldName)){
			    	continue;
			    }
			    Field jpaField = jpaFieldMap.get(fieldName);
			    if((jpaField.getModifiers() & Modifier.STATIC) != 0)// not deal with static variable 
			    	continue;
			    
			    doConvert(sourceObj, jpaModel, sourceField, jpaField);
			    
			    jpaFieldMap.remove(fieldName);
			    
			    if(jpaFieldMap.isEmpty())
			    	return;
			}
			sourceClass = sourceClass.getSuperclass();
		}
	}
	
	/**
	 * Convert Model from JPA Model, Using Annotation DaoConvertField from JPA Model
	 * Not deal with static variable
	 * @param jpaModel
	 * @param targetO
	 */
	public static void convertFromJpaModel(Object jpaModel, Object targetObj){
		if(targetObj == null || jpaModel == null)
			return;
		Map<String, Field> jpaFieldMap = getJpaFieldMap(jpaModel, targetObj.getClass().getName());
		Class<?> targetClass = targetObj.getClass();
		while(targetClass != null){
			Field[] fields = targetClass.getDeclaredFields();
			for (Field targetField : fields) {
				if((targetField.getModifiers() & Modifier.STATIC) != 0)// not deal with static variable 
			    	continue;
				String fieldName = targetField.getName();
			    if(!jpaFieldMap.containsKey(fieldName)){
			    	continue;
			    }
			    
			    Field jpaField = jpaFieldMap.get(fieldName);
			    
			    doConvert(jpaModel, targetObj, jpaField, targetField);
			    
			    jpaFieldMap.remove(fieldName);
			    
			    if(jpaFieldMap.isEmpty())
			    	return;
			}
			targetClass = targetClass.getSuperclass();
		}
	}
	
	/**
	 * Integer and Enum will convert according to EnumConstants's index
	 * Integer can convert to Long, but Long can not convert to Integer
	 * Boolean and Integer will convert according to (TRUE == 1; FALSE != 1)
	 * @param sourceObj
	 * @param targetObj
	 * @param sourceField
	 * @param targetField
	 */
	private static void doConvert(Object sourceObj, Object targetObj, Field sourceField, Field targetField){
		sourceField.setAccessible(true);
		targetField.setAccessible(true);
	    try{
	    	ConvertRule rule = ConvertRule.NORULE;
	    	if(!sourceField.getType().equals(targetField.getType())){
	    		if(targetField.getType().isEnum() && sourceField.get(sourceObj) instanceof Integer){
	    			rule = ConvertRule.INT_TO_ENUM;
	    		}else if(targetField.get(targetObj) instanceof Integer && sourceField.getType().isEnum()){
	    			rule = ConvertRule.ENUM_TO_INT;
	    		}else if(targetField.get(targetObj) instanceof Boolean && sourceField.get(sourceObj) instanceof Integer){
	    			rule = ConvertRule.INT_TO_BOOLEAN;
	    		}else if(targetField.get(targetObj) instanceof Integer && sourceField.get(sourceObj) instanceof Boolean){
	    			rule = ConvertRule.BOOLEAN_TO_INT;
	    		}else if(targetField.get(targetObj) instanceof Integer && sourceField.getType().getName().equals("java.lang.String")){
	    			rule = ConvertRule.STRING_TO_INT;
	    		}else if(targetField.getType().getName().equals("java.lang.String") && sourceField.get(sourceObj) instanceof Integer){
	    			rule = ConvertRule.INT_TO_STRING;
	    		}
	    	}
	    	rule.doConvert(sourceObj, targetObj, sourceField, targetField);
	    }catch(Exception e){
	    	logger.error("convertFromJpaModel setField failed! sourceFieldName : " + sourceField.getName() + ", error msg : " + e.getMessage());
	    }
	}
	
	private static Map<String, Field> getJpaFieldMap(Object jpaModel, String convertClassName){
		Class<?> jpaModelClass = jpaModel.getClass();
		Map<String, Field> jpaFieldMap = new HashMap<String, Field>();
		boolean skipField = false;
		while(jpaModelClass != null){
			Field[] fields = jpaModelClass.getDeclaredFields();
			for(Field jpaField : fields){
				skipField = false;
				String jpaFieldName = jpaField.getName();
				Annotation[] annotations = jpaField.getAnnotations();
				for(Annotation annotation : annotations){
					if(annotation instanceof DaoConvertSkipField){
						skipField = true;
						break;
					}
					if(annotation instanceof DaoConvertField){
						if(convertClassName.equals(((DaoConvertField) annotation).className())){
							jpaFieldName = ((DaoConvertField) annotation).fieldName();
							break;
						}
					}
				}
				if(jpaFieldName == null || jpaFieldName.isEmpty()){
					throw new RuntimeException("Invalid annotation DaoConvertField, fieldName is null! Class : " + jpaModelClass.getName() + ", jpaFieldName : " + jpaField.getName());
				}
				if(jpaFieldMap.containsKey(jpaFieldName)){
					logger.warn("getJpaFieldMap, SuperClass have the same Field with SubClass, Only deal with SubClass's Field! "
							+ "Class : " + jpaModelClass.getName() + ", jpaFieldName : " + jpaField.getName());
					continue;
				}
				if(!skipField)
					jpaFieldMap.put(jpaFieldName, jpaField);
			}
			jpaModelClass = jpaModelClass.getSuperclass();
		}
		return jpaFieldMap;
	}

}
