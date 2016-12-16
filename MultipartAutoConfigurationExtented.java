package xxx;

import javax.servlet.MultipartConfigElement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
@Configuration
public class MultipartAutoConfigurationExtented extends MultipartAutoConfiguration{
	
	 @Autowired
	 private MultipartProperties multipartProperties;
     @Override
     public MultipartConfigElement multipartConfigElement() {
    	 this.multipartProperties.setMaxFileSize("1073741824");//1048576==1mb  --->1mb*1024 = 1048576 *1024 ==1073741824mb[1G]
    	 this.multipartProperties.setMaxRequestSize("1073741824");
    	 this.multipartProperties.setFileSizeThreshold("1073741824");
    	 this.multipartProperties.setEnabled(true);
 		return this.multipartProperties.createMultipartConfig();
 	}
     @Override
     public StandardServletMultipartResolver multipartResolver() {
 		return new StandardServletMultipartResolver();
 	}
}
