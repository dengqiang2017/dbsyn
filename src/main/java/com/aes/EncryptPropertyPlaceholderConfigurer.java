package com.aes;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
/**
 * 配置文件解密,运行在xml配置数据库驱动之前,对数据库用户名密码进行解密后传递给DataSource下的参数
 * @author dengqiang 
 *
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer{
    private String[] encryptPropNames = {"jdbc.username", "jdbc.password","mysql.username","mysql.password"};
    @Override  
    protected String convertProperty(String propertyName, String propertyValue){
        //如果在加密属性名单中发现该属性  
        if (isEncryptProp(propertyName)){
            String decryptValue="";
            try {
                decryptValue = AESEncryptor.decrypt(propertyValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return decryptValue==""?propertyValue:decryptValue;
        }else {
            return propertyValue;
        }
    }

    private boolean isEncryptProp(String propertyName){
        for (String encryptName : encryptPropNames){
            if (encryptName.equals(propertyName)){
                return true;
            }
        }
        return false;
    }
}