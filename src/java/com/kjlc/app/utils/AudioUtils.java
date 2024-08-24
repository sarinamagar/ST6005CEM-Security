package com.kjlc.app.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class AudioUtils {
    
    private static String iconBasePath = "/home/myexamce/appservers/apache-tomcat-10.0.27/webapps/kjlcProductionApp/WEB-INF/classes/static/media/";
    
    public static String save(MultipartFile audio) throws IOException{

        String fileName = FileNameGenerator.generate();
        File audioPath = new File(iconBasePath + fileName + ".mp3" );
        try {
            FileOutputStream audioOutput = new FileOutputStream(audioPath);
            audioOutput.write(audio.getBytes());
            audioOutput.close();
            audioPath.createNewFile();
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return(fileName);
    }
}
