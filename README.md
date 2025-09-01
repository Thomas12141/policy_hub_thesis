# Policy Hub

The Policy Hub is a web application allowing developers in a dataspace to do the following actions:
1. Validate policies.
2. Upload and download json payload of policies. 
3. Download JAR ready policy evaluation EDC extensions for embedding them into the EDC Connector.

## Prerequisites

- Java Development Kit (JDK) 21
- Docker and Docker Compose
- Gradle (or use the included Gradle wrapper)

## Getting Started

Follow these steps to start the application:

1. **Start Docker services**:
   <br />Run the following command to start the necessary services for the application:
   ```bash
   docker-compose up -d
   ```

2. **Run the application**:
   <br />Use the Gradle wrapper to start the Spring Boot application:
   ```bash
   ./gradlew bootRun
   ```

3. The application should now be running and accessible based on its configured settings.

## Project Structure

```
policy-hub/
├── src/
│   └── main/
│       ├── java/               # Core application code
│       └── resources/          # Application resources
├── validation/                 # Policy validation logic
├── policy_evaluation/          # Policy evaluation components
│   └── edc_extension_storage/  # EDC extensions
└── test-utils/                 # Testing utilities
```

## REST API Endpoints

The Policy Hub provides the following REST API endpoints for interacting with policies and extensions:

### Policies

- **Upload a Policy**
  - **Endpoint**: `POST /api/policies`
  - **Description**: Uploads a policy JSON payload.
  - **Request Body**: JSON policy object.
  - **Response**: 
    - `200 OK`: Policy stored successfully.
    - `400 Bad Request`: Validation errors or missing `uid`.

- **Get a Policy by UID**
  - **Endpoint**: `GET /api/policies`
  - **Description**: Retrieves a policy by its unique identifier (`uid`).
  - **Query Parameter**: `uid` (required) - The unique identifier of the policy.
  - **Response**: 
    - `200 OK`: Returns the policy JSON.
    - `404 Not Found`: Policy not found.

- **List All Policies**
  - **Endpoint**: `GET /api/policies/all`
  - **Description**: Retrieves all stored policies.
  - **Response**: 
    - `200 OK`: Returns a list of all policies.
    - `500 Internal Server Error`: Error occurred while retrieving policies.

### Validation

- **Validate a Policy**
  - **Endpoint**: `POST /api/validation`
  - **Description**: Validates a policy JSON payload.
  - **Request Body**: JSON policy object.
  - **Response**: 
    - `200 OK`: Policy is valid.
    - `400 Bad Request`: Validation errors.

### JAR Extensions

- **List All JAR Extensions**
  - **Endpoint**: `GET /api/jars`
  - **Description**: Retrieves a list of all available JAR extensions.
  - **Response**: 
    - `200 OK`: Returns a list of JAR extensions.

- **Download a JAR Extension**
  - **Endpoint**: `GET /api/jars/download/{moduleName}`
  - **Description**: Downloads a specific JAR extension by its module name.
  - **Path Parameter**: `moduleName` (required) - The name of the module.
  - **Response**: 
    - `200 OK`: Returns the JAR file.
    - `404 Not Found`: JAR extension not found.

## EDC Extensions

The Policy Hub provides EDC (Eclipse Dataspace Connector) policy evaluation extensions that can be downloaded and integrated into EDC connectors.

### Available Extensions

The following policy evaluation extensions are available:

- **AccessPolicy**: Controls access based on participant identities
- **BillingPolicy**: Enforces payment requirements for asset access 
- **CountPolicy**: Limits the number of times an asset can be accessed
- **IsDataspaceMemberPolicy**: Verifies dataspace membership credentials
- **LocationPolicy**: Restricts access based on geographic location
- **TimeFramePolicy**: Controls access based on time constraints

### Adding New Extensions

To create a new EDC policy evaluation extension:

1. Create a new module in the `policy_evaluation/edc_extension_storage` directory.
2. Add the required Gradle configuration (build.gradle.kts):
```
plugins {
    `java-library`
    id("application")
}

dependencies {
    api("org.eclipse.edc:data-plane-spi:0.13.0")
    api("org.eclipse.edc:json-ld-spi:0.13.0")
    implementation("org.eclipse.edc:control-plane-core:0.13.0")
    // Add other required dependencies
}
```
3. Implement the core components:
- Create a policy function class implementing the `AtomicConstraintRuleFunction` interface (org.eclipse.edc.policy.engine.spi.AtomicConstraintRuleFunction)
- Create an extension class implementing the `ServiceExtension` interface (org.eclipse.edc.spi.system.ServiceExtension)
- Register the function to the `policyEngine` and bind it to the `bindingRegistry`, an example can be found in [`AccessPolicyExtension`](policy_evaluation\edc_extension_storage\AccessPolicy\src\main\java\org\example\access_policy\AccessPolicyExtension.java)
- Add the extension service definition in `META-INF/services`, an example can be found in `org.eclipse.edc.spi.system.ServiceExtension`(policy_evaluation\edc_extension_storage\AccessPolicy\src\main\resources\META-INF\services\org.eclipse.edc.spi.system.ServiceExtension)
4. Add a description into the map `moduleToDescription` in `JarService`(src\main\java\org\example\policy_hub\services\JarService.java)

