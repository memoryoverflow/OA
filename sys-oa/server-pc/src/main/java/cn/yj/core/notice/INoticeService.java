package cn.yj.core.notice;

import cn.yj.annotation.pagehelper.page.Page;
import cn.yj.common.LoginUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <br>
 *
 *
 *
 * @author 永健
 * @since 2021-01-25 15:00
 */
public interface INoticeService extends IService<Notice>
{
    /**
     *
     * @param params 搜索参数
     * @param page 分页对象
     * @param userCode 当前用户编码
     * @return
     */
    Page<Notice> findList(Map<String, Object> params, Page<Notice> page, LoginUser loginUser);
}
