package cn.yj.core.notice;


import cn.yj.annotation.pagehelper.page.Page;
import cn.yj.common.upload.FileUploadHandler;
import cn.yj.commons.utils.StringUtils;
import cn.yj.params.check.CheckObjectValue;
import cn.yj.params.check.KeyValue;
import cn.yj.tools.exception.ServiceException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2021-01-25 15:00
 */
@Service
@Primary
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService
{
    @Override
    public Page<Notice> findList(Map<String, Object> params, Page<Notice> page)
    {
        baseMapper.findList(params, page);
        return page;
    }

    @Override
    @CheckObjectValue(keyValue = {@KeyValue(type = Notice.class, name = {"title", "content"})})
    public boolean save(Notice entity)
    {
        uploadFile(entity);
        return baseMapper.insert(entity) > 0;
    }

    @Override
    @CheckObjectValue(keyValue = {@KeyValue(type = Notice.class, name = {"title", "content"})})
    public boolean updateById(Notice entity)
    {
        uploadFile(entity);
        return super.updateById(entity);
    }

    private void uploadFile(Notice entity)
    {
        String enclosure = entity.getEnclosure();
        if (StringUtils.isNotBlank(enclosure) && !enclosure.startsWith("http"))
        {
            String[] arr = enclosure.split("&");
            String fileName = arr[0];
            try
            {
                String fileUrl = FileUploadHandler.getInstant().upload(arr[1], fileName);
                entity.setEnclosure(fileUrl);
            } catch (Exception e)
            {
                e.printStackTrace();
                throw new ServiceException("附件上传异常");
            }
        }
    }
}