The extension will be automatically packaged into a JAR and made available for download through the Policy Hub API when the application starts.

### Extension example structure

```
NewPolicyName/
├── build.gradle.kts
├── src/
│   ├── main/
│   │   ├── java/org/example/new_policy/
│   │   │   ├── NewPolicyFunction.java
│   │   │   └── NewPolicyExtension.java
│   │   └── resources/META-INF/services/
│   │       └── org.eclipse.edc.spi.system.ServiceExtension
│   └── test/
│       └── java/org/example/new_policy/
│           └── NewPolicyFunctionTest.java
```

### Integration
1. Download the extension JAR from the Policy Hub API.
2. Include it to the connectors gradle build file.

## Validation logic

The validation system consists of three main components:
1. Syntax validation using JSON Schema.
2. Semantic validation using Drools rules engine.
3. Parallel validation calling all the implemented validation types.

### Architecture

The validation follows a parallel execution pattern where both syntax and semantic validations run concurrently through the [`ParallelPolicyValidator`](validation/src/main/java/org/example/validation/ParallelPolicyValidator.java).

```java
public List<String> validate(String policy) {
        if (policy == null) {
            return List.of("Policy is null");
        }
        List<CompletableFuture<List<String>>> futures = validators.stream()
                .map(v -> CompletableFuture.supplyAsync(() -> v.validate(policy)).exceptionally(ex -> {
                    logger.error("Error while validating policy", ex);
                    return List.of("Internal server error");
                }))
                .toList();

        return futures.stream()
                .map(CompletableFuture::join)
                .flatMap(List::stream)
                .toList();
    }
```
### Adding new validators

To add a new validator:

1. Implement the [`Validator`](validation/src/main/java/org/example/validation/Validator.java) interface:
```java
public interface Validator {
    List<String> validate(String policy);
}
```
2. Add your validator to the `validators set` in [`ParallelPolicyValidator`](validation/src/main/java/org/example/validation/ParallelPolicyValidator.java).


### Semantic Validation

Semantic validation ensures that the policy adheres to the logical and business rules defined for the application. This is achieved using the Drools rules engine. The process involves:

1. Parsing the policy JSON into a [`Policy`](validation\src\main\java\org\example\validation\semantic_validation\model\Policy.java) object using the [`Policy.ofJSON()`](validation/src/main/java/org/example/validation/semantic_validation/model/Policy.java) method.
2. Initializing a Drools `KieSession` with the rules defined in the [`rules/policy-rules.drl`](validation\src\main\resources\rules\policy-rules.drl) file.
3. Inserting the [`Policy`](validation\src\main\java\org\example\validation\semantic_validation\model\Policy.java) object into the session and firing all rules.
4. Collecting any validation errors into a [`PolicyValidation`](validation\src\main\java\org\example\validation\semantic_validation\model\PolicyValidation.java) object.

The semantic validation logic is implemented in the [`SemanticValidator`](validation/src/main/java/org/example/validation/semantic_validation/SemanticValidator.java) class.

#### Adding mew rules

To add a new Drools rule for semantic validation:

1. Open the [`rules/policy-rules.drl`](validation\src\main\resources\rules\policy-rules.drl) file.
2. Define a new rule using the Drools rule syntax. For example:
```java
rule "New Rule Name"
when
   // Conditions for the rule
   PolicyValidation( $policy : policy, $errors : errors )
   Policy( $permissions : permissions ) from $policy
   Rule( $constraints : constraints ) from $permissions
   Constraint( leftOperand == "exampleValue" && valueIsfalse(rightOperand) ) from $constraints
then
   // Actions to perform if the rule matches
   $errors.add("Error message for the rule");
end
```
3. Save the file. The new rule will automatically be picked up during the semantic validation process.

### Syntax Validation

Syntax validation ensures that the policy JSON conforms to the structure defined in the JSON Schema. The process involves:

1. Loading the JSON Schema from the [`schemas/policy.schema.json`](validation\src\main\resources\schemas\policy.schema.json) file.
2. Validating the policy JSON against the schema using the `JsonSchema` library.
3. Collecting any validation errors and returning them as a list of error messages.

The syntax validation logic is implemented in the [`SyntaxValidator`](validation/src/main/java/org/example/validation/syntax_validation/SyntaxValidator.java) class.

Both validators are designed to work independently and can be executed in parallel using the [`ParallelPolicyValidator`](validation/src/main/java/org/example/validation/ParallelPolicyValidator.java) class.