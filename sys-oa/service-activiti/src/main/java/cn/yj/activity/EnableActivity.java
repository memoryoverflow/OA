package cn.yj.activity;

import cn.yj.activity.web.modeler.ModelSaveRestResource;
import org.activiti.rest.editor.main.StencilsetRestResource;
import org.activiti.rest.editor.model.ModelEditorJsonRestResource;
import org.activiti.spring.boot.DataSourceProcessEngineAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <p>
 *
 * </p>
 *
 * @author 永健
 * @since 2021-04-16 18:13
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = {DataSourceProcessEngineAutoConfiguration.class, ModelSaveRestResource.class, ModelEditorJsonRestResource.class, StencilsetRestResource.class})
public @interface EnableActivity{ }
