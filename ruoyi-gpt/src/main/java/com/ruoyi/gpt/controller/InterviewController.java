package com.ruoyi.gpt.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.gpt.domain.Interview;
import com.ruoyi.gpt.service.IInterviewService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * interviewController
 * 
 * @author ruoyi
 * @date 2023-06-15
 */
@RestController
@RequestMapping("/gpt/interview")
public class InterviewController extends BaseController
{
    @Autowired
    private IInterviewService interviewService;

    /**
     * 查询interview列表
     */
    @PreAuthorize("@ss.hasPermi('gpt:interview:list')")
    @GetMapping("/list")
    public TableDataInfo list(Interview interview)
    {
        startPage();
        List<Interview> list = interviewService.selectInterviewList(interview);
        return getDataTable(list);
    }

    /**
     * 导出interview列表
     */
    @PreAuthorize("@ss.hasPermi('gpt:interview:export')")
    @Log(title = "interview", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Interview interview)
    {
        List<Interview> list = interviewService.selectInterviewList(interview);
        ExcelUtil<Interview> util = new ExcelUtil<Interview>(Interview.class);
        util.exportExcel(response, list, "interview数据");
    }

    /**
     * 获取interview详细信息
     */
    @PreAuthorize("@ss.hasPermi('gpt:interview:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(interviewService.selectInterviewById(id));
    }

    /**
     * 新增interview
     */
    @PreAuthorize("@ss.hasPermi('gpt:interview:add')")
    @Log(title = "interview", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Interview interview)
    {
        return toAjax(interviewService.insertInterview(interview));
    }

    /**
     * 修改interview
     */
    @PreAuthorize("@ss.hasPermi('gpt:interview:edit')")
    @Log(title = "interview", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Interview interview)
    {
        return toAjax(interviewService.updateInterview(interview));
    }

    /**
     * 删除interview
     */
    @PreAuthorize("@ss.hasPermi('gpt:interview:remove')")
    @Log(title = "interview", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(interviewService.deleteInterviewByIds(ids));
    }
}
