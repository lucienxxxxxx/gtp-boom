package com.ruoyi.gpt.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.gpt.mapper.InterviewMapper;
import com.ruoyi.gpt.domain.Interview;
import com.ruoyi.gpt.service.IInterviewService;

/**
 * interviewService业务层处理
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
@Service
public class InterviewServiceImpl implements IInterviewService 
{
    @Autowired
    private InterviewMapper interviewMapper;

    /**
     * 查询interview
     * 
     * @param id interview主键
     * @return interview
     */
    @Override
    public Interview selectInterviewById(Long id)
    {
        return interviewMapper.selectInterviewById(id);
    }

    /**
     * 查询interview列表
     * 
     * @param interview interview
     * @return interview
     */
    @Override
    public List<Interview> selectInterviewList(Interview interview)
    {
        return interviewMapper.selectInterviewList(interview);
    }

    /**
     * 新增interview
     * 
     * @param interview interview
     * @return 结果
     */
    @Override
    public int insertInterview(Interview interview)
    {
        return interviewMapper.insertInterview(interview);
    }

    /**
     * 修改interview
     * 
     * @param interview interview
     * @return 结果
     */
    @Override
    public int updateInterview(Interview interview)
    {
        return interviewMapper.updateInterview(interview);
    }

    /**
     * 批量删除interview
     * 
     * @param ids 需要删除的interview主键
     * @return 结果
     */
    @Override
    public int deleteInterviewByIds(Long[] ids)
    {
        return interviewMapper.deleteInterviewByIds(ids);
    }

    /**
     * 删除interview信息
     * 
     * @param id interview主键
     * @return 结果
     */
    @Override
    public int deleteInterviewById(Long id)
    {
        return interviewMapper.deleteInterviewById(id);
    }
}
