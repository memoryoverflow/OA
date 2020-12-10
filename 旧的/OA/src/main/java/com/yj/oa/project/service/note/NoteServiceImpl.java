package com.yj.oa.project.service.note;

import com.yj.oa.project.mapper.NoteMapper;
import com.yj.oa.project.po.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 永健
 */
@Service
@Transactional
public class NoteServiceImpl implements INoteService{
    @Autowired
    NoteMapper noteMapper;


    /**
     *
     * @描述:  批量删除
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:44
    */
    @Override
    public int deleteByPrimaryKeys(Integer[] id)
    {
        return noteMapper.deleteByPrimaryKeys(id);
    }

    /**
     *
     * @描述:  添加
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:44
    */
    @Override
    public int insertSelective(Note record)
    {
        return noteMapper.insertSelective(record);
    }


    /**
     *
     * @描述:  主键查询
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:44
    */
    @Override
    public Note selectByPrimaryKey(Integer id)
    {
        return noteMapper.selectByPrimaryKey(id);
    }

    /**
     *
     * @描述:  修改
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:45
    */
    @Override
    public int updateByPrimaryKeySelective(Note record)
    {
        return noteMapper.updateByPrimaryKeySelective(record);
    }

    /**
     *
     * @描述:  个人便签列表
     *
     * @params:
     * @return:
     * @date: 2018/9/28 14:50
    */
    @Override
    public List<Note> selectNoteList(Note note)
    {
        return noteMapper.selectNoteList(note);
    }
}
