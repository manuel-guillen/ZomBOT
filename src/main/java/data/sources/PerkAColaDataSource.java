package data.sources;

import com.fasterxml.jackson.core.type.TypeReference;
import data.model.PerkACola;

import java.util.Set;

public class PerkAColaDataSource extends DataSource<PerkACola> {

    private static final PerkAColaDataSource THIS = new PerkAColaDataSource();

    public static PerkAColaDataSource getInstance() {
        return THIS;
    }

    private PerkAColaDataSource() {
        super("/data/perkAColas.json");
    }

    @Override
    protected TypeReference<Set<PerkACola>> jsonDeserializeType() {
        return new TypeReference<>(){};
    }

}
