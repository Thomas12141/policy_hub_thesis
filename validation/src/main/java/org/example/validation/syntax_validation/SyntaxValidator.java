package org.example.validation.syntax_validation;

import com.networknt.schema.*;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.example.validation.Type;
import org.example.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.List;
import java.util.Set;

public class SyntaxValidator implements Validator {
    private static final Logger logger = LoggerFactory.getLogger(SyntaxValidator.class);

    @Override
    public List<String> validate(String input, Type type) {
        if (input == null) {
            return List.of("Policy is null");
        }
        JsonSchema schema;
        try {
            schema = getJSONSchema();
        } catch (InvalidPathException e) {
            logger.error("Invalid path of JSON schema", e);
            return List.of("Internal server error");
        }
        Set<ValidationMessage> errors = schema.validate(input, type == Type.JSON ? InputFormat.JSON : InputFormat.YAML);
        return errors.stream().map(ValidationMessage::getMessage).toList();
    }

    private static JsonSchema getJSONSchema() {

        JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);
        SchemaValidatorsConfig config = SchemaValidatorsConfig.builder().build();
        String schemaData;
        try (InputStream inputStream = SyntaxValidator.class.getClassLoader().getResourceAsStream("schemas/policy.schema.json")) {
            if (inputStream == null) {
                throw new FileNotFoundException("schemas/policy.schema.json not found in classpath");
            }
            schemaData = new String(inputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonSchemaFactory.getSchema(schemaData, InputFormat.JSON, config);
    }
}
