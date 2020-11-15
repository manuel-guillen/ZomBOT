package data.sources;

import com.fasterxml.jackson.core.type.TypeReference;
import data.model.GuideInfo;

import java.util.Set;

public class GuideInfoSource extends DataSource<GuideInfo> {

    private static final GuideInfoSource THIS = new GuideInfoSource();

    public static GuideInfoSource getInstance() {
        return THIS;
    }

    private GuideInfoSource() {
        super("/data/guideInfo.json");
    }

    @Override
    protected TypeReference<Set<GuideInfo>> jsonDeserializeType() {
        return new TypeReference<>(){};
    }

}
