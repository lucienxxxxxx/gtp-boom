package com.ruoyi.baidu.domain;


import org.springframework.web.multipart.MultipartFile;

public class IatRequestBody {
    public MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
