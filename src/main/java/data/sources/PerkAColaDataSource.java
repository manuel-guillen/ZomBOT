package data.sources;

import com.fasterxml.jackson.core.type.TypeReference;
import data.model.PerkACola;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

public class PerkAColaDataSource extends DataSource<PerkACola> {

    private static final PerkAColaDataSource THIS = new PerkAColaDataSource();

    public static PerkAColaDataSource getInstance() {
        return THIS;
    }

    private PerkAColaDataSource() {
        try {
            populateDataFromJSONFile(new File(this.getClass().getResource("/data/perkAColas.json").toURI()));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected TypeReference<Set<PerkACola>> jsonDeserializeType() {
        return new TypeReference<>(){};
    }

}
