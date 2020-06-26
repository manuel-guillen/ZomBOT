package data.sources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import data.model.PerkACola;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class PerkAColaDataSource {

    private static final Collector<PerkACola, ?, Map<String, PerkACola>> MAP_COLLECTOR = Collectors.toUnmodifiableMap(PerkACola::getSimplifiedName, Function.identity());
    private static final ObjectMapper JSON_MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    private static Map<String,PerkACola> data;

    static {
        try {
            populateDataFromJSONFile(new File("C:\\Users\\Manuel Guillen\\Documents\\Workspace\\ZomBOT\\src\\main\\resources\\data\\perkAColas.json"));
        } catch (IOException e) {
            throw new RuntimeException("Could not connect to data source.");
        }
    }

    // ===============================================================================================================

    public static void populateDataFromJSONFile(File inputJSONFile) throws IOException {
        Set<PerkACola> values = JSON_MAPPER.readValue(inputJSONFile, new TypeReference<>(){});
        data = values.stream().collect(MAP_COLLECTOR);
    }

    public static Map<String,PerkACola> getData() {
        return data;
    }

    public static void exportData(File outputJSONFile) throws IOException {
        JSON_MAPPER.writeValue(outputJSONFile, data.values());
    }

}
