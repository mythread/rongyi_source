package com.fanxian.commons.file;

public class FileResult {

    private int    size;
    private String filePath;
    private String imagePath;

    public FileResult() {

    }

    /**
     * 构造器
     * 
     * @param size
     * @param filePath
     */
    public FileResult(int size, String filePath, String imagePath) {
        this.size = size;
        this.filePath = filePath;
        this.imagePath = imagePath;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath the filePath to set
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the imagePath
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * @param imagePath the imagePath to set
     */
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
