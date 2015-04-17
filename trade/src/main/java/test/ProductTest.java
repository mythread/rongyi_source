package test;

import java.io.File;

import junit.framework.TestCase;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;

public class ProductTest {

    @Test
    public void saveProduct(){
        String filePath = "src/main/resources/applicationContext.xml";

        ApplicationContext ac = new FileSystemXmlApplicationContext(filePath);
        
      SessionFactory sf =  (SessionFactory) ac.getBean("sessionFactory");
    }
}
