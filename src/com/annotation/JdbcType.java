package com.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
/**
 * edatagrid数据类型注解
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JdbcType {
	public TYPE type() default TYPE.varchar;
	public boolean required() default true;
	//public String TYPE[]={"number","mail","date"};
	public  enum  TYPE{number,mail,date,varchar,phone,intonly,bool,dateTime};
	public int maxLen() default 0;
	public int minLen() default 0;
	public int max() default 0;
	public int min() default 0;
	public boolean idField() default false;
}
