package cn.yj.core.notice;


import cn.yj.admin.ConsVal;
import cn.yj.admin.entity.po.User;
import cn.yj.admin.service.IUserService;
import cn.yj.annotation.pagehelper.page.Page;
import cn.yj.common.LoginUser;
import cn.yj.common.upload.FileUploadHandler;
import cn.yj.commons.utils.StringUtils;
import cn.yj.params.check.annotation.CheckObjectValue;
import cn.yj.params.check.annotation.KeyValue;
import cn.yj.params.check.annotation.Require;
import cn.yj.tools.exception.ServiceException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2021-01-25 15:00
 */
@Service
@Primary
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService{
    @Resource
    private IUserService iUserService;


    @Override
    public Page<Notice> findList(Map<String, Object> params, @Require Page<Notice> page, @Require LoginUser loginUser) {
        baseMapper.findList(params, page);
        List<Notice> rows = page.getRows();

        List<Notice> newArrays = new ArrayList<>();

        List<Map<String, Object>> roles = loginUser.getRoles();
        List<String> roleCodes = new ArrayList<>();
        roles.forEach(map -> roleCodes.add(map.get("code").toString()));

        if (roleCodes.contains(ConsVal.SUPER_ADMIN_CODE)) {
            return page;
        }

        // 公告权限过滤筛选
        rows.forEach(notice -> {

            String powerCodesStr = notice.getPowerCodes();
            List<String> powerCodes = new ArrayList<>(Arrays.asList(powerCodesStr.split(",")));
            if (notice.getPowerType().equals(Notice.POWER_USER)) {
                String empCode = loginUser.getEmpCode();
                if (powerCodes.contains(empCode)) {
                    newArrays.add(notice);
                }
            }
            else if (notice.getPowerType().equals(Notice.POWER_ROLE)) {
                powerCodes.retainAll(roleCodes);
                if (!powerCodes.isEmpty()) {
                    newArrays.add(notice);
                }
            }
            else if (notice.getPowerType().equals(Notice.POWER_DEPT)) {
                User user = iUserService.selectById(loginUser.getId());
                String deptCode = user.getDeptCode();
                if (powerCodes.contains(deptCode)) {
                    newArrays.add(notice);
                }
            }
            else {
                newArrays.add(notice);
            }
        });
        int pageSize = page.getPageSize();
        page.setRows(newArrays);
        page.setPageSize(pageSize);
        return page;
    }

    @Override
    @CheckObjectValue(keyValue = {@KeyValue(type = Notice.class, name = {"title", "content", "powerType"})})
    public boolean save(Notice entity) {
        // 处理附件
        uploadFile(entity);

        // 检查
        checkPowerTypeCodes(entity);

        return baseMapper.insert(entity) > 0;
    }

    private void checkPowerTypeCodes(Notice entity) {
        if (entity.getPowerType().equals(Notice.POWER_ROLE) || entity.getPowerType().equals(Notice.POWER_DEPT) || entity.getPowerType().equals(Notice.POWER_USER)) {
            if (StringUtils.isBlank(entity.getPowerCodes())) {
                throw new ServiceException("请勾选选择公告权限");
            }
        }
    }

    @Override
    @CheckObjectValue(keyValue = {@KeyValue(type = Notice.class, name = {"title", "content"})})
    public boolean updateById(Notice entity) {
        uploadFile(entity);
        checkPowerTypeCodes(entity);
        return super.updateById(entity);
    }

    private void uploadFile(Notice entity) {
        String enclosure = entity.getEnclosure();
        if (StringUtils.isNotBlank(enclosure) && !enclosure.startsWith("http")) {
            String[] arr = enclosure.split("&");
            String fileName = arr[0];
            try {
                String fileUrl = FileUploadHandler.getInstant().upload(arr[1], fileName);
                entity.setEnclosure(fileUrl);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ServiceException("附件上传异常");
            }
        }
    }
}
