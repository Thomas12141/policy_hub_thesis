package org.example.validation.syntax_validation;

import com.networknt.schema.*;
import org.example.validation.Type;
import org.example.validation.Validatior;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

public class SyntaxValidator implements Validatior {
    public List<String> validate(String input, Type type) {
        JsonSchema schema;
        try {
            schema = getJSONSchema();
        } catch (Exception e) {

            return List.of("Internal server error");
        }
        Set<ValidationMessage> errors = schema.validate(input, type == Type.JSON ? InputFormat.JSON : InputFormat.YAML);
        return errors.stream().map(ValidationMessage::getMessage).toList();
    }

    private static JsonSchema getJSONSchema() throws IOException {

        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
        SchemaValidatorsConfig config = SchemaValidatorsConfig.builder().build();
        Path schemaPath = Paths.get("src/main/resources/schemas/policy.schema.json");
        String schemaData = Files.readString(schemaPath);
        return jsonSchemaFactory.getSchema(schemaData, InputFormat.JSON, config);
    }
}
