package com.yj.oa.project.service.workTime;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.common.utils.DateUtils;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.mapper.WorkTimeMapper;
import com.yj.oa.project.po.WorkTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 永健
 */
@Service
@Transactional
public class WorkTimeServiceImpl implements IWorkTimeService{
    private Logger log = LoggerFactory.getLogger(WorkTimeServiceImpl.class);

    @Autowired
    WorkTimeMapper workTimeMapper;


    /**
     * 根据id 批量删除
     *
     * @param id i
     */
    @Override
    public int deleteByPrimaryKeys(Integer[] ids) throws Exception
    {
        WorkTime workTime = selectUsing();
        for (int id : ids)
        {
            if (workTime.getId() == id)
            {
                throw new Exception("使用中，不允许删除！");
            }

        }
        return workTimeMapper.deleteByPrimaryKeys(ids);
    }

    /**
     * 添加
     */

    @Override
    public int insertSelective(WorkTime workTime) throws Exception
    {
        //默认有一条工作时间 在使用中
        WorkTime w = workTimeMapper.selectUsing();
        if (StringUtils.isNull(w))
        {
            workTime.setStatus(CsEnum.worktime.WORK_TIME_USIN.getValue());
        }


        /**
         * 检验上下班时间是否正确
         * 例如： 下班时间 不能早于上班时间，
         * 打卡开始时间不能 大于 打卡结束时间
         */
        //时间点不能设置为临界点 凌晨00:00:00
        CheckCriticalPoint(workTime);
        checkWorkTimeIsWrong(workTime);
        //上下班打卡时间是否冲突
        checkStartEndTime(workTime);

        workTime.setCreateTime(new Date());
        return workTimeMapper.insertSelective(workTime);
    }

    /**
     * 根据主键查询
     */
    @Override
    public WorkTime selectByPrimaryKey(Integer id)
    {
        return workTimeMapper.selectByPrimaryKey(id);
    }


    /**
     * 启用/停用工作时间表
     */
    public int startOrEndWorkTime(WorkTime record) throws Exception
    {

        //将原来使用的那一条设为停用
        WorkTime workTime = workTimeMapper.selectUsing();
        //设为使用
        if (CsEnum.worktime.WORK_TIME_USIN.getValue() == (int) record.getStatus())
        {
            if (!StringUtils.isNull(workTime))
            {
                workTime.setStatus(CsEnum.worktime.WORK_TIME_FREE.getValue());
                workTimeMapper.updateByPrimaryKeySelective(workTime);
                if (workTime.getId() == record.getId())
                {
                    throw new Exception("使用中!");
                }
            }
        }
        return workTimeMapper.updateByPrimaryKeySelective(record);
    }


    /**
     * 修改工作时间表
     */
    @Override
    public int updateByPrimaryKeySelective(WorkTime workTime) throws Exception
    {
        /**
         * 检验上下班时间是否正确
         * 例如： 下班时间 不能早于上班时间，
         * 打卡开始时间不能 大于 打卡结束时间
         */
        //时间点不能设置为临界点 凌晨00:00:00
        CheckCriticalPoint(workTime);
        checkWorkTimeIsWrong(workTime);
        //上下班打卡时间是否冲突
        checkStartEndTime(workTime);


        return workTimeMapper.updateByPrimaryKeySelective(workTime);
    }


    /**
     * 查询正在使用的那一条
     */
    @Override
    public WorkTime selectUsing()
    {
        return workTimeMapper.selectUsing();
    }

    /**
     * 工作时间列表
     */
    @Override
    public List<WorkTime> selectWorkTimeList(WorkTime workTime)
    {
        return workTimeMapper.selectWorkTimeList(workTime);
    }

    /**
     * 检验上下班的打卡时间是否冲突
     */
    public static void checkStartEndTime(WorkTime workTime) throws Exception
    {
        //下午上班打卡时间和上午下班打卡时间不允许交叉
        Date attendMorLeaveEndTime = workTime.getAttendMorLeaveEndTime();
        Date attendAfterNoonStartTime = workTime.getAttendAfterNoonStartTime();

        int Noonhours = DateUtils.getHours(attendAfterNoonStartTime);
        int Morhours = DateUtils.getHours(attendMorLeaveEndTime);

        //小时是否相同
        if (Morhours > Noonhours)
        {
            throw new Exception("上下班打卡时间冲突");
        }


        //分钟是否相同
        int noonMinute = DateUtils.getMinute(attendAfterNoonStartTime);
        int morMinete = DateUtils.getMinute(attendMorLeaveEndTime);
        if (morMinete > noonMinute)
        {
            throw new Exception("上下班打卡时间冲突");
        }


        //秒是否相同
        int noonSecend = DateUtils.getSecend(attendAfterNoonStartTime);
        int morSecend = DateUtils.getSecend(attendMorLeaveEndTime);
        if (morSecend > noonSecend)
        {
            throw new Exception("上下班打卡时间冲突");
        }
    }


