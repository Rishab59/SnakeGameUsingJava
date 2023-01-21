/*
 * Developer's Name : Rishab.H
 * This code is developed using Eclipse IDE.
 */

import javax.swing.* ;
import java.awt.* ;
import java.awt.event.* ;
import java.util.Random ;


public class GamePanel extends JPanel implements ActionListener
{

	static final int SCREEN_WIDTH = 600 ;
	static final int SCREEN_HEIGHT = 600 ;
	
	static final int UNIT_SIZE = 25 ;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE ;
	static final int DELAY = 80 ;
	
	final int [] x = new int [GAME_UNITS] ; // Stores the X - Coordinates of the body part.
	final int [] y = new int [GAME_UNITS] ; // Stores the Y - Coordinates of the body part.
	
	int bodyParts = 6 ;
	int applesEaten = 0 ;
	int appleX ;
	int appleY ;
	char direction = 'R' ; // goes right when game starts
	boolean running = false ;
	
	int gameOver = 0 ;
	
	Timer timer ;
	Random random ;

	
	GamePanel ()
	{
		
		random = new Random () ;
		this.setPreferredSize (new Dimension (SCREEN_WIDTH, SCREEN_HEIGHT)) ;
		this.setBackground (Color.black) ;
		this.setFocusable (true) ;
		this.addKeyListener (new MyKeyAdapter ()) ;
		startGame () ;
	
	}
	
	
	public void startGame ()
	{
		
		newApple () ;
		running = true ;
		timer = new Timer (DELAY, this) ;
		timer.start () ;
	
	}
	
	public void paintComponent (Graphics g)
	{
		
		super.paintComponent (g) ;
		draw (g) ;
	
	}
	
	public void draw (Graphics g)
	{
		
		if (running)
		{
			
			// Just to make coding easy, this doesn't going to affect the output.
			for (int i = 0 ; i < (SCREEN_HEIGHT / UNIT_SIZE) ; i++)
			{
				g.drawLine ((i * UNIT_SIZE), 0, (i * UNIT_SIZE), SCREEN_HEIGHT) ;
				g.drawLine (0, (i * UNIT_SIZE), SCREEN_WIDTH, (i * UNIT_SIZE)) ;
			}
		
			g.setColor (Color.red) ;
			g.fillOval (appleX, appleY, UNIT_SIZE, UNIT_SIZE) ;
		
			for (int i = 0 ; i < bodyParts ; i++)
			{
				if (i == 0) // Head of the Snake.
				{
					g.setColor (Color.green) ;
					g.fillRect (x[i], y[i], UNIT_SIZE, UNIT_SIZE) ;
				}
				else // Body of the Snake.
				{
					g.setColor (new Color (45, 180, 0)) ; // A Different Shade of green.
					if (bodyParts > 11)
					{
						g.setColor (new Color (random.nextInt (255), random.nextInt (255), random.nextInt (255))) ;
						// Generates a random Color for body after Score 5.
					}
					g.fillRect (x[i], y[i], UNIT_SIZE, UNIT_SIZE) ;
				}
			}
			
			g.setColor (Color.red) ;
			g.setFont (new Font ("Consolas", Font.BOLD, 40)) ;
			FontMetrics metrics = getFontMetrics (g.getFont ()) ;
			g.drawString ("Score : " + applesEaten, ((SCREEN_WIDTH - metrics.stringWidth ("Score : " + applesEaten))) / 2, g.getFont ().getSize () ) ;
		}
		
		else
		{
			gameOver (g) ;
		}
	}
	
	public void newApple ()
	{
		appleX = ( random.nextInt ( (int) (SCREEN_WIDTH / UNIT_SIZE)) ) * UNIT_SIZE ;
		appleY = ( random.nextInt ( (int) (SCREEN_HEIGHT / UNIT_SIZE)) ) * UNIT_SIZE ;
	}
	
	public void move ()
	{
		for (int i = bodyParts ; i > 0 ; i--)
		{
			x[i] = x[i - 1] ; 
			y[i] = y[i - 1] ;
			// Shifting the coordinates by 1.
		}
		
		switch (direction)
		{
			case 'U' : // Up
				y[0] = y[0] - UNIT_SIZE ;
				break ;
			case 'D' : // Down
				y[0] = y[0] + UNIT_SIZE ;
				break ;
			case 'L' : // Left
				x[0] = x[0] - UNIT_SIZE ;
				break ;
			case 'R' : // Right
				x[0] = x[0] + UNIT_SIZE ;
				break ;
		}
	}
	
	public void checkApple ()
	{
		if ((x[0] == appleX) && (y[0] == appleY))
		{
			bodyParts++ ;
			applesEaten++ ;
			newApple () ;
		}
	}
	
	public void checkCollisions ()
	{
		// This loop checks if the head collides with the body
		for (int i = bodyParts ; i > 0 ; i--)
		{
			if ((x[0] == x[i]) && (y[0] == y[i]))
			{
				running = false ;
			}
		}
		
		// Check if the head collides with the left border.
		if (x[0] < 0)
		{
			running = false ;
		}
		
		// Check if the head collides with the right border.
		if (x[0] > SCREEN_WIDTH)
		{
			running = false ;
		}
		
		// Check if the head collides with the top border.
		if(y[0] < 0)
		{
			running = false ;
		}
		
		// Check if the head collides with the bottom border.
		if (y[0] > SCREEN_HEIGHT)
		{
			running = false ;
		}
		
		if (! running)
		{
			timer.stop () ;
		}
	}
	
	public void gameOver (Graphics g)
	{
		// GameOver Text
		g.setColor (Color.red) ;
		g.setFont (new Font ("Consolas", Font.BOLD, 20)) ;
		FontMetrics metrics = getFontMetrics (g.getFont ()) ;
		g.drawString ("Game Over Thanks for playing - By Rishab's Snake Game", (SCREEN_WIDTH - metrics.stringWidth ("Game Over Thanks for playing - By Rishab's Snake Game")) / 2, SCREEN_HEIGHT / 2 ) ;
		// Placing the String in the center.
		
		// Display Score
		g.drawString ("Score : " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth ("Score : " + applesEaten)) / 2, (SCREEN_HEIGHT / 2) + 30 ) ;
		
		g.drawString ("Press \"Enter\" Key to retry", (SCREEN_WIDTH - metrics.stringWidth ("Press \"Enter\" Key to retry")) / 2, (SCREEN_HEIGHT / 2) + 60 ) ;
		gameOver++ ;
	}
	
	@Override
	public void actionPerformed (ActionEvent e) 
	{
		
		// TODO Auto-generated method stub
		if (running)
		{
			move () ;
			checkApple () ;
			checkCollisions () ;
		}
		repaint () ;
	
	}

	
	public class MyKeyAdapter extends KeyAdapter // Inner Class
	{
		
		@Override
		public void keyPressed (KeyEvent e)
		{
			switch(e.getKeyCode ())
			{
				case KeyEvent.VK_LEFT :
					if(direction != 'R')
					{
						direction = 'L' ;
					}
					break ;
				case KeyEvent.VK_RIGHT :
					if(direction != 'L')
					{
						direction = 'R' ;
					}
					break ;
				case KeyEvent.VK_UP :
					if(direction != 'D')
					{
						direction = 'U' ;
					}
					break ;
				case KeyEvent.VK_DOWN :
					if(direction != 'U')
					{
						direction = 'D' ;
					}
					break ;
			}
			
			if((gameOver > 0) && (e.getKeyCode () == KeyEvent.VK_ENTER))
			{
				new  GameFrame () ;
			}
		}
	
	}

}
