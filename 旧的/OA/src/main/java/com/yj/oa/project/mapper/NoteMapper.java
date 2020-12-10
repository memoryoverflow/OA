package com.yj.oa.project.mapper;

import com.yj.oa.project.po.Note;

import java.util.List;

public interface NoteMapper {

    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKeys(Integer[] id);

    /**
     * 添加
     * @param record
     * @return
     */
    int insertSelective(Note record);

    /**
     * id查询
     * @param id
     * @return
     */
    Note selectByPrimaryKey(Integer id);

    /**
     * 修改
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Note record);

    /**
     * 列表
     * @param note
     * @return
     */
    List<Note> selectNoteList(Note note);
}