package com.fanxian.web.common.util;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.yue.commons.seine.web.servlet.result.mime.MimeResultAdapter;

/**
 * 文件下载的WebResult
 */
public class MimeFileResultAdapter extends MimeResultAdapter {

    private String fileName;

    public String getFileName() {
        if (this.fileName == null) return "noname";
        else return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public MimeFileResultAdapter(String fileName) {
        this.fileName = fileName;
    }

    public final boolean onResponse(HttpServletResponse response) {
        String contentType = this.getContentType();
        if (contentType == null || contentType.toString().trim().length() == 0) {
            this.setContentType("application/octet-stream;charset=" + this.getCharacterEncoding());
        }
        response.setContentType(this.getContentType());
        response.setHeader("Pragma", "private");
        response.setHeader("Cache-Control", "private, must-revalidate");

        try {
            this.onDownload(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    /**
     * 执行用户的下载操作
     * 
     * @param outputStream
     */
    protected void onDownload(OutputStream outputStream) throws IOException {
    }
}
