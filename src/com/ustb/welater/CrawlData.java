package com.ustb.welater;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlData {
	
	/**
	 * 爬取网页的源代码
	 * @param url
	 * @param encoding
	 * @return
	 * @deprecated 改成用Jsoup自带的Jsoup.connect(url)
	 */
	public static String getHtmlResourceByURL(String url, String encoding) {
		URL urlObj = null;
		HttpURLConnection httpConn = null;
		InputStreamReader isr = null;
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			//建立网络连接
			urlObj = new URL(url);
			//打开网络连接
			httpConn = (HttpURLConnection) urlObj.openConnection();
			httpConn.addRequestProperty("User-Agent",  "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36");
			//httpConn.setRequestProperty("Cookie", "foo=bar"); 
			isr = new InputStreamReader(httpConn.getInputStream(), encoding);
			reader = new BufferedReader(isr);
			String temp = null;
			while ((temp=reader.readLine()) != null) {
				sb.append(temp + "\n");
			}
			return sb.toString();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (isr != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	public void setCookie() {
		
	}
	
	
	/**
	 * 提取豆瓣的评论数据
	 * @param url1
	 * @param url2
	 * @param encoding
	 * @return
	 */
	public static String extComments(String url1, String url2, String encoding){
		int start = 0;
		StringBuilder sb = new StringBuilder();
		while (start <= 500) {
			String url = url1+start+url2;
			//String html = getHtmlResourceByURL(url, encoding);
			
			Map<String, String> cookies = new HashMap<>();
			cookies.put("bid", "82WfmVIi9S8");
			cookies.put("gr_user_id", "c591206a-ddd6-41bf-98f8-6191c655606c");
			cookies.put("viewed", "\"1010197_1262816_27041530_1394622\"");
			cookies.put("ps", "y");
			cookies.put("dbcl2", "\"178504730:QJYGTsYU3GU\"");
			cookies.put("ck", "EPwN");
			cookies.put("loc-last-index-location-id", "\"128302\"");
			cookies.put("ll", "\"128302\"");
			cookies.put("_vwo_uuid_v2", "D6C0B989B166B786B3874BA64F28362E8|cd83cb1971be0d71c3f09b9eee4d6411");
			cookies.put("ap", "1");
			cookies.put("_pk_ref.100001.4cf6", "%5B%22%22%2C%22%22%2C1526031029%2C%22https%3A%2F%2Faccounts.douban.com%2Fphone%2Fbind%3Fck%3DEPwN%22%5D");
			cookies.put(" _pk_id.100001.4cf6", "7416c70656d63558.1511239540.9.1526031069.1526029014.");
			cookies.put("_pk_ses.100001.4cf6", "*");
			cookies.put("__utma", "30149280.502361605.1511239540.1526026643.1526031029.27");
			cookies.put("__utmb", "30149280.0.10.1526031029");
			cookies.put("__utmc", "30149280");
			cookies.put("__utmz", "30149280.1526023936.25.23.utmcsr=movie.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/subject/26683723/comments");
			cookies.put("__utmv", "30149280.17850");
			cookies.put("__utma", "223695111.1403456638.1511239540.1526026643.1526031029.9");
			cookies.put("__utmb", "223695111.0.10.1526031029");
			cookies.put("__utmc", "223695111");
			cookies.put("__utmz", "223695111.1526000082.5.5.utmcsr=accounts.douban.com|utmccn=(referral)|utmcmd=referral|utmcct=/phone/bind");
			cookies.put("push_noty_num", "0");
			cookies.put("push_doumail_num", "0");
			
			Document document = null;
			try {
				document = Jsoup.connect(url)
						.header("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)").cookies(cookies) //连接豆瓣时带上cookie就不会被拒绝访问
						.timeout(3000).get();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			try {
				Thread.sleep(1000); //防止爬取太快 豆瓣服务器封ip
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Document document = Jsoup.parse(html);
			Elements elements = document.getElementsByClass("comment-item");
			for (Element element : elements) {
				String comment = element.getElementsByTag("p").text(); //用户评论
				sb.append(comment+"\n");
			}
			start +=20;
		}
		return sb.toString();
	}
	
	/**
	 * 把提取到的评论数据写进一个文件
	 * @param comments
	 * @param encoding
	 */
	public static void writeToFile(String comments, String encoding) {
		File file = new File("D:\\ComputerSoftware\\java\\MyEclipse\\Workspace\\houlai\\data\\houlai.txt");
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		PrintWriter writer = null;
		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos, encoding);
			writer = new PrintWriter(osw);
			writer.print(comments);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}
	
	
	public static void main(String[] args) {
		String url1 = "https://movie.douban.com/subject/26683723/comments?start=";
		String url2 = "&limit=20&sort=new_score&status=P&percent_type=";
		String encoding = "utf-8";
		String comments = extComments(url1, url2, encoding);
		writeToFile(comments, encoding);
	
	}

}
