package com.kjlc.app.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Date;

import javax.imageio.ImageIO;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class CertificateUtil {

    public static String generateCertificate(String name, Date from, Date to) throws IOException, FontFormatException {
        Resource resource = new ClassPathResource("static/images/certificate.png");
        Resource fontResource = new ClassPathResource("static/assets/font/Beyonce-demo.ttf");
            
        File file = resource.getFile();
        final BufferedImage image = ImageIO.read(file);

        Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontResource.getFile()).deriveFont(Font.ITALIC | Font.BOLD,26f);
    
        Graphics g = image.getGraphics();
        g.setFont(customFont);
        g.setColor(Color.BLACK);
        g.drawString(name, 445, 534);
        g.drawString(from.toString(), 590, 644);
        g.drawString(to.toString(), 900, 644);
        g.dispose();

        String filename = FileNameGenerator.generate();
    
        // ImageIO.write(image, "png", new File("/home/myexamce/appservers/apache-tomcat-10.0.27/webapps/kjlcProductionApp/WEB-INF/classes/static/media/"+filename + ".png"));
        ImageIO.write(image, "png", new File("src/main/resources/static/media/"+filename + ".png"));
        return(filename);
    }

    public static void main(String[] args) throws IOException, FontFormatException {
        Date date = new Date(2000000);
        generateCertificate("Saugat Singh", date, date);

    }

}