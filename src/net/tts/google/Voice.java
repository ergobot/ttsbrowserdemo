package net.tts.google;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import org.omg.CORBA.portable.InputStream;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Voice implements Runnable{
    private static final String TEXT_TO_SPEECH_SERVICE = 
            "http://translate.google.com/translate_tts";
    private static final String USER_AGENT =  
            "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:11.0) " +
            "Gecko/20100101 Firefox/11.0";

    private String words;
    
    public Voice(String words){
    	this.words = words;
    }
    
    public Voice(){}
    
    private void main(String input) throws Exception {


        String language = "english";
        String text = input;
        text = URLEncoder.encode(text, "utf-8");
        System.out.println("Completed");
        this.go(language, text);
        
        
    }

    public void go(String language, String text) throws Exception {

    	String strUrl = "http://tts-api.com/tts.mp3?q=" + text;
        
        URL url = new URL(strUrl);

        // Etablish connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Get method
        connection.setRequestMethod("GET");
        // Set User-Agent to "mimic" the behavior of a web browser. In this 
        // example, I used my browser's info
        connection.addRequestProperty("User-Agent", USER_AGENT);
        connection.connect();

        // Get content
        BufferedInputStream bufIn = 
                new BufferedInputStream(connection.getInputStream());
        byte[] buffer = new byte[1024];
        int n;
        ByteArrayOutputStream bufOut = new ByteArrayOutputStream();
        while ((n = bufIn.read(buffer)) > 0) {
            bufOut.write(buffer, 0, n);
        }

        // Done, save data
        File output = new File("output.mp3");
        BufferedOutputStream out = 
                new BufferedOutputStream(new FileOutputStream(output));
        out.write(bufOut.toByteArray());
        out.flush();
        out.close();
        System.out.println("Done");

        WebDriver webDriver = new FirefoxDriver();
        
        webDriver.get("file://"+ new File("output.mp3").getAbsolutePath());
        
        
        Thread.sleep(30000);
        webDriver.close();
        
        
    }

    public static void out(String input) throws Exception{
    	Voice v = new Voice();
    	v.run(input);
    	
    }
    
	public void run(String input) throws Exception {
		
		this.main(input);
	}

	public void run() {
		
		
	}

    
}

