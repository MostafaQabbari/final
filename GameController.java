package fruitninja.controller;

import fruitninja.model.*;
import fruitninja.view.ApplicationView;
import fruitninja.view.EndGameView;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Random;

public class GameController extends JFrame {

	// Model
	private Field field;
	//private Player player ;
	Player player = Player.getInstance();
	// View
	private ApplicationView appView;
	private EndGameView endGameView;

	// Controller
	private Timer timer;
	public static int time;
	private int maxTime;
	private Clip bgSound;
	private Clip slashSound;
	Random rg;

	public int getTime() {
		return time;
	}

	/**
	 * Sets initial values, initializes the playing field, loads audio and sets
	 * up the panels in the frame.
	 */

	Context context1 = new Context(new NormalAdd());
	Context context2 = new Context(new SpecialAdd());
	JButton reStart = new JButton("Restart");

	//Player player = Player.getInstance();
	public GameController() {
		super("Fruitninja V1.0");
		// Set default values
		maxTime = 180000;
		time = 0;

		// Load objects
		rg = new Random();
		//Player player = Player.getInstance();
		field = new Field();
		//player = new Player();
		loadAudio();
		// Load possible views
		appView = new ApplicationView(field, player);
		endGameView = new EndGameView(player);
		// Setup view
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		add(appView);
		pack();
	}

