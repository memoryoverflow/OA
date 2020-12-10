package cn.yj.activity.service;

import cn.yj.activity.entity.ModelVo;
import cn.yj.annotation.pagehelper.page.Page;
import org.activiti.engine.repository.Model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-08 16:44
 */
public interface IModelService
{
    /**
     * 模型列表
     *
     * @param modelName 参数
     * @return
     */
    List<Model> getListModel(String modelName);


    Page<Model> getListPageModel(String modelName, Page<Model> page);


    /**
     * 创建model
     *
     * @param modelVo 基本参数
     * @return
     */
    Model crateModel(ModelVo modelVo) ;


    /**
     * 删除model
     *
     * @param modelId id
     * @return
     */
    void deleteByModelId(String modelId);


    /**
     * 模型发布
     *
     * @param modelId
     */
    void modelDeploy(String modelId) throws IOException;

}
