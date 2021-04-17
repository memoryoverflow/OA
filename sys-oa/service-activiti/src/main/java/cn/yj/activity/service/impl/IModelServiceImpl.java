package cn.yj.activity.service.impl;

import cn.yj.activity.entity.ModelVo;
import cn.yj.activity.service.IModelService;
import cn.yj.annotation.pagehelper.page.Page;
import cn.yj.commons.utils.StringUtils;
import cn.yj.params.check.annotation.CheckObjectValue;
import cn.yj.params.check.annotation.KeyValue;
import cn.yj.params.check.annotation.Require;
import cn.yj.tools.exception.ServiceException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-08 16:50
 */
@Service
public class IModelServiceImpl extends ActivityBaseService implements IModelService
{
    private static final int VERSION = 1;

    @Override
    public List<Model> getListModel(String modelName)
    {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        if (StringUtils.isNotBlank(modelName))
        {
            modelQuery.modelNameLike(modelName);
        }
        return modelQuery.list();
    }

    @Override
    public Page<Model> getListPageModel(String modelName, Page<Model> page)
    {
        ModelQuery modelQuery = repositoryService.createModelQuery();
        if (StringUtils.isNotBlank(modelName))
        {
            modelQuery.modelNameLike(modelName);
        }
        int pageSize = page.getPageSize();
        long count = modelQuery.count();
        page.setRows(modelQuery.listPage(page.getPageNum() - 1, pageSize));
        page.setTotal(count);
        page.setPageSize(pageSize);
        page.setPages((int) ((count % pageSize == 0) ? (count / pageSize) : ((count / pageSize) + 1)));
        return page;
    }

    @CheckObjectValue(keyValue = {@KeyValue(type = ModelVo.class, name = {"modelName", "modelKey"})})
    @Override
    public Model crateModel(ModelVo modelVo)
    {
        // 设置 model表中的 meta_info_
        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME, modelVo.getModelName());
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, modelVo.getModelDesc());
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, VERSION);

        // 创建一个模型
        Model model = repositoryService.newModel();
        model.setKey(modelVo.getModelKey());
        model.setName(modelVo.getModelName());
        model.setMetaInfo(modelNode.toString());

        // 保存模型
        repositoryService.saveModel(model);

        // 完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);

        try
        {
            repositoryService.addModelEditorSource(model.getId(), editorNode.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e)
        {
            throw new ServiceException(e.getMessage());
        }

        return model;
    }

    @Override
    public void deleteByModelId(@Require String modelId)
    {
        repositoryService.deleteModel(modelId);
    }

    @Override
    public void modelDeploy(@Require String modelId) throws IOException
    {
        Model model = repositoryService.getModel(modelId);

        if (StringUtils.isNotBlank(model.getDeploymentId()))
        {
            throw new ServiceException("该流程已经部署");
        }

        byte[] bytes = repositoryService.getModelEditorSource(model.getId());
        if (StringUtils.isNull(model) || StringUtils.isNull(bytes))
        {
            throw new ServiceException(String.format("模型ID不存在 [ %s ]", modelId));
        }
        JsonNode modelNode = objectMapper.readTree(bytes);

        BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (bpmnModel.getProcesses().isEmpty())
        {
            throw new ServiceException("请至少设计一条主线流程");
        }

        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(bpmnModel);

        // 部署发布模型流程
        String processName = model.getName() + ".bpmn20.xml";

        Deployment deployment = repositoryService
                .createDeployment()
                .name(model.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();

        // 默认挂起流程
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId());
        repositoryService.suspendProcessDefinitionById(processDefinitionQuery.deploymentId(deployment.getId()).singleResult().getId());

        // 更新 Model deploymentId
        model.setDeploymentId(deployment.getId());
        repositoryService.saveModel(model);
    }
}
