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
     * ���� get���� 
     */  
    public void get() {  
        CloseableHttpClient httpclient = HttpClients.createDefault();  
       
            // ����httpGet����
        	
            HttpGet httpGet = new HttpGet("http://www.baidu.com/");
//        	HttpGet httpGet = new HttpGet("http://mirrors.hust.edu.cn/apache//httpcomponents/httpcore/binary/httpcomponents-core-4.4.8-osgi-bin.zip");
            System.out.println("executing request " + httpGet.getURI());  
            
            CloseableHttpResponse response = null; 
            		
            try {
            	//��ȡresponse����
				response = httpclient.execute(httpGet);
				//��ȡ��Ӧʵ��
				HttpEntity entity = response.getEntity();
				StatusLine status = response.getStatusLine();
				//��Ӧ״̬
				System.out.println("��Ӧ״̬��" + status.toString());
				if(status.toString().contains("404")) {
					System.out.println("url�����ɹ�������");
				}
				if(entity != null) {
					// ��ӡ��Ӧ���ݳ���    
                    System.out.println("Response content length: " + entity.getContentLength());  
                    // ��ӡ��Ӧ����    
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
