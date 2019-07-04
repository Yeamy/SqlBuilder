package com.yeamy.sql.statement.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@java.lang.annotation.Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Target {
	DataBase value() default DataBase.ALL;
}
