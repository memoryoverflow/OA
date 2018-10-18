package com.yj.oa.project.service.attendCount;

import com.yj.oa.common.utils.myThread.MyAttendCountThread;
import com.yj.oa.project.mapper.*;
import com.yj.oa.project.po.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author 永健
 */
@Service
@Transactional
public class AttendCountServiceImpl implements IAttendCountService{
    private Logger log = LoggerFactory.getLogger(AttendCountServiceImpl.class);


    @Autowired
    AttendCountMapper attendCountMapper;

    @Autowired
    AttendMapper attendMapper;

    @Autowired
    WorkTimeMapper workTimeMapper;

    @Autowired
    UserMapper userMapper;
    @Autowired
    LeaveFormMapper leaveFormMapper;

    @Autowired
    ThreadPoolExecutor threadPoolExecutor;


    /**
     *
     * @描述 批量删除
     *
     * @date 2018/9/18 21:13
     */
    @Override
    public int deleteByPrimaryKeys(Integer[] positionId) throws RuntimeException
    {
        try
        {
            return attendCountMapper.deleteByPrimaryKeys(positionId);
        }
        catch (RuntimeException e)
        {
            log.error("$$$$$ 删除公失败=[{}]", e);
            throw new RuntimeException("操作失败！");
        }
    }


    /**
     *
     * @描述 根据id查
     *
     * @date 2018/9/18 20:19
     */
    @Override
    public AttendCount selectByPrimaryKey(Integer id)
    {
        return attendCountMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @描述 修改
     *
     * @date 2018/9/18 20:20
     */
    @Override
    public int updateByPrimaryKeySelective(AttendCount record)
    {
        return attendCountMapper.updateByPrimaryKeySelective(record);
    }

    /**
     *
     * @描述 列表
     *
     * @date 2018/9/18 20:20
     */
    @Override
    public List<AttendCount> selectAttendCountList(AttendCount record)
    {
        return attendCountMapper.selectAttendCountList(record);
    }


    /**
     * 定时任务 每个月的1号统计用户的考勤情况
     */
    @Scheduled(cron = "0 0 0 1 1-12 ?")
    @Override
    public void insertSelective()
    {
        //统计开始计时
        log.info("统计开始=[{}]", new Date());
        log.info("........");

        //每个线程处理30个用户
        final int count = 30;
        //所有用户
        List<User> users = userMapper.selectByUser(new User());
        List<List<User>> lists = getThreadCount(users, count);

        for (int i = 0; i < lists.size(); i++)
        {
            MyAttendCountThread myThread = new MyAttendCountThread(workTimeMapper, attendMapper, leaveFormMapper,
                                                                   attendCountMapper, lists.get(i));
            threadPoolExecutor.execute(myThread);
        }

        //统计结束
        log.info("统计结束，结束时间=[{}]", new Date());
    }

    /**
     * 计算需要开启几个线程
     *
     * @param list 总用户
     * @param count 每个线程处理几个
     */
    static List<List<User>> getThreadCount(List<User> list, int count)
    {
        List<List<User>> lists = new ArrayList<>();


        int p = (list.size() + (count - 1)) / count;

        //开启的线程个数；
        for (int i = 0; i < p; i++)
        {
            List<User> users = new ArrayList<>();

            for (int j = 0; j < list.size(); j++)
            {
                int index = ((j + 1) + (count - 1)) / count;

                if (index == (i + 1))
                {
                    users.add(list.get(j));
                }
                if ((j + 1) == ((j + 1) * count))
                {
                    break;
                }
            }

            lists.add(users);

        }
        System.out.println("开启的线程个数：" + lists.size());
        return lists;
    }


}
