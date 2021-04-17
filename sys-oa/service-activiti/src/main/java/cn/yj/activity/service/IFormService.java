package cn.yj.activity.service;

import cn.yj.activity.entity.po.Form;
import cn.yj.common.baseDao.IService;

/**
 * <br>
 *
 * @author 永健
 * @since 2020-12-11 11:23
 */
public interface IFormService extends IService<Form>{
    Form selectByProcDefId(String procDefId);
}
