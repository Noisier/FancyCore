package tk.fancystore.noisier.database.data.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataTableInfo {
  String name() default "";

  String create() default "";

  String select() default "";

  String insert() default "";

  String update() default "";
}
