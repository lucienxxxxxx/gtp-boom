package com.ruoyi.gpt.mapper;

import java.util.List;
import com.ruoyi.gpt.domain.Interview;

/**
 * interviewMapper接口
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
public interface InterviewMapper 
{
    /**
     * 查询interview
     * 
     * @param id interview主键
     * @return interview
     */
    public Interview selectInterviewById(Long id);

    /**
     * 查询interview列表
     * 
     * @param interview interview
     * @return interview集合
     */
    public List<Interview> selectInterviewList(Interview interview);

    /**
     * 新增interview
     * 
     * @param interview interview
     * @return 结果
     */
    public int insertInterview(Interview interview);

    /**
     * 修改interview
     * 
     * @param interview interview
     * @return 结果
     */
    public int updateInterview(Interview interview);

    /**
     * 删除interview
     * 
     * @param id interview主键
     * @return 结果
     */
    public int deleteInterviewById(Long id);

    /**
     * 批量删除interview
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInterviewByIds(Long[] ids);
}
