import java.io.Serializable;

public class DragonBallZ implements Serializable {
    private String name;
    private Float power;

    public DragonBallZ( String name, Float power) {
        this.name = name;
        this.power = power;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPower() {
        return power;
    }

    public void setPower(Float power) {
        this.power = power;
    }

}
