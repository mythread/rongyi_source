package base.util.file;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import base.util.date.DateTool;

public class FileUtil {

	/**
	 * 获得文件列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public static List<File> getFileList(String path) throws Exception {
		List<File> list = new ArrayList<File>();
		File[] filearr = null;

		File file = new File(path);
		if (file.exists()) {
			filearr = file.listFiles();
			for (int i = 0; i < filearr.length; i++) {
				if (filearr[i].isDirectory()) {
					continue;
				}else{
					list.add(filearr[i]);
				}
			}
			return list;
		} else
			return null;
	}

	/**
	 * 生成图片文件名
	 * @param userType
	 * @param orderType
	 * @return
	 */
	public static String generateFileName() {
		String date = DateTool.getCurrentDateYYMMDD();
		String hmssss = DateTool.getCurrentHMSSSS();
		Random random = new Random();
		int hzrandom = random.nextInt(10);
		String name = date + hmssss + hzrandom;
		return name;
	}
	
	/**
	 * 下载文件到本地
	 * 
	 * @param urlString
	 *            被下载的文件地址
	 * @param filename
	 *            本地文件名
	 * @throws Exception
	 *             各种异常
	 */
	public static String download(String urlString, String localPath) throws Exception {
		String filename = "";
		if (urlString.lastIndexOf(".") > -1) {
			filename = FileUtil.generateFileName() + urlString.substring(urlString.lastIndexOf("."), urlString.length());
		}else {
			throw new Exception("远程图片路径有异常！");
		}
		// 构造URL
		URL url = new URL(urlString);
		// 打开连接
		URLConnection con = url.openConnection();
		// 输入流
		InputStream is = con.getInputStream();
		// 1K的数据缓冲
		byte[] bs = new byte[1024];
		// 读取到的数据长度
		int len;
		// 输出的文件流
		OutputStream os = new FileOutputStream(localPath+filename);
		// 开始读取
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// 完毕，关闭所有链接
		os.close();
		is.close();
		return filename;
	}
}
