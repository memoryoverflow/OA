package cn.yj.core.notice;

import cn.yj.annotation.pagehelper.StartPage;
import cn.yj.annotation.pagehelper.page.Page;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2021-01-25 14:59
 */
public interface NoticeMapper extends BaseMapper<Notice>
{
    @StartPage
    List<Notice> findList(@Param("map") Map<String, Object> params, Page<Notice> page);
}
