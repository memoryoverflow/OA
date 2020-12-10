package com.yj.oa.project.service.attend;

import com.yj.oa.common.constant.CsEnum;
import com.yj.oa.common.utils.DateUtils;
import com.yj.oa.common.utils.StringUtils;
import com.yj.oa.project.mapper.*;
import com.yj.oa.project.po.Attend;
import com.yj.oa.project.po.LeaveForm;
import com.yj.oa.project.po.User;
import com.yj.oa.project.po.WorkTime;
import com.yj.oa.project.service.workTime.WorkTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 永健
 */
@Service
@Transactional
public class AttendServiceImpl implements IAttendService{
    private Logger log = LoggerFactory.getLogger(AttendServiceImpl.class);


    @Autowired
    AttendMapper attendMapper;

    @Autowired
    WorkTimeMapper workTimeMapper;

    @Autowired
    UserMapper userMapper;
    @Autowired
    LeaveFormMapper leaveFormMapper;


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
            return attendMapper.deleteByPrimaryKeys(positionId);
        }
        catch (RuntimeException e)
        {
            log.error("$$$$$ 删除公失败=[{}]", e);
            throw new RuntimeException("操作失败！");
        }
    }

    /**
     *
     * @描述 添加 打卡
     *
     * @date 2018/9/18 20:19
     */
    @Override
    public int insertSelective(Attend attend) throws Exception
    {

        int i = 0;
        Date date = new Date();
        long currDate = date.getTime();
        WorkTime workShif = workTimeMapper.selectUsing();


        //2.判断是不是当天第一次打卡,如果是 就插入数据，否则更新 中午 或者下午 晚上 时间的打卡时间
        Attend isAttend = attendMapper.selectSaveDayIsAttend(attend.getUserId());
        Attend attend1 = null;
        if (StringUtils.isNull(isAttend))
        {
            //第一次打卡
            attend1 = attend(date, workShif, attend, currDate);
            attend1.setWeek(DateUtils.getTodayWeek());
            i = attendMapper.insertSelective(attend1);
        }
        else
        {
            attend1 = attend(date, workShif, attend, currDate);
            attend1.setWeek(DateUtils.getTodayWeek());
            i = attendMapper.updateByPrimaryKeySelective(attend1);
        }

        return i;
    }


    /**
     *
     * @描述 根据id查
     *
     * @date 2018/9/18 20:19
     */
    @Override
    public Attend selectByPrimaryKey(Integer id)
    {
        return attendMapper.selectByPrimaryKey(id);
    }


    /**
     * 管理员修改打卡 记录
     */
    @Override
    public int updateByPrimaryKey(Attend record)
    {
        return attendMapper.updateByPrimaryKey(record);
    }

    /**
     *
     * @描述 列表
     *
     * @date 2018/9/18 20:20
     */
    @Override
    public List<Attend> selectAttendList(Attend record)
    {
        return attendMapper.selectAttendList(record);
    }

    /**
     * 判断当天是否已经第一次打卡
     */
    @Override
    public Attend selectSaveDayIsAttend(String userId)
    {
        return attendMapper.selectSaveDayIsAttend(userId);
    }


    public Attend attend(Date date, WorkTime workShif, Attend attend, long currDate) throws Exception
    {
        //早上上班时间
        long morWorkStartTime = WorkTimeUtils.MorWorkStartTime(date, workShif);
        // 早上下班时间
        long morWorkEndTime = WorkTimeUtils.MorWorkEndTime(date, workShif);

        //早上开始打卡时间
        long attendStartMorTime = WorkTimeUtils.attendStartMorTime(date, workShif);
        //早上结束打卡时间
        long attendEndMorTime = WorkTimeUtils.attendEndMorTime(date, workShif);

        //早上下班打卡开始时间
        long leavMorStartTime = WorkTimeUtils.leaveMorStartTime(date, workShif);
        //早上下班打卡结束时间
        long leavMorEndtTime = WorkTimeUtils.leaveMorEnddate(date, workShif);


        //下午上班打卡开始时间
        long attendAfterNoonStatrTime = WorkTimeUtils.attendAfterNoonStatrTime(date, workShif);
        //下午上班打卡结束时间
        long attendAfterNoonEndTime = WorkTimeUtils.attendAfterNoonEndTime(date, workShif);

        //下午上班开始时间
        long afterNoonStarWorkTime = WorkTimeUtils.AfterNoonStarWorkTime(date, workShif);
        //下午上班结束时间
        long afterNonEndWorkTime = WorkTimeUtils.AfterNonEndWorkTime(date, workShif);


        //下午下班开始打卡时间
        long attendAfterNoonleaveStartTime = WorkTimeUtils.AttendAfterNoonLeaveStartTime(date, workShif);
        //下午下班结束打卡时间
        long attendAfterNoonleaveEndTime = WorkTimeUtils.AttendAfterNoonLeaveEndTime(date, workShif);


        Attend saveDayIsAttend = attendMapper.selectSaveDayIsAttend(attend.getUserId());

        Attend isFirstAttend = attendMapper.selectSaveDayIsAttend(attend.getUserId());

        //如果第一次打卡不是早上上班时间，那么当天的考勤就设为异常
        if (StringUtils.isNull(isFirstAttend))
        {
            if (currDate > attendEndMorTime)
            {
                attend.setStatus(CsEnum.attend.ATTEND_ERROR.getValue());
            }
        }


        //早打卡 打卡开始时间 < 当前打卡时间 < 下班时间
        if (attendStartMorTime <= currDate && currDate < attendEndMorTime)
        {
            //判断是否重复打卡
            if (saveDayIsAttend != null && saveDayIsAttend.getAttendMorStart() != null)
            {
                throw new Exception("请勿重复打卡！");
            }

            // 当前打卡时间 > 上班时间
            if (currDate > morWorkStartTime)
            {
                //迟到
                attend.setStatus(CsEnum.attend.ATTEND_ERROR.getValue());
            }
            attend.setAttendMorStart(date);
        }
        //中午下班打卡
        else if (leavMorStartTime <= currDate && currDate <= leavMorEndtTime)
        {
            //判断是否重复打卡
            if (saveDayIsAttend != null && saveDayIsAttend.getAttendMorLeave() != null)
            {
                throw new Exception("请勿重复打卡！");
            }
            attend.setAttendMorLeave(date);
        }
        //下午上班打卡
        else if (attendAfterNoonStatrTime <= currDate && currDate < attendAfterNoonEndTime)
        {
            //判断是否重复打卡
            if (saveDayIsAttend != null && saveDayIsAttend.getAttendNoonStart() != null)
            {
                throw new Exception("请勿重复打卡！");
            }
            if (currDate > afterNoonStarWorkTime)
            {
                //迟到
                attend.setStatus(CsEnum.attend.ATTEND_ERROR.getValue());
            }
            attend.setAttendNoonStart(date);
        }
        else if (attendAfterNoonleaveStartTime <= currDate && currDate <= attendAfterNoonleaveEndTime)
        {
            //判断是否重复打卡
            if (saveDayIsAttend != null && saveDayIsAttend.getAttendNoonLeave() != null)
            {
                throw new Exception("请勿重复打卡！");
            }
            attend.setAttendNoonLeave(date);
        }
        else
        {
            throw new Exception("现在不是打卡时间！");
        }
        return attend;
    }
}
