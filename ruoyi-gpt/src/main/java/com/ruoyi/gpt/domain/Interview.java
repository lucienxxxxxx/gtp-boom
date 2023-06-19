package com.ruoyi.gpt.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * interview对象 gpt_interview
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
public class Interview extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 会话id */
    @Excel(name = "会话id")
    private Long sessionId;

    /** 面试号uuid */
    @Excel(name = "面试号uuid")
    private Long interviewId;

    /** 被面试者 */
    @Excel(name = "被面试者")
    private String interviewee;

    /** 面试者 */
    @Excel(name = "面试者")
    private String interviewer;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSessionId(Long sessionId) 
    {
        this.sessionId = sessionId;
    }

    public Long getSessionId() 
    {
        return sessionId;
    }
    public void setInterviewId(Long interviewId) 
    {
        this.interviewId = interviewId;
    }

    public Long getInterviewId() 
    {
        return interviewId;
    }
    public void setInterviewee(String interviewee) 
    {
        this.interviewee = interviewee;
    }

    public String getInterviewee() 
    {
        return interviewee;
    }
    public void setInterviewer(String interviewer) 
    {
        this.interviewer = interviewer;
    }

    public String getInterviewer() 
    {
        return interviewer;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sessionId", getSessionId())
            .append("interviewId", getInterviewId())
            .append("interviewee", getInterviewee())
            .append("interviewer", getInterviewer())
            .toString();
    }
}
