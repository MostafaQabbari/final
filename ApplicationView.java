package fruitninja.view;

import fruitninja.model.Field;
import fruitninja.model.Player;

import javax.swing.*;


public class ApplicationView extends JPanel {

	private GameView gameView;
	private BarView barView;
	private BoxLayout layout;

	/**
	 * Sets up the BarView and Gameview, then adds them to the Application view
	 * using a BoxLayout to layout the panel.
	 */
	public ApplicationView(Field field, Player player) {
		super();
		// Set a layout for the panel
		layout = new BoxLayout(this, BoxLayout.PAGE_AXIS);
		setLayout(layout);
		// Load the views
		gameView = new GameView(field);
		barView = new BarView(player);
		// Setup view
		setSize(500, gameView.getHeight() + barView.getHeight());
		add(barView);
		add(gameView);
	}

}
