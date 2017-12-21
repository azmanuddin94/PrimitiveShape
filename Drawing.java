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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.plaf.ColorUIResource;

import javax.sound.sampled.*;

import java.io.File;
import java.io.IOException;


public class Drawing
{
	private static int shapeOption=0,fillShapeOption=1;
	private static int width=100,height=100,ovalHeight=70;
	private static int xpos,ypos;
	private static int xposT1=50,xposT2=100,xposT3=0,yposT1=0,yposT2=100,yposT3=100;
	private static int xSpeed=3,ySpeed=0;
	private static final int UPDATE_PERIOD = 10;
	private static int condi=0;
	private static Timer timer;
	private static int option=0;
	private static Boolean move=false;
	private static Boolean automove=false;
	
	private JFrame mainFrame;
	private JPanel basePanel;
	private JPanel lowestPanel;
	private JPanel resizeShapePanel;
	private JPanel resizeShapeIconPanel;
	private JPanel clearPanel;
	private JPanel clearIconPanel;
	private JPanel moveShapePanel;
	private JPanel moveShapeIconPanel;
	private JPanel drawingPanel;
	private JPanel shapePanel;
	private JPanel shapeIconPanel;
	private JPanel fillShapePanel;
	private JPanel fillShapeIconPanel;
	private customDrawPanel outcomePanel;
	
	private ButtonGroup shapeButton;
	private JToggleButton rectangle;
	private JToggleButton circle;
	private JToggleButton triangle;
	private JToggleButton oval;
	
	private JButton up;
	private JButton left;
	private JButton down;
	private JButton right;
	private JToggleButton auto;
	private JButton small;
	private JButton big;
	private JButton clear;
	
	private ButtonGroup fillOption;
	private JRadioButton fill;
	private JRadioButton unfill;
	
