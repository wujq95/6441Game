package strategy;

public class BenevolentStrategy implements Strategy {
    @Override
    public void attack() {
        System.out.println("Attacking benevolently...");
    }

    @Override
    public void reinforce() {
        System.out.println("Reinforcing benevolently...");
    }

    @Override
    public void fortify() {
        System.out.println("Fortifying benevolently...");
    }
}

