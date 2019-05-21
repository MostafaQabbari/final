package fruitninja.view;

import fruitninja.model.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class EndGameView extends JPanel {


	private Player player;
	private Font mainFont;
	private RenderingHints rh;
	private BufferedImage Image;



	/**
	 * Sets up the dimensions and rendering options for the panel.
	 */
	public EndGameView(Player player) {
		super();
		// Set dimensions for the view
		Dimension dim = new Dimension(550, 550);
		setMaximumSize(dim);
		setMinimumSize(dim);
		setPreferredSize(dim);
		setSize(dim);
		// Load player
		this.player = player;
		// Setup rendering options
		mainFont = new Font("TimesNew", 0, 40);
		rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}


	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		try {
			Image = ImageIO.read(getClass().getResourceAsStream("/fruitninja/resources/background.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		g2.drawImage(Image,0,0,550,550,null);
		g2.setRenderingHints(rh);
		// Fill the screen with gray
		/**g2.setColor(Color.gray);
		g2.fillRect(0, 0,550, 550);*/
		// Show Game over and end score in white
		g2.setColor(Color.white);
		g2.setFont(mainFont);
		g.drawString("Game Over", 80, (getHeight()/4) );
		g.drawString("Score: " + player.getScore(), 80, (getHeight() / 2) );
		g.drawString("High Score: " + player.load_high_score(), 80, (getHeight() / 3) *2);

	}

}
