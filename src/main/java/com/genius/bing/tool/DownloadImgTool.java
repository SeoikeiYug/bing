package com.genius.bing.tool;

import org.apache.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @类名 DownloadImgTool
 * @描述 DownloadImgTool
 * @作者 shuaiqi
 * @创建日期 2020/1/10 11:33
 * @版本号 1.0
 * @参考地址
 **/
@Component
public class DownloadImgTool {

	public static void writeImgEntityToFile(HttpEntity imgEntity, String fileAddress) {
		File storeFile = new File(fileAddress);
		try (FileOutputStream output = new FileOutputStream(storeFile)) {
			if (imgEntity != null) {
				InputStream instream;
				instream = imgEntity.getContent();
				byte[] b = new byte[8 * 1024];
				int count;
				while ((count = instream.read(b)) != -1) {
					output.write(b, 0, count);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}