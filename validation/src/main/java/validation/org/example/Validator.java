package validation.org.example;

import java.util.List;

public interface Validator {
  List<String> validate(String policy, Type type);
}