	/**
	 * Loads the audio resources into the audio clips.
	 */
	private void loadAudio() {
		try {
			bgSound = AudioSystem.getClip();
			bgSound.open(AudioSystem.getAudioInputStream(
					new BufferedInputStream(getClass().getResourceAsStream("/fruitninja/resources/game_music.wav"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			slashSound = AudioSystem.getClip();
			slashSound.open(AudioSystem.getAudioInputStream(
					new BufferedInputStream(getClass().getResourceAsStream("/fruitninja/resources/slash.wav"))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Generates a new GameObject and assigns it a random starting location and
	 * speed.
	 *
	 * @return A new GameObject which can be added to the Playing Field.
	 */
	private GameObject newObject() {
		// Create random GameObject
		int rand = rg.nextInt(7);
		GameObject object;
		switch (rand) {
			case 0:
				object = new Apple();
				break;
			case 1:
				object = new Orange();
				break;
			case 2:
				object = new Strawberry();
				break;
			case 3:
				object = new Bomb();
				break;
			case 4:
				object = new sandia();
				break;
			case 5:
				object = new Blue();
				break;
			default:
				object = new Bomb2();
				break;
		}
		// Set random position and speed
		rand = rg.nextInt(4);
		int maxPos = 250 - object.getSize();
		int pos = rg.nextInt(maxPos);
		int velX = rg.nextInt(3) + 1;
		int velY = rg.nextInt(5) + 2;
		switch (rand) {
			case 0:
				object.setPosition(pos, 250 + maxPos);
				object.setVelocity(velX, -velY - 7);
				break;
			case 1:
				object.setPosition(pos + maxPos, 250 + maxPos);
				object.setVelocity(-velX, -velY - 7);
				break;
			case 2:
				object.setPosition(0, maxPos + pos);
				object.setVelocity(velX, -velY - (pos / 30));
				break;

			default:
				object.setPosition(maxPos + 250, maxPos + pos);
				object.setVelocity(-velX, -velY - (pos / 30));
				break;
		}
		return object;
	}

	/**
	 * This method is used when the game is over to show the EndGameView (player
	 * score) inside the frame.
	 */
	private void gameOver() {
		// Stop the update timer
		timer.stop();
		bgSound.close();
		// Replace the view with game over screen
		int score = player.getScore();
		int high_score = player.load_high_score();
		if(score>high_score)
		{
			try {
				player.save_highscore_to_file(score);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		reStart.setBounds(100, 450, 140, 40);
		reStart.setLayout(null);
		remove(appView);
		add(reStart);
		add(endGameView);
		repaint();
		reStart.addActionListener(arg0 -> {
			this.setVisible(false);
			remove(endGameView);
			GameController controller = new GameController();

			// Center the frame on monitor
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			Dimension frameSize = controller.getSize();
			controller.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
			// Show the application on screen
			controller.setVisible(true);
			// Run the game
			controller.run();


		});
	}

	/**
	 * This method is the main method to run the game. It initializes the Mouse
	 * listeners and starts the sound and game timer.
	 */
	public void run() {
		// Start background music
		bgSound.loop(Clip.LOOP_CONTINUOUSLY);
		// Setup a GameObject in the field
		field.setObject(newObject());

		// Add a mouselistener
		appView.getComponent(1).addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// Check and record if a player slashes the GameObject in the
				// field
				if (field.getObject().insideBounds(e.getX(), e.getY())) {
					field.getSlash().setSlashed(true);
				}
			}

		});

		appView.getComponent(1).addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// Record the starting position when the mouse is clicked
				field.getSlash().setStartPosition(e.getX(), e.getY());
				field.getSlash().setSlashed(false);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// Set the end position when the mouse is released
				field.getSlash().setEndPosition(e.getX(), e.getY());
				if (field.getSlash().isSlashed()) {
					// Make a slash sound
					slashSound.stop();
					slashSound.setFramePosition(0);
					slashSound.start();
					// Add score or subtract the player's life
					if (field.getObject().getType() == 6 || field.getObject().getType() == 5) {
						player.addLife();
						int score = player.getScore();
						int new_score = context2.executeStrategy(score);
						player.setScore(new_score);
					}
					if (field.getObject().getType() == 4) {
						gameOver();}
					if (field.getObject().getType() == 3) {
						player.setLives(player.getLives()-1);
						if (player.getLives() <= 0)
							// Player is dead, show the end screen.
							gameOver();
					} else {
						int score = player.getScore();
						int new_score = context1.executeStrategy(score);
						player.setScore(new_score);
					}
					field.setObject(newObject());
				}
			}


			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});

		timer = new Timer(16, new ActionListener() {
			long lastTime = System.currentTimeMillis();
			long delta = 0L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// Check time since last update
				delta = System.currentTimeMillis() - lastTime;
				lastTime = System.currentTimeMillis();
				// Update the objects position
				field.getObject().update(delta);
				// Check the position of the object on the field
				int x = field.getObject().getX();
				int y = field.getObject().getY();
				int size = field.getObject().getSize();
				// Create a new object when the object is off-screen
				if ((x < 0) || (y < 0) || (x + size >= 500) || (y + size >= 500)) {
					player.subtractLife(field.getObject().getType());
					if (player.getLives()==0){
						gameOver();
					}
					field.setObject(newObject());
				}
				repaint();
				// Increase the game time
				time += delta;
				// Check playing time
				if (time >= maxTime) {
					gameOver();
				}
			}

		});
		timer.start();

	}
	public void run2() {
		// Start background music
		bgSound.loop(Clip.LOOP_CONTINUOUSLY);
		// Setup a GameObject in the field
		field.setObject(newObject());

		// Add a mouselistener
		appView.getComponent(1).addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// Check and record if a player slashes the GameObject in the
				// field
				if (field.getObject().insideBounds(e.getX(), e.getY())) {
					field.getSlash().setSlashed(true);
				}
			}

		});

		appView.getComponent(1).addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// Record the starting position when the mouse is clicked
				field.getSlash().setStartPosition(e.getX(), e.getY());
				field.getSlash().setSlashed(false);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// Set the end position when the mouse is released
				field.getSlash().setEndPosition(e.getX(), e.getY());
				if (field.getSlash().isSlashed()) {
					// Make a slash sound
					slashSound.stop();
					slashSound.setFramePosition(0);
					slashSound.start();
					// Add score or subtract the player's life
					if (field.getObject().getType() == 6 || field.getObject().getType() == 5) {
						player.addLife();
						int score = player.getScore();
						int new_score = context2.executeStrategy(score);
						player.setScore(new_score);
					}
					if (field.getObject().getType() == 4) {
						gameOver();}
					if (field.getObject().getType() == 3) {
						player.setLives(player.getLives()-1);
						if (player.getLives() <= 0)
							// Player is dead, show the end screen.
							gameOver();
					} else {
						int score = player.getScore();
						int new_score = context1.executeStrategy(score);
						player.setScore(new_score);
					}
					field.setObject(newObject());
				}
			}


			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

		});

		timer = new Timer(16, new ActionListener() {
			long lastTime = System.currentTimeMillis();
			long delta = 0L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// Check time since last update
				delta = System.currentTimeMillis() - lastTime;
				lastTime = System.currentTimeMillis();
				// Update the objects position
				field.getObject().update(delta);
				// Check the position of the object on the field
				int x = field.getObject().getX();
				int y = field.getObject().getY();
				int size = field.getObject().getSize();
				// Create a new object when the object is off-screen
				if ((x < 0) || (y < 0) || (x + size >= 500) || (y + size >= 500)) {
					player.subtractLife(field.getObject().getType());
					if (player.getLives()==0){
						gameOver();
					}
					field.setObject(newObject());
				}
				repaint();
				// Increase the game time
				time += delta;
				// Check playing time
				if (time >= 60000) {
					gameOver();
				}
			}

		});
		timer.start();

	}
}
