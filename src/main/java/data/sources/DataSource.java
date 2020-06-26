package data.sources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import data.model.Data;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class DataSource<T extends Data> {

    protected static final ObjectMapper JSON_MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    protected final Collector<T, ?, Map<String,T>> MAP_COLLECTOR = Collectors.toUnmodifiableMap(T::getSimplifiedName, Function.identity());
    protected Map<String, T> data;

    protected DataSource() {
        try {
            populateDataFromWebSource();
        } catch (IOException e) {
            throw new RuntimeException("Could not connect to online data source.");
        } catch (RuntimeException e) {
            // Await implementation in concrete class
        }
    }

    public Map<String, T> getDataMap() {
        return data;
    }

    protected abstract TypeReference<Set<T>> jsonDeserializeType();

    public void populateDataFromWebSource() throws IOException {
        throw new RuntimeException("Method not implemented (by default)");
    }

    public void populateDataFromJSONFile(File inputJSONFile) throws IOException {
        Set<T> values = JSON_MAPPER.readValue(inputJSONFile, jsonDeserializeType());
        data = values.stream().collect(MAP_COLLECTOR);
    }

    public void exportData(File outputJSONFile) throws IOException {
        JSON_MAPPER.writeValue(outputJSONFile, data.values());
    }

}
