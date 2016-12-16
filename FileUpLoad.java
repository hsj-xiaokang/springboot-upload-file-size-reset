package xxx;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@RestController
@RequestMapping("/fileUpLoad")
public class FileUpLoad {
	    @RequestMapping(value="/test", method=RequestMethod.POST    )  
	    public Map<String, Object> ngUpload(HttpServletRequest request,HttpServletResponse res){
	    	
	      System.out.println("in--------------");
	      
	        Map<String, Object> resMap = new HashMap<String, Object>();  
	        resMap.put("result", "error");  
	  
	        //解析器解析request的上下文  
	        CommonsMultipartResolver multipartResolver =  
	            new CommonsMultipartResolver(request.getSession().getServletContext()); 
//	        multipartResolver.setMaxUploadSizePerFile(1024*1024*1024);
//	        multipartResolver.setMaxUploadSize(1024*1024*1024);
//	            FileUpload fu = multipartResolver.getFileUpload();
//	            fu.setHeaderEncoding("UTF-8");////设置上传文件大小大小
//	            fu.setFileSizeMax(1024*1024*1024);
//	            fu.setSizeMax(1024*1024*1024);
	          //先判断request中是否包涵multipart类型的数据，  
	        if(multipartResolver.isMultipart(request)){  
	           //再将request中的数据转化成multipart类型的数据  
	            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;  
	            Iterator iter = multiRequest.getFileNames();  
	            while(iter.hasNext()){  
	                //这里的name为fileItem的alias属性值，相当于form表单中name  
	                String name=(String)iter.next();  
	                System.out.println("--------------------------name:"+name);  
	                //根据name值拿取文件  
	                MultipartFile file = multiRequest.getFile(name); 
	                BufferedOutputStream  bos = null;
	                FileOutputStream outStream = null;
//					try {
						//字节数组转16进制字符串
//						byte[]  bt = file.getBytes();
//						String str = toHexString(bt);
//						str = JSON.toJSONString(str);
						
						//16进制字符串转字节数组
//						byte[] bt_ =  toByteArray((String)JSONObject.parse(str));                        
                        //写到本地
                      //new一个文件对象用来保存图片，默认保存当前工程根目录  
//                        String url = "D:/angularfileupload/";
//                        File imageFile = new File(url + "copy_"+ file.getOriginalFilename() );  
                        //创建输出流  
//                         outStream = new FileOutputStream(imageFile);  
                        //缓冲流
//                        bos = new BufferedOutputStream (outStream);
//                        //写入数据  
////                        outStream.write(bt_);  
//                        //关闭输出流  
////                        outStream.close(); 
//                        byte[] b = new byte[1024*5];//1kb*5[每次创建5mb的大小作为缓冲]
//                        bos.write(b);
                        
                        
//					} catch (IOException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//					finally{
//						//Close the BufferedOutputStream
//			            try {
//			                if (bos != null) {
//			                	bos.flush();
//			                	bos.close();
//			                }
//			                if(outStream != null){//先打开的后关上
//			                	outStream.flush();
//			                	outStream.close();
//			                }
//			            } catch (IOException ex) {
//			                ex.printStackTrace();
//			            }
//					}
                    
	                if(file != null){  
	                    String fileName = file.getOriginalFilename();  
	                    String path = "D:/ionic/" + fileName; //本地 
	                    File localFile = new File(path);  
	                    if(!localFile.getParentFile().exists()) {  
	                         //如果目标文件所在的目录不存在，则创建父目录  
	                         localFile.getParentFile().mkdirs();  
	                         System.out.println("---------------parent:"+localFile.getParentFile().getPath());  
	                     }  
	                    //写文件到本地  
	                    try {  
	                        file.transferTo(localFile); 
	                       

	                    } catch (IOException e) {  
	                        e.printStackTrace();  
	                        return resMap;  
	                    }  
	                }  
	            }  
	          }else {  
	              return resMap;  
	        }  
	          resMap.put("result", "success");  
	          return resMap;  
	    }
	    
	    
	    /**
	     * 16进制的字符串表示转成字节数组
	     * 
	     * @param hexString
	     *            16进制格式的字符串
	     * @return 转换后的字节数组
	     **/
	    public static byte[] toByteArray(String hexString) {
	     if (StringUtils.isEmpty(hexString))
	      throw new IllegalArgumentException("this hexString must not be empty");
	    
	     hexString = hexString.toLowerCase();
	     final byte[] byteArray = new byte[hexString.length() / 2];
	     int k = 0;
	     for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
	      byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
	      byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
	      byteArray[i] = (byte) (high << 4 | low);
	      k += 2;
	     }
	     return byteArray;
	    }
	    
	    /**
	     * 字节数组转成16进制表示格式的字符串
	     * 
	     * @param byteArray
	     *            需要转换的字节数组
	     * @return 16进制表示格式的字符串
	     **/
	    public static String toHexString(byte[] byteArray) {
	     if (byteArray == null || byteArray.length < 1)
	      throw new IllegalArgumentException("this byteArray must not be null or empty");
	    
	     final StringBuilder hexString = new StringBuilder();
	     for (int i = 0; i < byteArray.length; i++) {
	      if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
	       hexString.append("0");
	      hexString.append(Integer.toHexString(0xFF & byteArray[i]));
	     }
	     return hexString.toString().toLowerCase();
	    }
}
