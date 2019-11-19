package strategy;

public class AggressiveStrategy implements Strategy {
    @Override
    public void attack() {
        System.out.println("Attacking aggressively...");
    }

    @Override
    public void reinforce() {
        System.out.println("Reinforcing aggressively...");
    }

    @Override
    public void fortify() {
        System.out.println("Fortifying aggressively...");
    }
}
