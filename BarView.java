package fruitninja.view;

import fruitninja.controller.GameController;
import fruitninja.model.Player;

import javax.swing.*;
import java.awt.*;


public class BarView extends JPanel {


	private Player player;
	private Font mainFont;
	RenderingHints rh;

	public BarView(Player player) {
		super();
		// Set dimensions for the view
		Dimension dim = new Dimension(500, 50);
		setMaximumSize(dim);
		setMinimumSize(dim);
		setPreferredSize(dim);
		setSize(dim);
		this.player = player;
		// Setup rendering options
		mainFont = new Font("TimesNew", 0, 20);
		rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}

	/**
	 * This method is overridden from JPanel, it is used by swing to paint the
	 * view. It renders the player's score and lives in a bar.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHints(rh);
		g2.setColor(Color.black);
		g2.fillRect(0, 0, getWidth(), getHeight());
		// Draw score and remaining lives in white
		g2.setColor(Color.white);
		g2.setFont(mainFont);
		g2.drawString("Score: " + player.getScore(), 40, (getHeight() / 2) + (mainFont.getSize() / 2));
		g2.drawString("Time: " + GameController.time/1000, 210, (getHeight() / 2) + (mainFont.getSize() / 2));
		g2.drawString("Lives: " + player.getLives(), 350, (getHeight() / 2) + (mainFont.getSize() / 2));
	}

}
