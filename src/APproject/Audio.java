/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APproject;

import java.io.File;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author DELL
 */
public class Audio {
    
    public static void sound(){
    String path = "Audio\\transaction.wav";   
    Media letterSound = new Media(new File(path).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
    mediaPlayer.play();
    }
    
    
    public static void sound1(){
    String path = "Audio\\error.wav";  
    Media letterSound = new Media(new File(path).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(letterSound);
    mediaPlayer.play();
    }
}
