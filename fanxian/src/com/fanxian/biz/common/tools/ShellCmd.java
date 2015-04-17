package com.fanxian.biz.common.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ShellCmd {

    private static int execute(String shellCommand, String... args) {
        int success = 0;
        try {

            Process pid = null;
            StringBuilder cmdstr = new StringBuilder();
            cmdstr.append(shellCommand).append(" ");
            if (args != null) {
                for (String s : args) {
                    cmdstr.append(s).append(" ");
                }
            }
            String[] cmd = { "/bin/sh", "-c", cmdstr.toString() };
            System.out.println("run shell ---> " + "/bin/sh -c " + cmdstr.toString());
            long t1 = System.currentTimeMillis();
            pid = Runtime.getRuntime().exec(cmd);
            if (pid != null) {
                int p = pid.waitFor();
            } else {
            }
            long t2 = System.currentTimeMillis();
            InputStream out = pid.getInputStream();
            System.out.println(convertStreamToString(out));

            InputStream in = pid.getErrorStream();
            System.out.println(convertStreamToString(in));
            System.out.println("exitValue = " + pid.exitValue() + "  time=" + (t2 - t1));
        } catch (Exception ioe) {
            success = 1;
            ioe.printStackTrace();
        } finally {
        }
        return success;
    }

    /**
     * 移动一个文件<br>
     * 例如: mv("/tmp/1.jpg","/tmp/2.jpg") 把/tmp/1.jpg改名为/tmp/2.jpg
     * 
     * @param source 源文件的绝对路径
     * @param target 目标文件的绝对路径
     * @return
     */
    public static boolean mv(String source, String target) {
        return execute("/bin/mv", "'" + source + "'", "'" + target + "'") == 0;
    }

    /**
     * Copy一个文件<br>
     * 例如: copy("/tmp/1.jpg","/tmp/2.jpg") 把/tmp/1.jpg 复制一份为/tmp/2.jpg
     * 
     * @param source 源文件的绝对路径
     * @param target 目标文件的绝对路径
     * @return
     */
    public static boolean copy(String source, String target) {
        return execute("/bin/cp", "'" + source + "'", "'" + target + "'") == 0;
    }

    /**
     * 压缩sourceList中的文件，生成target文件。<br>
     * demo代码如下：<br>
     * List<String> sourceList = new ArrayList<String>();<br
     * sourceList.add("/tmp/IMGP0003.JPG"); //压缩/tmp/IMGP0003.JPG文件<br
     * sourceList.add("/tmp/IMGP001*.JPG"); //压缩/tmp/IMGP001*.JPG文件<br
     * sourceList.add("/tmp/jpg/"); //压缩/tmp/jpg 文件夹递归的文件<br
     * boolean flag = gzip("/tmp/5.tar.gz", sourceList); //生成的压缩文件/tmp/5.tar.gz
     * 
     * @param target
     * @param sourceList list中每个文件必须是绝对路径。可以支持"*"通配符，同时还支持文件夹递归copy
     * @return
     */
    public static boolean gzip(String path, String target, List<String> sourceList) {
        StringBuilder args = new StringBuilder();
        args.append("'").append(path).append(File.separator).append(target).append("'");
        args.append(" -C '").append(path).append("' ");
        if (sourceList != null) {
            for (String s : sourceList) {
                args.append("'").append(s).append("' ");
            }
        }
        return execute("/bin/tar czvf ", args.toString()) == 0;
    }

    // public static boolean gzip(String target, List<String> sourceList) {
    // StringBuilder args = new StringBuilder();
    // // args.append(target).append(" ");
    // if (sourceList != null) {
    // for (String s : sourceList) {
    // args.append(s).append(" ");
    // }
    // }
    // String tmpDir = "/tmp/" + System.nanoTime() + "/";
    // System.out.println(tmpDir);
    // execute("/bin/mkdir -p", tmpDir);
    // execute("/bin/cp -r ", args.toString() + " " + tmpDir);
    //
    // return execute("/bin/tar czvf", target + " " + tmpDir) == 0;
    // // return true;
    // }

    private static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    /**
	 */
    public static void main(String[] args) {
        // boolean flag = copy("/tmp/aaa.txt", "/tmp/bbb.txt");
        List<String> sourceList = new ArrayList<String>();
        sourceList.add("1.png");
        sourceList.add("2.png");
        sourceList.add("3.png");
        sourceList.add("4.png");
        // sourceList.add("/tmp/jpg/");
        boolean flag = gzip("/tmp/1386302378113672000", "abc.tar.gz", sourceList);
        System.out.println(flag);
    }
}
