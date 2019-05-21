package fruitninja.view;

import fruitninja.model.Field;
import fruitninja.model.GameObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameView extends JPanel {

	private Field field;
	private BufferedImage bg;
	private ImageIcon[] images;

	/**
	 * Sets up the dimensions of the panel and loads the images to be used in
	 * the rendering process.
	 */
	public GameView(Field field) {
		super();
		// Set dimensions for the view
		Dimension dim = new Dimension(500, 500);
		setMaximumSize(dim);
		setMinimumSize(dim);
		setPreferredSize(dim);
		setSize(dim);
		// Load field
		this.field = field;
		// Load background
		try {
			bg = ImageIO.read(getClass().getResource("/fruitninja/resources/background.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Load images for the GameObjects
		images = new ImageIcon[7];
		images[0] = createIcon("/fruitninja/resources/apple.png", "apple");
		images[1] = createIcon("/fruitninja/resources/orange.png", "orange");
		images[2] = createIcon("/fruitninja/resources/strawberry.png", "strawberry");
		images[3] = createIcon("/fruitninja/resources/bomb.png", "bomb");
		images[4] = createIcon("/fruitninja/resources/boom.png", "boom");
		images[5] = createIcon("/fruitninja/resources/sandia.png", "Sandia");
		images[6] = createIcon("/fruitninja/resources/blue.png", "blue");
	}

	/**
	 * This method is used to create a new ImageIcon based on the path and
	 * desription in the parameters.
	 *
	 * @param path
	 *            The path to the image as registered in the classpath (path
	 *            after /src/).
	 * @param description
	 *            Description for the image.
	 * @return A new ImageIcon with the image as defined in the path argument.
	 */
	private ImageIcon createIcon(String path, String description) {
		java.net.URL imgURL = getClass().getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL, description);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}

	/**
	 * This method is overridden from JPanel, it is used by swing to paint the
	 * view. It renders the GameObject on the game field.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// Draw the background
		g.drawImage(bg, 0, 0, this);
		// Draw hte GameObject on screen
		GameObject obect = field.getObject();
		images[obect.getType()].paintIcon(this, g, obect.getX(), obect.getY());
	}

}
