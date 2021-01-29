package cn.yj.core.notice;

import cn.yj.annotation.pagehelper.page.Page;
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
    Page<Notice> findList(Map<String, Object> params, Page<Notice> update_time);
}
