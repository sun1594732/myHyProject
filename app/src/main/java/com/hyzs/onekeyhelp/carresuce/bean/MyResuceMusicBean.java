package com.hyzs.onekeyhelp.carresuce.bean;

/**
 * Created by hyzs123 on 2017/3/27.
 */

public class MyResuceMusicBean {


    /**
     * url : http://192.168.1.186:803/Files/179/voice/179_8cdc172b-ddf3-42c6-88b5-625d88ed307c.mp3
     * fileType : 1
     * message : 正常
     * code : 10000
     */

    private String url;
    private String fileType;
    private String message;
    private String code;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
