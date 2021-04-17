package cn.yj.activity.service.impl;

import cn.yj.activity.entity.po.Form;
import cn.yj.activity.mapper.FormMapper;
import cn.yj.activity.service.IFormService;
import cn.yj.common.UUIdUtils;
import cn.yj.common.baseDao.ServiceImpl;
import cn.yj.params.check.annotation.CheckObjectValue;
import cn.yj.params.check.annotation.KeyValue;
import cn.yj.params.check.annotation.Require;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-11 11:23
 */
@Service
@Transactional
public class FormServiceImpl extends ServiceImpl<FormMapper, Form> implements IFormService
{

    @CheckObjectValue(keyValue = {@KeyValue(type = Form.class, name = {"formName", "procDefId", "createUserId", "formContent"})})
    @Override
    public boolean insert(Form form)
    {
        form.setId(UUIdUtils.getUUId32());
        form.setCreateTime(new Date());
        form.setUpdateTime(new Date());
        return super.insert(form);
    }

    @CheckObjectValue(keyValue = {@KeyValue(type = Form.class, name = {"id"})})
    @Override
    public boolean updateById(Form form)
    {
        form.setUpdateTime(new Date());
        return super.updateById(form);
    }

    @Override
    public Form selectByProcDefId(@Require String procDefId)
    {
        return baseMapper.selectByProcDefId(procDefId);
    }
}
