package data.sources;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import data.model.Data;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public abstract class DataSource<T extends Data> {

    protected static final ObjectMapper JSON_MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    protected Set<T> data;

    protected DataSource() {
        try {
            populateDataFromWebSource();
        } catch (IOException e) {
            throw new RuntimeException("Could not connect to online data source.");
        } catch (RuntimeException e) {
            // Await implementation in concrete class
        }
    }

    public Set<Data> getDataSet() {
        return (Set<Data>) data;
    }

    protected abstract TypeReference<Set<T>> jsonDeserializeType();

    public void populateDataFromWebSource() throws IOException {
        throw new RuntimeException("Method not implemented (by default)");
    }

    public void populateDataFromJSONFile(File inputJSONFile) throws IOException {
        data = JSON_MAPPER.readValue(inputJSONFile, jsonDeserializeType());
    }

    public void exportData(File outputJSONFile) throws IOException {
        JSON_MAPPER.writeValue(outputJSONFile, data);
    }

}
