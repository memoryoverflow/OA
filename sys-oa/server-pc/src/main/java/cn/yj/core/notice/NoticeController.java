package cn.yj.core.notice;

import cn.yj.annotation.pagehelper.page.OrderBy;
import cn.yj.common.AbstractController;
import cn.yj.common.AppExecutor;
import cn.yj.common.OperateLog;
import cn.yj.common.upload.FileUploadHandler;
import cn.yj.commons.utils.FileUtils;
import cn.yj.commons.utils.StringUtils;
import cn.yj.commons.utils.ZipUtils;
import cn.yj.entity.R;
import cn.yj.tools.exception.ServiceException;
import io.minio.errors.*;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import javax.validation.Valid;
import java.io.*;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2021-01-25 15:10
 */
@RestController
@RequestMapping("/notice")
public class NoticeController extends AbstractController<Notice>{
    private final INoticeService iNoticeService;

    @Autowired
    public NoticeController(INoticeService iNoticeService) {
        this.iNoticeService = iNoticeService;
    }

    @GetMapping("/list")
    public R listPage(@RequestParam Map<String, Object> params) throws Exception {
        return success(iNoticeService.findList(params, page(new OrderBy(OrderBy.Direction.DESC, "create_time")), getCurrentUser()));
    }

    @PostMapping("/save")
    @RequiresPermissions(value = {"notice:add"})
    @OperateLog(describe = "添加公告")
    public R add(@RequestBody @Valid Notice notice) {
        notice.setCreateBy(getCurrentUser().getName());
        return success(iNoticeService.save(notice));
    }


    @RequiresPermissions(value = {"notice:update"})
    @PutMapping("/update")
    @OperateLog(describe = "更新公告")
    public R update(@RequestBody @Valid Notice notice) {
        return success(iNoticeService.updateById(notice));
    }


    @DeleteMapping("/remove/{ids}")
    @RequiresPermissions(value = {"notice:del"})
    @OperateLog(describe = "删除公告")
    public R update(@PathVariable("ids") List<String> ids) {
        return success(iNoticeService.removeByIds(ids));
    }

    @PostMapping("/imgFile")
    public R noticeImgFile(MultipartFile file) throws IOException, InvalidKeyException, NoSuchAlgorithmException, XmlPullParserException, MyException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException {
        if (StringUtils.isNull(file)) {
            throw new ServiceException("the file is not null");
        }
        return R.success(FileUploadHandler.getInstant().upload(file));
    }


    @DeleteMapping("/deleteImgFile")
    public R deleteImgFile(String url) throws IOException, InvalidKeyException, NoSuchAlgorithmException, XmlPullParserException, MyException, InvalidArgumentException, InternalException, NoResponseException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException {
        FileUploadHandler.getInstant().delete(url);
        return R.success();
    }

    /**
     * 下载附件
     *
     * @param id
     * @return
     */
    @GetMapping("/downloadEnclosure/{id}")
    @OperateLog(describe = "下载公告附件")
    public ResponseEntity downloadEnclosure(@PathVariable String id) throws IOException {
        Notice notice = iNoticeService.getById(id);
        if (StringUtils.isNull(notice)) {
            throw new ServiceException("找不到当前公告");
        }

        String url = notice.getEnclosure();

        // 将文件下载到该目录
        long currentTimeMillis = System.currentTimeMillis();
        String userHome = System.getProperty("user.home").concat("/" + currentTimeMillis);
        FileUtils.createFolder(userHome);

        // 创建文件下载路径

        FileUtils.createFolder(userHome);
        // 新建一个说明文件
        String remarkFile = userHome.concat("/").concat("备注说明.txt");
        FileUtils.createFile(remarkFile);

        String zipFile = userHome.concat(".zip");

        String fileName = getBucketName(url);
        try {

            InputStream inputStream = FileUploadHandler.getInstant().download(url);
            IOUtils.copy(inputStream, new FileOutputStream(userHome.concat("/").concat(fileName)));

        } catch (Exception e) {
            FileUtils.writeFileNio(new File(remarkFile), "该专利：[ ".concat(fileName).concat(" ]文件：").concat(fileName).concat(",下载失败/文件不存在"), Charset.defaultCharset().name());
            e.printStackTrace();
        }
        ZipUtils.toZip(userHome, new FileOutputStream(zipFile), true);
        // 删除掉临时文件 和文件夹

        AppExecutor.exec(() -> {
            try {
                Thread.sleep(1 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            FileUtils.delete(zipFile);
            FileUtils.delete(userHome);
        });

        // 设置响应头 http://127.0.0.1:8081/oa/notice/downloadEnclosure/url=http://memoryoverflow.cn:9000/images-bucket/%E4%B8%8B%E8%BD%BD.png
        HttpHeaders headers = new HttpHeaders();
        // 通知浏览器以下载的方式打开文件
        headers.setContentDispositionFormData("attachment", currentTimeMillis + ".zip");
        // 定义以流的形式下载返回文件数据
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 使用Sring MVC框架的ResponseEntity对象封装返回下载数据
        return new ResponseEntity<>(org.apache.commons.io.FileUtils.readFileToByteArray(new File(zipFile)), headers, HttpStatus.OK);
    }

    private static String getBucketName(String name) {
        if (StringUtils.isNotBlank(name)) {
            int i = name.lastIndexOf("/");
            if (i > 0) {
                name = name.substring(i + 1);
            }
        }
        return name;
    }

}
