//****************************************************************************
// Assignment: Program (Project)
//
// Authors and matrix number: 	Azrie Bin Joe@ Abdullah			(46380)
//								Norhaslin binti Naton       	(48103) 
//								Mohd Azman Bin Uddin        	(42217)
//
// Honour code: We pledge that this program represents our own program code.
// We received help from (Mohd Saifuddin and Internet)in understanding and debugging our program.
//****************************************************************************

import java.awt.*;
import javax.swing.*;
import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;

public class SplashScreen extends JWindow {
  private int duration;
  public SplashScreen(int d) {
    duration = d;
  }

  // A simple little method to show a title screen in the center
  // of the screen for the amount of time given in the constructor
  public void showSplash() {
    JPanel content = (JPanel)getContentPane();
    content.setBackground(Color.white);

    // Set the window's bounds, centering the window
    int width = 600;
    int height =338;
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width-width)/2;
    int y = (screen.height-height)/2;
    setBounds(x,y,width,height);

    // Build the splash screen
    JLabel label = new JLabel(new ImageIcon("pic\\LOGAN.gif"));
    JLabel copyrt = new JLabel
      ("LOGAN", JLabel.CENTER);
    copyrt.setFont(new Font("Sans-Serif", Font.BOLD, 12));
    content.add(label, BorderLayout.CENTER);
    content.add(copyrt, BorderLayout.SOUTH);
    Color oraRed = new Color(156, 20, 20,  255);
    content.setBorder(BorderFactory.createLineBorder(oraRed, 3));

    // Display it
    setVisible(true);

    // Wait a little while, maybe while loading resources
    try { Thread.sleep(duration); } catch (Exception e) {}

    setVisible(false);
  }

  public void showSplashAndExit() {
    showSplash();
    System.exit(0);
  }

  public static void main(String[] args) {
    // Throw a nice little title page up on the screen first
    SplashScreen splash = new SplashScreen(2500);

    try {
	         // Open an audio input stream.
	         File soundFile = new File ("sound\\claw.wav");
	         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
	         // Get a sound clip resource.
	         Clip clip = AudioSystem.getClip();
	         // Open audio clip and load samples from the audio input stream.
	         clip.open(audioIn);
	         clip.stop();
	         clip.setFramePosition(0);
	         clip.start(); 
	      } catch (UnsupportedAudioFileException p) {
	         p.printStackTrace();
	      } catch (IOException p) {
	         p.printStackTrace();
	      } catch (LineUnavailableException p) {
	         p.printStackTrace();
	      }
	  splash.showSplashAndExit();
  }
}