	public Drawing()
	{
		prepareGUI();
	}
	private void prepareGUI()
	{
		mainFrame= new JFrame("Assignment 2");
		mainFrame.setSize(700,730);
		mainFrame.getContentPane().setBackground(Color.white);
		mainFrame.setLocation(305,40);
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//the whole background panel inside the frame
		basePanel= new JPanel();
		basePanel.setBackground(Color.white);
		basePanel.setLayout(new BorderLayout());
		mainFrame.add(basePanel);
		
		//the base panel at lower part
		lowestPanel= new JPanel();
		lowestPanel.setLayout(new GridLayout(1,3));
		lowestPanel.setPreferredSize(new Dimension(0,160));
		lowestPanel.setBackground(Color.white);
		basePanel.add(lowestPanel,BorderLayout.SOUTH);
		
		//base panel of lower part divide to three section: resizeShapePanel,clearPanel,moveShapePanel
		resizeShapePanel=new JPanel();
		resizeShapePanel.setLayout(new BorderLayout());
		resizeShapePanel.setBackground(Color.white);
		resizeShapePanel.add(new JLabel("Resize Shape",JLabel.CENTER),BorderLayout.NORTH);
		
		clearPanel=new JPanel();
		clearPanel.setLayout(new BorderLayout());
		clearPanel.setBackground(Color.white);
		clearPanel.add(new JLabel("Clear Panel",JLabel.CENTER),BorderLayout.NORTH);
		
		moveShapePanel=new JPanel();
		moveShapePanel.setLayout(new BorderLayout());
		moveShapePanel.setBackground(Color.white);
		moveShapePanel.add(new JLabel("Move Shape",JLabel.CENTER),BorderLayout.NORTH);
		
		lowestPanel.add(resizeShapePanel);
		lowestPanel.add(clearPanel);
		lowestPanel.add(moveShapePanel);
		
		//base panel at upper part
		drawingPanel=new JPanel();
		drawingPanel.setLayout(new BorderLayout());
		drawingPanel.setBorder(new LineBorder(Color.BLACK,5));
		drawingPanel.setBackground(Color.WHITE);
		basePanel.add(drawingPanel,BorderLayout.CENTER);
		
		//panel shows all result after selecting function: draw out shape,resize,clear and also move
		outcomePanel=new customDrawPanel();
		outcomePanel.setBackground(Color.white);
		drawingPanel.add(outcomePanel,BorderLayout.CENTER);
		
		//panel for the toolsbar at right hand side
		shapePanel=new JPanel();
		shapePanel.setLayout(new BorderLayout());
		shapePanel.setBackground(Color.white);
		shapePanel.setPreferredSize(new Dimension(150,0));
		shapePanel.setBorder(BorderFactory.createMatteBorder(0,5,0,0,Color.BLACK));
		shapePanel.add(new JLabel("Shape",JLabel.CENTER),BorderLayout.NORTH);
		drawingPanel.add(shapePanel,BorderLayout.EAST);
		
		//panel for the JRadioButton: fill & unfill
		fillShapePanel=new JPanel();
		fillShapePanel.setLayout(new BorderLayout());
		fillShapePanel.setBorder(BorderFactory.createMatteBorder(5,0,0,0,Color.BLACK));
		fillShapePanel.setBackground(Color.white);
		fillShapePanel.setPreferredSize(new Dimension(0,110));
		fillShapePanel.add(new JLabel("Fill Shape",JLabel.CENTER),BorderLayout.NORTH);
		shapePanel.add(fillShapePanel,BorderLayout.SOUTH);
		
		//set color when button clicked
		UIManager.put("ToggleButton.select", Color.LIGHT_GRAY);
		UIManager.put("Button.select",new Color(153,208,253));
		
		mainFrame.setVisible(true);
	}
	private void setUpShapeButton()
	{
		//panel to divide the four shape position equally
		shapeIconPanel=new JPanel();
		shapeIconPanel.setLayout(new GridLayout(4,1,0,1));
		shapeIconPanel.setBackground(Color.white);
		shapePanel.add(shapeIconPanel,BorderLayout.CENTER);
		
		//GridBagLayout used to set the position and size of button
		JPanel rectangleIconPanel=new JPanel();
		rectangleIconPanel.setLayout(new GridBagLayout());
		GridBagConstraints gba=new GridBagConstraints();
		rectangleIconPanel.setBackground(Color.white);
		shapeIconPanel.add(rectangleIconPanel);
		
		//positioning of button
		gba.fill=GridBagConstraints.HORIZONTAL;
		gba.gridx=0;
		gba.gridy=0;
		
		rectangle=new JToggleButton(new ImageIcon("pic\\square_filled.png"));
		rectangle.setBackground(Color.WHITE);
		rectangle.setFocusPainted(false);
		rectangle.setBorder(BorderFactory.createEmptyBorder());
		rectangle.addActionListener(new buttonClicked());
		rectangleIconPanel.add(rectangle,gba);
			
		//GridBagLayout used to set the position and size of button
		JPanel circleIconPanel=new JPanel();
		circleIconPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbb=new GridBagConstraints();
		circleIconPanel.setBackground(Color.WHITE);
		shapeIconPanel.add(circleIconPanel);
		
		//positioning of button
		gbb.fill=GridBagConstraints.HORIZONTAL;
		gbb.gridx=0;
		gbb.gridy=0;
		
		circle=new JToggleButton(new ImageIcon("pic\\circle_filled.png"));
		circle.setBackground(Color.WHITE);
		circle.setFocusPainted(false);
		circle.setBorder(BorderFactory.createEmptyBorder());
		circle.addActionListener(new buttonClicked());
		circleIconPanel.add(circle,gbb);
		
		//GridBagLayout used to set the position and size of button
		JPanel triangleIconPanel=new JPanel();
		triangleIconPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		triangleIconPanel.setBackground(Color.white);
		shapeIconPanel.add(triangleIconPanel);
		
		//positioning of button
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridx=0;
		gbc.gridy=0;
		
		triangle=new JToggleButton(new ImageIcon("pic\\triangle_filled.png"));
		triangle.setBackground(Color.WHITE);
		triangle.setFocusPainted(false);
		triangle.setBorder(BorderFactory.createEmptyBorder());
		triangle.addActionListener(new buttonClicked());
		triangleIconPanel.add(triangle,gbc);
		
		//GridBagLayout used to set the position and size of button
		JPanel ovalIconPanel=new JPanel();
		ovalIconPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbd=new GridBagConstraints();
		ovalIconPanel.setBackground(Color.white);
		shapeIconPanel.add(ovalIconPanel);
		
		//positioning of button
		gbd.fill=GridBagConstraints.HORIZONTAL;
		gbd.gridx=0;
		gbd.gridy=0;
		
		oval=new JToggleButton(new ImageIcon("pic\\oval_filled.png"));
		oval.setBackground(Color.WHITE);
		oval.setFocusPainted(false);
		oval.setBorder(BorderFactory.createEmptyBorder());
		oval.addActionListener(new buttonClicked());
		ovalIconPanel.add(oval,gbd);
		
		//Group all four shape button together
		shapeButton=new ButtonGroup();
		shapeButton.add(rectangle);
		shapeButton.add(circle);
		shapeButton.add(triangle);
		shapeButton.add(oval);
		
		mainFrame.setVisible(true);
	}
	private void setUpRadioButton()
	{
		//panel to divide the position of two option equally
		fillShapeIconPanel=new JPanel();
		fillShapeIconPanel.setLayout(new GridLayout(2,1));
		fillShapeIconPanel.setBackground(Color.WHITE);
		fillShapePanel.add(fillShapeIconPanel,BorderLayout.CENTER);
		
		fill=new JRadioButton("Fill");
		fill.setFocusPainted(false);
		fill.setBackground(Color.WHITE);
		fill.setSelected(true);
		fill.addActionListener(new buttonClicked());
				
		unfill=new JRadioButton("Unfill");
		unfill.setFocusPainted(false);
		unfill.setBackground(Color.WHITE);
		unfill.addActionListener(new buttonClicked());
				
		fillShapeIconPanel.add(fill);
		fillShapeIconPanel.add(unfill);
		
		//Group two option radioButton together
		fillOption=new ButtonGroup();
		fillOption.add(fill);
		fillOption.add(unfill);
		
		mainFrame.setVisible(true);
	}
	private void setUpResizeShapeButton()
	{
		//GridBagLayout used to set the position and size of button
		resizeShapeIconPanel=new JPanel();
		resizeShapeIconPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		resizeShapeIconPanel.setBackground(Color.white);
		resizeShapePanel.add(resizeShapeIconPanel,BorderLayout.CENTER);
		
		small=new JButton(new ImageIcon("pic\\smaller_blue_basic.png"));
		small.setBackground(Color.WHITE);
		small.setFocusPainted(false);
		small.addActionListener(new buttonClicked());
		small.setBorder(BorderFactory.createEmptyBorder());
		
		big=new JButton(new ImageIcon("pic\\bigger_blue_basic.png"));
		big.setBackground(Color.WHITE);
		big.setFocusPainted(false);
		big.addActionListener(new buttonClicked());
		big.setBorder(BorderFactory.createEmptyBorder());
		
		//positioning of button
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridx=0;
		gbc.gridy=0;
		resizeShapeIconPanel.add(small,gbc);
		
		//positioning of button
		gbc.gridx=1;
		gbc.gridy=0;
		resizeShapeIconPanel.add(big,gbc);
		mainFrame.setVisible(true);
	}
	private void setUpClearButton()
	{
		clearIconPanel=new JPanel();
		clearIconPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		clearIconPanel.setBackground(Color.white);
		clearPanel.add(clearIconPanel,BorderLayout.CENTER);
		
		clear=new JButton(new ImageIcon("pic\\clear.png"));
		clear.setBackground(Color.WHITE);
		clear.setFocusPainted(false);
		clear.setBorder(BorderFactory.createEmptyBorder());
		clear.addActionListener(new buttonClicked());
		
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridx=0;
		gbc.gridy=0;
		clearIconPanel.add(clear,gbc);
		
		mainFrame.setVisible(true);
		
	}
	private void setUpMoveShapeButton()
	{
		//GridBagLayout used to set the position and size of button
		moveShapeIconPanel=new JPanel();
		moveShapeIconPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc=new GridBagConstraints();
		moveShapeIconPanel.setBackground(Color.white);
		moveShapePanel.add(moveShapeIconPanel,BorderLayout.CENTER);
		
		up=new JButton(new ImageIcon("pic\\up.png"));
		up.setBackground(Color.WHITE);
		up.setPreferredSize(new Dimension(50,50));
		up.setFocusPainted(false);
		up.addActionListener(new buttonClicked());
		up.setBorder(BorderFactory.createEmptyBorder());
		
		left=new JButton(new ImageIcon("pic\\left.png"));
		left.setBackground(Color.WHITE);
		left.setPreferredSize(new Dimension(50,50));
		left.setFocusPainted(false);
		left.addActionListener(new buttonClicked());
		left.setBorder(BorderFactory.createEmptyBorder());
		
		down=new JButton(new ImageIcon("pic\\down.png"));
		down.setBackground(Color.WHITE);
		down.setPreferredSize(new Dimension(50,50));
		down.setFocusPainted(false);
		down.addActionListener(new buttonClicked());
		down.setBorder(BorderFactory.createEmptyBorder());
		
		right=new JButton(new ImageIcon("pic\\right.png"));
		right.setBackground(Color.WHITE);
		right.setPreferredSize(new Dimension(50,50));
		right.setFocusPainted(false);
		right.addActionListener(new buttonClicked());
		right.setBorder(BorderFactory.createEmptyBorder());
		
		auto=new JToggleButton(new ImageIcon("pic\\auto.png"));
		auto.setBackground(Color.WHITE);
		auto.setPreferredSize(new Dimension(50,50));
		auto.setFocusPainted(false);
		auto.addActionListener(new buttonClicked());	
		auto.setBorder(BorderFactory.createEmptyBorder());
		
		//positioning of button
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridx=1;
		gbc.gridy=0;
		moveShapeIconPanel.add(up,gbc);

		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridx=0;
		gbc.gridy=1;
		moveShapeIconPanel.add(left,gbc);
		gbc.gridx=1;
		gbc.gridy=1;
		moveShapeIconPanel.add(auto,gbc);
		gbc.gridx=2;
		gbc.gridy=1;
		moveShapeIconPanel.add(right,gbc);
		
		gbc.fill=GridBagConstraints.HORIZONTAL;
		gbc.gridx=1;
		gbc.gridy=2;
		moveShapeIconPanel.add(down,gbc);
		
		mainFrame.setVisible(true);
	}
	private class buttonClicked implements ActionListener
	{
		//actionListener to perform timer.start();
		@Override
		public void actionPerformed(ActionEvent e) 
		{
			ActionListener updateTask = new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e) 
				{
					if(shapeOption==option)
					 {
						update();   // update the (x, y) position
						outcomePanel.repaint();
					 }
				}
			};
			
