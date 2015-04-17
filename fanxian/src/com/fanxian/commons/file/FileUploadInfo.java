package com.fanxian.commons.file;

import java.io.InputStream;

/**
 * @author yozohj 2013-8-7 下午09:30:32
 */
public class FileUploadInfo {

    private String      fileName;
    private long        fileSize;
    private InputStream inputStream;

    public FileUploadInfo(String fileName, long fileSize, InputStream inputStream) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.inputStream = inputStream;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @return the fileSize
     */
    public long getFileSize() {
        return fileSize;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

}
