package com.kjlc.app.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class QrUtil {
    private static String iconBasePath = "/home/myexamce/appservers/apache-tomcat-10.0.27/webapps/kjlcProductionApp/WEB-INF/classes/static/images/";
    //private static String iconBasePath = "src/main/resources/static/images/";
    public static String save(MultipartFile icon) throws IOException{
        File thumbnailPath = new File(iconBasePath + "Qr" + ".jpeg" );
        try {
            FileOutputStream thumbnailOutput = new FileOutputStream(thumbnailPath);
            thumbnailOutput.write(icon.getBytes());
            thumbnailOutput.close();
            thumbnailPath.createNewFile();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return("Qr");
    }
    public static int delete(String filename) throws IOException{
        File file = new File(iconBasePath + filename);
        try{
            if(file.delete()){
                return(1);
            }
        }
        catch(Exception e){

        }
        return(0);
    }
}
