package lcwben.demo1;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;


public class reqDemo {
	
    @Test  
    public void jUnitTest() {  
        get();  
    }  
  
  
    /** 
     * 发送 get请求 
     */  
    public void get() {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
       
            // 创建httpGet对象
        	
            HttpGet httpGet = new HttpGet("http://www.baidu.com/");
//        	HttpGet httpGet = new HttpGet("http://mirrors.hust.edu.cn/apache//httpcomponents/httpcore/binary/httpcomponents-core-4.4.8-osgi-bin.zip");
            System.out.println("executing request " + httpGet.getURI());  
            
            CloseableHttpResponse response = null; 
            		
            try {
            	//获取response对象
				response = httpclient.execute(httpGet);
				//获取响应实体
				HttpEntity entity = response.getEntity();
				StatusLine status = response.getStatusLine();
				//响应状态
				System.out.println("响应状态：" + status.toString());
				if(status.toString().contains("404")) {
					System.out.println("url反馈成功！！！");
				}
				if(entity != null) {
					// 打印响应内容长度    
                    System.out.println("Response content length: " + entity.getContentLength());  
                    // 打印响应内容    
                    System.out.println("Response content: " + EntityUtils.toString(entity));  
                  
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if(response != null) {response.close();}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
            

    }  
  
 
}
