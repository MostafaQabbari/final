package fruitninja.model;

import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Player {

	private int score;
	private int lives;
	private int high_score ;

	/**
	 * Sets the default values for the player, the player always starts with 3
	 * lives and a score of 0.
	 */
	private static Player player = new Player();
	private Player()
	{
		this.score = 0;
		this.lives = 5;
	}
	public static Player getInstance(){
		return player;
	}







	/**
	 * Returns the player's current score.
	 *
	 * @return Player's current score.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Adds to the player's score.
	 *
	 * @param score
	 *            The amount to be added to the player's score.
	 */
	public void addScore(int score) {
		this.score += score;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	/**
	 * Subtracts a life from the player.
	 */
	public void subtractLife(int type) {
		if (!(type==3||type==4))
		{
			lives--;

		}
	}
	public void addLife() {
		lives++;
	}

	/**
	 * Returns the amount of lives the player has left.
	 *
	 * @return The amount of lives the player has left.
	 */
	public int getLives() {
		return lives;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void save_highscore_to_file (int highscore) throws IOException
	{
		// Accept a string
		int HS = highscore;
		String SHS = Integer.toString(HS);

		// read character wise from string and write
		// into FileWriter
		try ( // attach a file to FileWriter
			  FileWriter fw = new FileWriter("Score.txt")) {
			// read character wise from string and write
			// into FileWriter
			for (int i = 0; i < SHS.length(); i++) {
				fw.write(SHS.charAt(i));
			}
			//close the file
			fw.close();
		}
	}

	public int load_high_score ()
	{
		BufferedReader reader;
		int IHS = 0;
		try {
			reader = new BufferedReader(new FileReader("Score.txt"));
			String HS = reader.readLine();

			while (HS != null)
			{
				System.out.println(HS);
				IHS = Integer.valueOf(HS);
				// read next line
				HS = reader.readLine();
			}
			reader.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return IHS;
	}

}
