package fruitninja.model;

public class Context {
	private Strategy strategy;

	public Context(Strategy strategy){
		this.strategy = strategy;
	}
	public int executeStrategy(int score){
		return strategy.addScore(score);
	}

}