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
		URL imgURL = new URL(url.trim());// ת��URL
		HttpURLConnection urlConn = (HttpURLConnection) imgURL.openConnection();// ��������
		urlConn.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.79 Safari/537.36");
		urlConn.connect();
		System.out.println(GetYizhiboVideos.class.toString() + ":��ȡ����=" + urlConn.getResponseMessage());
		String folerRoute = "H:"+File.separator+"yizb"+File.separator;
		File folder = new File(folerRoute);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		if (urlConn.getResponseCode() == 200) {// ���ص�״̬����200 ��ʾ�ɹ�
			InputStream ins = urlConn.getInputStream(); // ��ȡ������,����վ��ȡ���ݵ� �ڴ���

			String newSubFolerName = "H:"+File.separator+"yizb"+File.separator+ folerName;//�浽H����yizb�ļ���

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
			System.out.println(GetYizhiboVideos.class.toString() + "����ȡts��Ƶ���,��ʱ="
					+ ((System.currentTimeMillis() - startTime) / 1000) + "s");
		}

	}

	/**
	 * �򻯷�����
	 * @param videoUrl
	 * @param folderName
	 */
	public static void shortMethod(String videoUrl, String folderName) {
		int tsNum = 0;
		String tsNumStr = "";//�ļ���000000.ts
		String tsNumUrlStr="";//url   0,1,2,3...279.ts
		while (tsNum <= 500) {// 1Сʱ��ƵtsNumԼ300
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
	 * ���Է���
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		GetYizhiboVideos.shortMethod("https://alcdn.hls.xiaoka.tv/201863/15a/9da/cdp5BboBsDT0eRe4/","20180603dl");//ʾ������

	}

}


