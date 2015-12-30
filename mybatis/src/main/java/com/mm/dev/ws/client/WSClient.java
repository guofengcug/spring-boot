package com.mm.dev.ws.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WSClient {
	
	public static void main(String[] args) throws Exception {
		
        //服务的地址
        URL wsUrl = new URL("http://localhost:9000/services/holidayService");
        
        HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();
        
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        OutputStream os = conn.getOutputStream();
        
        //请求体
        String soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
        		+ "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" "
        		+ "xmlns:sch=\"http://mycompany.com/hr/schemas\""
        		+ " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" "
        		+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + 
                      "<soap:Body> <Holiday><arg0>aaa</arg0>  </Holiday> </soap:Body> </soap:Envelope>";
        
        soap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:sch=\"http://mycompany.com/hr/schemas\">"
		        	+"<soapenv:Header/>"
		        	+ "<soapenv:Body>"
		        	+ "<sch:HolidayRequest>"
		        	+ "<sch:Holiday>"
		            + "<sch:StartDate>2012-10-12</sch:StartDate>"
		            + "<sch:EndDate>2012-10-13</sch:EndDate>"
		            + "</sch:Holiday>"
		            + "<sch:Employee>"
		            + "<sch:Number>1</sch:Number>"
		            + "<sch:FirstName>Gary</sch:FirstName>"
		            + "<sch:LastName>Guo</sch:LastName>"
		            + "</sch:Employee>"
		            + "</sch:HolidayRequest>"
		            + "</soapenv:Body>"
		            + "</soapenv:Envelope>";
        os.write(soap.getBytes());
        InputStream is = conn.getInputStream();
        
        byte[] b = new byte[1024];
        int len = 0;
        String s = "";
        while((len = is.read(b)) != -1) {
            String ss = new String(b,0,len,"UTF-8");
            s += ss;
        }
        
        System.out.println(s);
        
        is.close();
        os.close();
        conn.disconnect();
    }

}
