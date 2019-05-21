package fruitninja.model;


public class Field {

	private GameObject object;
	private SlashTrail slash;

	/**
	 * Sets up a new SlashTrail.
	 */
	public Field() {
		slash = new SlashTrail();
	}

	/**
	 * Sets the GameObject in the field.
	 *
	 * @param object
	 *            GameObject to be added to the field.
	 */
	public void setObject(GameObject object) {
		this.object = object;
	}

	/**
	 * Returns the GameObject in the field.
	 *
	 * @return GameObject in the field.
	 */
	public GameObject getObject() {
		return object;
	}

	/**
	 * Returns the Slash object in the field.
	 *
	 * @return Slash object in the field.
	 */
	public SlashTrail getSlash() {
		return slash;
	}

}
