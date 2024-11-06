public class Humano extends DragonBallZ {
    private String arteMarcial;

    public Humano(String name, Float power, String arteMarcial) {
        super(name, power);
        this.arteMarcial = arteMarcial;
    }

    public String getArteMarcial() {
        return arteMarcial;
    }

    public void setArteMarcial(String arteMarcial) {
        this.arteMarcial = arteMarcial;
    }
}
