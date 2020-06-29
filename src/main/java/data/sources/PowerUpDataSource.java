package data.sources;

import com.fasterxml.jackson.core.type.TypeReference;
import data.model.PowerUp;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class PowerUpDataSource extends DataSource<PowerUp> {

    private static final PowerUpDataSource THIS = new PowerUpDataSource();

    public static PowerUpDataSource getInstance() {
        return THIS;
    }

    private PowerUpDataSource() {
        try {
            populateDataFromJSONFile(new File(this.getClass().getResource("/data/powerUps.json").toURI()));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected TypeReference<Set<PowerUp>> jsonDeserializeType() {
        return new TypeReference<>(){};
    }

}
