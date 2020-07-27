package data.sources;

import com.fasterxml.jackson.core.type.TypeReference;
import data.model.PowerUp;

import java.util.Set;

public class PowerUpDataSource extends DataSource<PowerUp> {

    private static final PowerUpDataSource THIS = new PowerUpDataSource();

    public static PowerUpDataSource getInstance() {
        return THIS;
    }

    private PowerUpDataSource() {
        super("/data/powerUps.json");
    }

    @Override
    protected TypeReference<Set<PowerUp>> jsonDeserializeType() {
        return new TypeReference<>(){};
    }

}
