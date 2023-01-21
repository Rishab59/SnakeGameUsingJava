/*
 * Developer's Name : Rishab.H
 * This code is developed using Eclipse IDE.
 */

import javax.swing.JFrame ;


public class GameFrame extends JFrame
{

	GameFrame()
	{
	
		this.add(new GamePanel()) ;
		this.setTitle("Snake Game By Rishab") ;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
		
		this.setResizable(false) ;
		this.pack() ; 
		// Fits everything to the window
		
		this.setVisible(true) ;
		this.setLocationRelativeTo(null) ;
		// Appear in the middle of the screen
	
	}

}
