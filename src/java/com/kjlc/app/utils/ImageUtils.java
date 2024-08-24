package com.kjlc.app.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class ImageUtils {
    
    private static String iconBasePath = "/home/myexamce/appservers/apache-tomcat-10.0.27/webapps/kjlcProductionApp/WEB-INF/classes/static/media/";
    
    public static String save(MultipartFile icon) throws IOException{

        String fileName = FileNameGenerator.generate();
        File thumbnailPath = new File(iconBasePath + fileName + ".png" );
        try {
            FileOutputStream thumbnailOutput = new FileOutputStream(thumbnailPath);
            thumbnailOutput.write(icon.getBytes());
            thumbnailOutput.close();
            thumbnailPath.createNewFile();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return(fileName);
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