			if(e.getSource()==auto)
			{
				try {
		         // Open an audio input stream.
		         File soundFile = new File ("sound\\automatic.wav");
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         if (clip.isRunning())
	       	   {
	      	 clip.stop();			        	                	
	             }
	       else{ clip.start();   
	              clip.setFramePosition(0);
	           }
		      } catch (UnsupportedAudioFileException r){
		         r.printStackTrace();
		      } catch (IOException r) {
		         r.printStackTrace();
		      } catch (LineUnavailableException r) {
		         r.printStackTrace();
		      }
				condi+=1; //variable to control the switch on and off of the button
				outcomePanel.requestFocusInWindow();
				if(condi%2!=0)
				{
					timer=new Timer(UPDATE_PERIOD, updateTask);
					timer.start();
				}
				else
				{
					timer.stop();
				}
			}
			else if(e.getSource()==rectangle)
			{
				try {
		         // Open an audio input stream.
		         File soundFile = new File ("sound\\rectangle.wav");
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         if (clip.isRunning())
	       	   {
	      	 clip.stop();			        	                	
	             }
	       else{ clip.start();   
	              clip.setFramePosition(0);
	           }
		      } catch (UnsupportedAudioFileException r){
		         r.printStackTrace();
		      } catch (IOException r) {
		         r.printStackTrace();
		      } catch (LineUnavailableException r) {
		         r.printStackTrace();
		      }
				 shapeOption=1;
		         width=100;
		         height=100;
		         outcomePanel.requestFocusInWindow();

			}
			else if(e.getSource()==circle)
			{
				try {
		         // Open an audio input stream.
		         File soundFile = new File ("sound\\circle.wav");
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         if (clip.isRunning())
	       	   {
	      	 clip.stop();			        	                	
	             }
	       else{ clip.start();   
	              clip.setFramePosition(0);
	           }
		      } catch (UnsupportedAudioFileException r){
		         r.printStackTrace();
		      } catch (IOException r) {
		         r.printStackTrace();
		      } catch (LineUnavailableException r) {
		         r.printStackTrace();
		      }
				shapeOption=2;
		        width=100;
		        height=100;
		        outcomePanel.requestFocusInWindow();
			}
			else if(e.getSource()==triangle)
			{
				try {
		         // Open an audio input stream.
		         File soundFile = new File ("sound\\triangle.wav");
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         if (clip.isRunning())
	       	   {
	      	 clip.stop();			        	                	
	             }
	       else{ clip.start();   
	              clip.setFramePosition(0);
	           }
		      } catch (UnsupportedAudioFileException r){
		         r.printStackTrace();
		      } catch (IOException r) {
		         r.printStackTrace();
		      } catch (LineUnavailableException r) {
		         r.printStackTrace();
		      }
				 shapeOption=3;
				 width=100;
			     height=100;
		         xposT1=50;
		         xposT2=100;
		         xposT3=0;
		         yposT1=0;
		         yposT2=100;
		         yposT3=100;
		         outcomePanel.requestFocusInWindow();
			}
			else if(e.getSource()==oval)
			{
				try {
		         // Open an audio input stream.
		         File soundFile = new File ("sound\\oval.wav");
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         if (clip.isRunning())
	       	   {
	      	 clip.stop();			        	                	
	             }
	       else{ clip.start();   
	              clip.setFramePosition(0);
	           }
		      } catch (UnsupportedAudioFileException r){
		         r.printStackTrace();
		      } catch (IOException r) {
		         r.printStackTrace();
		      } catch (LineUnavailableException r) {
		         r.printStackTrace();
		      }
				shapeOption=4;
		        width=100;
		        ovalHeight=70;
		        outcomePanel.requestFocusInWindow();
			}
			else if(e.getSource()==clear)
			{
				try {
		         // Open an audio input stream.
		         File soundFile = new File ("sound\\clear.wav");
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         if (clip.isRunning())
	       	   {
	      	 clip.stop();			        	                	
	             }
	       else{ clip.start();   
	              clip.setFramePosition(0);
	           }
		      } catch (UnsupportedAudioFileException r){
		         r.printStackTrace();
		      } catch (IOException r) {
		         r.printStackTrace();
		      } catch (LineUnavailableException r) {
		         r.printStackTrace();
		      }
				move=false;
				shapeOption=0;
				outcomePanel.requestFocusInWindow();
				outcomePanel.repaint();
			}
			else if(e.getSource()==fill)
			{
				try {
		         // Open an audio input stream.
		         File soundFile = new File ("sound\\fill.wav");
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         if (clip.isRunning())
	       	   {
	      	 clip.stop();			        	                	
	             }
	       else{ clip.start();   
	              clip.setFramePosition(0);
	           }
		      } catch (UnsupportedAudioFileException r){
		         r.printStackTrace();
		      } catch (IOException r) {
		         r.printStackTrace();
		      } catch (LineUnavailableException r) {
		         r.printStackTrace();
		      }
				fillShapeOption=1;
				outcomePanel.requestFocusInWindow();
				outcomePanel.repaint();
			}
			else if(e.getSource()==unfill)
			{
				try {
		         // Open an audio input stream.
		         File soundFile = new File ("sound\\unfill.wav");
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         if (clip.isRunning())
	       	   {
	      	 clip.stop();			        	                	
	             }
	       else{ clip.start();   
	              clip.setFramePosition(0);
	           }
		      } catch (UnsupportedAudioFileException r){
		         r.printStackTrace();
		      } catch (IOException r) {
		         r.printStackTrace();
		      } catch (LineUnavailableException r) {
		         r.printStackTrace();
		      }
				fillShapeOption=2;
				outcomePanel.requestFocusInWindow();
				outcomePanel.repaint();
			}
			else if(e.getSource()==up)
			{
				try {
		         // Open an audio input stream.
		         File soundFile = new File ("sound\\up.wav");
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         if (clip.isRunning())
	       	   {
	      	 clip.stop();			        	                	
	             }
	       else{ clip.start();   
	              clip.setFramePosition(0);
	           }
		      } catch (UnsupportedAudioFileException r){
		         r.printStackTrace();
		      } catch (IOException r) {
		         r.printStackTrace();
		      } catch (LineUnavailableException r) {
		         r.printStackTrace();
		      }	
				ypos=ypos-10;
				outcomePanel.requestFocusInWindow();
				outcomePanel.repaint();
			}
			else if(e.getSource()==left)
			{
				try {
		         // Open an audio input stream.
		         File soundFile = new File ("sound\\left.wav");
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         if (clip.isRunning())
	       	   {
	      	 clip.stop();			        	                	
	             }
	       else{ clip.start();   
	              clip.setFramePosition(0);
	           }
		      } catch (UnsupportedAudioFileException r){
		         r.printStackTrace();
		      } catch (IOException r) {
		         r.printStackTrace();
		      } catch (LineUnavailableException r) {
		         r.printStackTrace();
		      }	
				xpos=xpos-10;
				outcomePanel.requestFocusInWindow();
				outcomePanel.repaint();
			}
			else if(e.getSource()==right)
			{
				try {
		         // Open an audio input stream.
		         File soundFile = new File ("sound\\right.wav");
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         if (clip.isRunning())
	       	   {
	      	 clip.stop();			        	                	
	             }
	       else{ clip.start();   
	              clip.setFramePosition(0);
	           }
		      } catch (UnsupportedAudioFileException r){
		         r.printStackTrace();
		      } catch (IOException r) {
		         r.printStackTrace();
		      } catch (LineUnavailableException r) {
		         r.printStackTrace();
		      }	
				xpos=xpos+10;
				outcomePanel.requestFocusInWindow();
				outcomePanel.repaint();
			}
			else if(e.getSource()==down)
			{
				try {
		         // Open an audio input stream.
		         File soundFile = new File ("sound\\down.wav");
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         if (clip.isRunning())
	       	   {
	      	 clip.stop();			        	                	
	             }
	       else{ clip.start();   
	              clip.setFramePosition(0);
	           }
		      } catch (UnsupportedAudioFileException r){
		         r.printStackTrace();
		      } catch (IOException r) {
		         r.printStackTrace();
		      } catch (LineUnavailableException r) {
		         r.printStackTrace();
		      }	
				ypos=ypos+10;
				outcomePanel.requestFocusInWindow();
				outcomePanel.repaint();
			}
			else if(e.getSource()==small)
			{
				try {
		         // Open an audio input stream.
		         File soundFile = new File ("sound\\smaller.wav");
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         if (clip.isRunning())
	       	   {
	      	 clip.stop();			        	                	
	             }
	       else{ clip.start();   
	              clip.setFramePosition(0);
	           }
		      } catch (UnsupportedAudioFileException r){
		         r.printStackTrace();
		      } catch (IOException r) {
		         r.printStackTrace();
		      } catch (LineUnavailableException r) {
		         r.printStackTrace();
		      }
				//restriction on the size of shape
				if(width>25&&(height>25||ovalHeight>16))
				{
					width=width/2;
					height=height/2;
					ovalHeight=ovalHeight/2;
					xposT1=xposT1/2;
					xposT2=xposT2/2;
					xposT3=xposT3/2;
					yposT1=yposT1/2;
					yposT2=yposT2/2;
					yposT3=yposT3/2;
					outcomePanel.requestFocusInWindow();
					outcomePanel.repaint();
				}
			}
			else if(e.getSource()==big)
			{
				try {
		         // Open an audio input stream.
		         File soundFile = new File ("sound\\larger.wav");
		         AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
		         // Get a sound clip resource.
		         Clip clip = AudioSystem.getClip();
		         // Open audio clip and load samples from the audio input stream.
		         clip.open(audioIn);
		         if (clip.isRunning())
	       	   {
	      	 clip.stop();			        	                	
	             }
	       else{ clip.start();   
	              clip.setFramePosition(0);
	           }
		      } catch (UnsupportedAudioFileException r){
		         r.printStackTrace();
		      } catch (IOException r) {
		         r.printStackTrace();
		      } catch (LineUnavailableException r) {
		         r.printStackTrace();
		      }
				outcomePanel.requestFocusInWindow();
				checkEnlargeCondition();   //restriction on the size of shape
			}
		}
		public void checkEnlargeCondition()
		{
			//restriction on the size of shape
			outcomePanel.requestFocusInWindow();
			
			
			if((shapeOption==4 && fillShapeOption==1)||(shapeOption==4 && fillShapeOption==2))  
			{
				//condition when oval selected
				if(width<outcomePanel.getWidth()-width&&width<281&&ovalHeight<outcomePanel.getHeight()-ovalHeight)
				{
					width=width*2;
					height=height*2;
					ovalHeight=ovalHeight*2;
					xposT1=xposT1*2;
					xposT2=xposT2*2;
					xposT3=xposT3*2;
					yposT1=yposT1*2;
					yposT2=yposT2*2;
					yposT3=yposT3*2;
					outcomePanel.requestFocusInWindow();
					outcomePanel.repaint();
					if((xpos>outcomePanel.getWidth()-width))
	       	 		{
						xpos=outcomePanel.getWidth()-width-1;
						outcomePanel.requestFocusInWindow();
						outcomePanel.repaint();
	       	 		}
					if((ypos>outcomePanel.getHeight()-ovalHeight))
					{
						ypos=outcomePanel.getHeight()-ovalHeight-1;
						outcomePanel.requestFocusInWindow();
						outcomePanel.repaint();
					}
				}
			}
			else
			{
				//condition when other shape selected
				if(width<outcomePanel.getWidth()-width&&height<outcomePanel.getHeight()-height)
				{
					width=width*2;
					height=height*2;
					ovalHeight=ovalHeight*2;
					xposT1=xposT1*2;
					xposT2=xposT2*2;
					xposT3=xposT3*2;
					yposT1=yposT1*2;
					yposT2=yposT2*2;
					yposT3=yposT3*2;
					outcomePanel.requestFocusInWindow();
					outcomePanel.repaint();
					if((xpos>outcomePanel.getWidth()-width))
	       	 		{
						xpos=outcomePanel.getWidth()-width-1;
						outcomePanel.requestFocusInWindow();
						outcomePanel.repaint();
	       	 		}
					if((ypos>outcomePanel.getHeight()-height))
					{
						ypos=outcomePanel.getHeight()-height-1;
						outcomePanel.requestFocusInWindow();
						outcomePanel.repaint();
					}
				}
			}
		}
		public void update() 
		{
			// update the (x, y) position and control the movement of shape
			xpos += xSpeed;
		    ypos += ySpeed;
		    if((shapeOption==4 && fillShapeOption==1)||(shapeOption==4 && fillShapeOption==2))
		    {
		    	if (xpos > outcomePanel.getWidth()-width || xpos < 0) 
		    	{
		    		xSpeed = -xSpeed;
		    	}
		    	if (ypos >outcomePanel.getHeight()-ovalHeight || ypos < 0) 
		    	{
		    		ySpeed = -ySpeed;
		    	}
		    }
		    else
		    {
		    	if (xpos > outcomePanel.getWidth()-width  || xpos < 0) 
		    	{
		    		xSpeed = -xSpeed;
		    	}
		    	if (ypos > outcomePanel.getHeight()-height || ypos < 0) 
		    	{
		    		ySpeed = -ySpeed;
		    	}
		    }
		}
	}
	private static class customDrawPanel extends JPanel implements MouseListener,MouseMotionListener,KeyListener
	{
		//customize panel that inherit JPanel to modify the paintComponent Function
		//implemented MouseListener, MouseMotionListener and also KeyListener
		Boolean dragged=false;
		public customDrawPanel()
		{
			addKeyListener(this);
			this.setFocusable(true);
			addMouseListener(this);
			addMouseMotionListener(this);
		}
		@Override
		public void keyPressed(KeyEvent e) 
		{
			// TODO Auto-generated method stub
			//keyboard functioning code
			switch(e.getKeyCode())
			{
				case KeyEvent.VK_DOWN:
				{
					ypos=ypos+10;
					break;
				}
				case KeyEvent.VK_UP:
				{
					ypos=ypos-10;
					break;
				}
				case KeyEvent.VK_LEFT:
				{
					xpos=xpos-10;
					break;
				}
				case KeyEvent.VK_RIGHT:
				{
					xpos=xpos+10;
					break;
				}
			}
			requestFocusInWindow();
			repaint();
			frameBorderConstraint();         //restriction on border outcomePanel
		}
		@Override
		public void keyReleased(KeyEvent e) 
		{}
		@Override
		public void keyTyped(KeyEvent e) 
		{}
		@Override
		public void mouseDragged(MouseEvent e) 
		{
			 // TODO Auto-generated method stub
	         xpos = e.getX();
	         ypos = e.getY();
	         dragged=true;
	         repaint();
	         frameBorderConstraint();        //restriction on border outcomePanel
		}
		@Override
		public void mousePressed(MouseEvent e)
		{
			// TODO Auto-generated method stub
			move=true;
		}
		@Override
		public void mouseReleased(MouseEvent e)
		{}
		@Override
		public void mouseEntered(MouseEvent e)
		{
			// TODO Auto-generated method stub
			if(option!=shapeOption)
			{
				move=false;
			}
		}
		@Override
		public void mouseExited(MouseEvent e)
		{}
		@Override
		public void mouseClicked(MouseEvent e)
		{}
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			if(!move)
			{
				xpos = e.getX();
				ypos = e.getY();
				dragged=true;
				repaint();
				frameBorderConstraint();    //restriction on border outcomePanel
			}
		}
		@Override
		public void paintComponent(Graphics g)
		{
			frameBorderConstraint();        //restriction on border outcomePanel
			
			if(shapeOption==1 && fillShapeOption==1)
			{
				option=1;
				if(dragged)
				{
					super.paintComponent(g);
					g.setColor(Color.BLUE);
					g.fillRect(xpos, ypos, width, height);
				}
			}
			else if(shapeOption==1 && fillShapeOption==2)
			{
				option=1;
				super.paintComponent(g);
				g.setColor(Color.BLUE);
				g.drawRect(xpos, ypos, width, height);
			}
			else if(shapeOption==2 && fillShapeOption==1)
			{
				option=2;
				if(dragged)
				{
					super.paintComponent(g);
					g.setColor(Color.BLUE);
					g.fillOval(xpos, ypos, width, height);
				}
			}
			else if(shapeOption==2 && fillShapeOption==2)
			{
				option=2;
				super.paintComponent(g);
				g.setColor(Color.BLUE);
				g.drawOval(xpos, ypos, width, height);
			}
			else if(shapeOption==3 && fillShapeOption==1)
			{
				option=3;
				if(dragged)
				{
					super.paintComponent(g);
					g.setColor(Color.BLUE);
					g.fillPolygon(new int[]{xpos+xposT1,xpos+xposT2,xpos+xposT3},new int[]{ypos+yposT1,ypos+yposT2,ypos+yposT3},3);
				}
			}
			else if(shapeOption==3 && fillShapeOption==2)
			{
				option=3;
				super.paintComponent(g);
				g.setColor(Color.BLUE);
				g.drawPolygon(new int[]{xpos+xposT1,xpos+xposT2,xpos+xposT3},new int[]{ypos+yposT1,ypos+yposT2,ypos+yposT3},3);
			}
			else if(shapeOption==4 && fillShapeOption==1)
			{
				option=4;
				if(dragged)
				{
					super.paintComponent(g);
					g.setColor(Color.BLUE);
					g.fillOval(xpos, ypos, width, ovalHeight);
				}
			}
			else if(shapeOption==4 && fillShapeOption==2)
			{
				option=4;
				super.paintComponent(g);
				g.setColor(Color.BLUE);
				g.drawOval(xpos, ypos, width, ovalHeight);
			}
			else if(shapeOption==0 && fillShapeOption==1)
			{
				super.paintComponent(g);
			}
			else if(shapeOption==0 && fillShapeOption==2)
			{
				super.paintComponent(g);
			}
		}
		public void frameBorderConstraint()
		{
			//restriction on border outcomePanel
			if((shapeOption==4 && fillShapeOption==1)||(shapeOption==4 && fillShapeOption==2))
			{
				if((xpos>getWidth()-width))
				{
					xpos=getWidth()-width-1;
					requestFocusInWindow();
					repaint();
				}
				if(xpos<0)
	        	{
	        		xpos=0;
	        		repaint();
	        	}
				if((ypos>getHeight()-ovalHeight))
				{
					ypos=getHeight()-ovalHeight-1;
					requestFocusInWindow();
					repaint();
				}
				if(ypos<0)
	        	{
	        		ypos=0;
	        		repaint();
	        	}	
			}
		    else
		    {
		        if((xpos>getWidth()-width))
		        {
					xpos=getWidth()-width-1;
					requestFocusInWindow();
					repaint();
				}
		        if(xpos<0)
	        	{
	        		xpos=0;
	        		repaint();
	        	}
		        if((ypos>getHeight()-height))
		        {
					ypos=getHeight()-height-1;
					requestFocusInWindow();
					repaint();
		        }
		        if(ypos<0)
	        	{
	        		ypos=0;
	        		repaint();
	        	}
		     }
		}
	}	
	public static void main(String[] args)
	{
		Drawing obj=new Drawing();
		obj.setUpShapeButton();
		obj.setUpRadioButton();
		obj.setUpResizeShapeButton();
		obj.setUpClearButton();
		obj.setUpMoveShapeButton();
	}
}