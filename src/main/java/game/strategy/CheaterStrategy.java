package strategy;

public class CheaterStrategy implements Strategy{
    @Override
    public void attack() {
        System.out.println("Attacking in cheater mode...");
    }

    @Override
    public void reinforce() {
        System.out.println("Reinforcing in cheater mode...");
    }

    @Override
    public void fortify() {
        System.out.println("Fortifying in cheater mode...");
    }
}
