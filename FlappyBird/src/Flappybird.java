import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Flappybird extends JFrame implements ActionListener,KeyListener {
	boolean play=false;
	private final int height =800;
	private final int width=800;
	
	int delay=15;
	int columnspeed=10;
	int birdspeed=50;
	int birdfallspeed=3;
	int birdpos=height/2-10;
	int score;
	Random rand;
	ArrayList<Rectangle> columns;
	
	Timer timer = new Timer(delay,this); 
	Flappybird()
	{  
	   this.setTitle("FLAPPY BIRD");
		this.setSize(height, width);
		this.setVisible(true);
		//this.setResizable(false);
		this.addKeyListener(this);
		this.setFocusable(true);
		this.setFocusTraversalKeysEnabled(false);
		columns = new ArrayList<>();
		rand = new Random();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);    // on clicking the close button,the program terminates
		createcolumns(true);
		createcolumns(true);
		
		timer.start();
	}
	public void createcolumns(boolean start){
		int space=300;
		int colwidth=100;
		int colheight=25+rand.nextInt(300);
		if(start){
		columns.add(new Rectangle(width+colwidth+columns.size()*300, height-colheight-180,colwidth,colheight));
		columns.add(new Rectangle(width+colwidth+(columns.size()-1)*300, 0,colwidth,height-colheight-180-space));

		}
		else{
			columns.add(new Rectangle(columns.get(columns.size()-1).x+600, height-colheight-180,colwidth,colheight));
			columns.add(new Rectangle(columns.get(columns.size()-1).x, 0,colwidth,height-colheight-180-space));

		}
		}
	public void paintcolumns(Graphics g,Rectangle col)
	{ g.setColor(Color.GREEN.darker());
	  g.fillRect(col.x, col.y, col.width, col.height);
	 	
	}
	@Override
	public void paint(Graphics g)
	{   //background
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, width, height);
		//bird
		g.setColor(Color.RED);
		g.fillRect(width/2-10,birdpos,20,20);
		//bottom
		g.setColor(Color.ORANGE.darker());    // .darker() makes your color more dark
		g.fillRect(0, height-150, width,150);
		//grass
		g.setColor(Color.GREEN);
		g.fillRect(0, height-180, width, 30);
		//display score
		g.setColor(Color.WHITE);
		Font font = new Font("Comic Sans MS", Font.BOLD, 65);
		g.setFont(font);
		g.drawString("SCORE   "+score,width/2-50, 100);
		
		for(int i=0;i<columns.size();i++)
		{
			paintcolumns(g,columns.get(i));
		}
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(play==true){
		birdpos=birdpos+birdfallspeed;
		for(int i=0;i<columns.size();i++)
		{   
			Rectangle rect=columns.get(i);
			rect.x=rect.x-columnspeed;
		}
		for(int i=0;i<columns.size();i++)
		{ Rectangle rect = columns.get(i);
		   if(rect.y==0&&width/2-10+10>rect.x+rect.width/2-columnspeed/2&&width/2-10+10<=rect.x+rect.width/2+columnspeed/2){ // ensures that middle of bird lies in the range of length columnspeed
			   score++;
			   repaint();
		   }
		    if(new Rectangle(width/2-10,birdpos,20,20).intersects(rect)||birdpos<0||birdpos>height-180)
		    {
		    	int choice = JOptionPane.showConfirmDialog(this,"Your score is"+score+"Do you want to restart");
		    	if(choice==JOptionPane.YES_OPTION){  // instead of using JOptionPane,we can also set font and use g.drawstring to display GAME OVER
		    		score=0;
		    		columns=new ArrayList<>();
		    		play=false;
		    		birdpos=height/2-10;
		    		createcolumns(true);
		    		createcolumns(true);
		    		repaint();
		    		
		    	}
		    	else{ 
		    		play=false;
		    		super.dispose();
		    	}
		    }
			if(rect.x+100<0)                  // 100 specifies colwidth
			{   
				columns.remove(rect);         // one column is removed and one more is added
				if(rect.y==0)
				{ createcolumns(false); }   
				
			}
		}
		repaint();
		}
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP){
			play=true;
			birdpos=birdpos-birdspeed;
			repaint();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}

}
