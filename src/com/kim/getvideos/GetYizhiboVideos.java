package com.kim.getvideos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetYizhiboVideos {


	public static void getVideo(String url, String folerName, String tsNumStr) throws IOException {
		long startTime = System.currentTimeMillis();
		URL imgURL = new URL(url.trim());// 转换URL
		HttpURLConnection urlConn = (HttpURLConnection) imgURL.openConnection();// 构造连接
		urlConn.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.79 Safari/537.36");
		urlConn.connect();
		System.out.println(GetYizhiboVideos.class.toString() + ":获取连接=" + urlConn.getResponseMessage());
		String folerRoute = "H:"+File.separator+"yizb"+File.separator;
		File folder = new File(folerRoute);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		if (urlConn.getResponseCode() == 200) {// 返回的状态码是200 表示成功
			InputStream ins = urlConn.getInputStream(); // 获取输入流,从网站读取数据到 内存中

			String newSubFolerName = "H:"+File.separator+"yizb"+File.separator+ folerName;//存到H盘下yizb文件夹

			File subfolder = new File(newSubFolerName);

			if (!subfolder.exists()) {

				subfolder.mkdirs();

			}

			OutputStream out = new BufferedOutputStream(
					new FileOutputStream(new File(newSubFolerName +File.separator + tsNumStr)));
			int len = 0;
			byte[] buff = new byte[1024 * 10];

			while (-1 != (len = (new BufferedInputStream(ins)).read(buff))) {
				out.write(buff, 0, len);
			}
			urlConn.disconnect();
			ins.close();
			out.close();
			System.out.println(GetYizhiboVideos.class.toString() + "：获取ts视频完成,耗时="
					+ ((System.currentTimeMillis() - startTime) / 1000) + "s");
		}

	}

	/**
	 * 简化方法名
	 * @param videoUrl
	 * @param folderName
	 */
	public static void shortMethod(String videoUrl, String folderName) {
		int tsNum = 0;
		String tsNumStr = "";//文件名000000.ts
		String tsNumUrlStr="";//url   0,1,2,3...279.ts
		while (tsNum <= 500) {// 1小时视频tsNum约300
			tsNumStr = String.format("%06d", tsNum) + ".ts";
			tsNumUrlStr=Integer.toString(tsNum)+".ts";
			try {
				GetYizhiboVideos.getVideo(videoUrl + tsNumUrlStr, folderName + "", tsNumStr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tsNum++;

			ThreadV t1 = new ThreadV("t1");
			t1.start();
		}
	}

	
	
	/**
	 * 测试方法
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		GetYizhiboVideos.shortMethod("https://alcdn.hls.xiaoka.tv/201863/15a/9da/cdp5BboBsDT0eRe4/","20180603dl");//示例链接

	}

}



