package com.trw.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface CountScore {
	 /**
     * 分数
     */
  //  int score() ;
    
    /**
     * 增  Constant.INTTPUT,
     * 减  Constant.OUTPUT
     */
  //  String type() default Constant.INTTPUT;
    /**
     * 规则Id
     */
    String rule() default "";
}
