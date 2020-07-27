package data.sources;

import com.fasterxml.jackson.core.type.TypeReference;
import data.model.ZombiesMap;

import java.util.Set;

public class ZombiesMapDataSource extends DataSource<ZombiesMap> {

    private static final ZombiesMapDataSource THIS = new ZombiesMapDataSource();

    public static ZombiesMapDataSource getInstance() {
        return THIS;
    }

    private ZombiesMapDataSource() {
        super("/data/maps.json");
    }

    @Override
    protected TypeReference<Set<ZombiesMap>> jsonDeserializeType() {
        return new TypeReference<>(){};
    }

}
