package strategy;

public class RandomStrategy implements Strategy{
    @Override
    public void attack() {
        System.out.println("Attacking randomly...");
    }

    @Override
    public void reinforce() {
        System.out.println("Reinforcing randomly...");
    }

    @Override
    public void fortify() {
        System.out.println("Fortifying randomly...");
    }
}
