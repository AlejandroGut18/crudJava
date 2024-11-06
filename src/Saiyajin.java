public class Saiyajin extends DragonBallZ {
    private String fase;

    public Saiyajin(String name, Float power, String fase) {
        super(name, power);
        this.fase = fase;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }
}
