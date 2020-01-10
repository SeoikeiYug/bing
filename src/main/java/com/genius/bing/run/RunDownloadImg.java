package com.genius.bing.run;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.genius.bing.tool.DownloadImgTool.writeImgEntityToFile;

/**
 * @类名 RunDownloadImg
 * @描述 RunDownloadImg
 * @作者 shuaiqi
 * @创建日期 2020/1/10 11:39
 * @版本号 1.0
 * @参考地址
 **/
public class RunDownloadImg {

	public static void main(String[] args) {
		System.out.println("获取Bing图片地址中……");

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		HttpGet httpGet = new HttpGet("http://cn.bing.com");

		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
			Pattern p = Pattern.compile("g_img=\\{url:.*\\.jpg");
			Matcher m = p.matcher(EntityUtils.toString(response.getEntity()));
			String address = null;
			if (m.find()) {
				System.out.println(m.group());
				address = m.group().split("\"")[1].split("\\\\")[0];
			} else {
				System.exit(0);
			}
			System.out.println("图片地址:" + address);
			System.out.println("正在下载……");
			HttpGet getImage = new HttpGet("http://cn.bing.com" + address);
			CloseableHttpResponse responseImg = httpClient.execute(getImage);
			HttpEntity entity = responseImg.getEntity();

			writeImgEntityToFile(entity,"C:\\buildtools\\img" + dateFormat.format(new Date()) + ".jpg");

			System.out.println("下载完毕.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}