    /**
     * 检验上下班时间是否正确
     * 例如： 下班时间 不能早于上班时间，
     * 打卡开始时间不能 大于 打卡结束时间
     */
    public static void checkWorkTimeIsWrong(WorkTime workTime) throws Exception
    {


        /**
         * date 只拿来比较 取出 年月日 统一日期
         * workTime 取出 时分秒  拼接成 yyyy-MM-DD HH:mm:ss 字符串
         *  转成统一日期时间戳比较
         *  存到数据库中的类型为 time HH:mm:ss
         */

        Date date = new Date();
        //早上打卡时间比较
        if (WorkTimeUtils.attendStartMorTime(date, workTime) > WorkTimeUtils.attendEndMorTime(date, workTime))
        {
            throw new Exception("结束打卡时间早于开始打卡时间！");
        }

        //早上上班时间比较
        if (WorkTimeUtils.MorWorkStartTime(date, workTime) > WorkTimeUtils.MorWorkEndTime(date, workTime))
        {
            throw new Exception("下班时间早于上班时间！");
        }

        //中午下班比较
        if (WorkTimeUtils.leaveMorStartTime(date, workTime) > WorkTimeUtils.leaveMorEnddate(date, workTime))
        {
            throw new Exception("下班打卡时间早于结束打卡时间！");
        }

        //下午上班时间比较
        if (WorkTimeUtils.AfterNoonStarWorkTime(date, workTime) > WorkTimeUtils.AfterNonEndWorkTime(date, workTime))
        {
            throw new Exception("下班时间早于上班时间！");
        }

        //晚上下班比较
        if (WorkTimeUtils.attendAfterNoonStatrTime(date, workTime) > WorkTimeUtils.attendAfterNoonEndTime(date,
                                                                                                          workTime))
        {
            throw new Exception("下班打卡时间早于结束打卡时间！");
        }


    }

    /**
     * 结束打卡时间 和结束上班时间临街点判断
     * @param workTime
     */
    public void CheckCriticalPoint(WorkTime workTime) throws Exception
    {
        //零界点判断
        String getAttendMorendTime = DateUtils.DateToSTr(workTime.getAttendMorendTime()).substring(11, DateUtils.DateToSTr(
                workTime.getAttendMorendTime()).length());
        String getWorkEndTimeMor = DateUtils.DateToSTr(workTime.getWorkEndTimeMor()).substring(11, DateUtils.DateToSTr(
                workTime.getWorkEndTimeMor()).length());
        String getAttendMorLeaveEndTime = DateUtils.DateToSTr(workTime.getAttendMorLeaveEndTime()).substring(11, DateUtils.DateToSTr(
                workTime.getAttendMorLeaveEndTime()).length());
        String getAttendAfterNoonendTime = DateUtils.DateToSTr(workTime.getAttendAfterNoonendTime()).substring(11, DateUtils.DateToSTr(
                workTime.getAttendAfterNoonendTime()).length());
        String getWorkEndTimeAfterNoon = DateUtils.DateToSTr(workTime.getWorkEndTimeAfterNoon()).substring(11, DateUtils.DateToSTr(
                workTime.getAttendAfterNoonendTime()).length());
        String getAttendAfterLeaveEndTime = DateUtils.DateToSTr(workTime.getAttendAfterLeaveEndTime()).substring(11, DateUtils.DateToSTr(
                workTime.getAttendAfterNoonendTime()).length());
        List<String> objects = new ArrayList<>();

        objects.add(getAttendMorendTime);
        objects.add(getWorkEndTimeMor);
        objects.add(getAttendMorLeaveEndTime);
        objects.add(getAttendAfterNoonendTime);
        objects.add(getWorkEndTimeAfterNoon);
        objects.add(getAttendAfterLeaveEndTime);

        for (String s:objects)
        {
            if (s.equals("00:00:00"))
            {
                throw new Exception("时间存在零界点！");
            }
        }


    }
}
