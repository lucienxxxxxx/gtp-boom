package com.ruoyi.did.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.did.DidUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/did")
public class DidController extends BaseController {

    @GetMapping("genDidClip")
    @ResponseBody
    public AjaxResult genDidClip(String text) throws IOException, InterruptedException {
        return success(DidUtils.genClips(text));
    }

    @GetMapping("genDidTalk")
    @ResponseBody
    public AjaxResult genDidTalk(String text) throws IOException, InterruptedException {
        return success(DidUtils.genTalks(text));
    }
}
