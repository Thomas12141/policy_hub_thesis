package org.example.policy_hub.controllers;

import java.util.List;
import org.example.policy_hub.services.ValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/validation")
public class ValidationController {

  private final ValidationService service;

  public ValidationController(ValidationService service) {this.service = service;}

  @PostMapping
  public ResponseEntity<String> validate(@RequestBody String policy) {
    List<String> errors = service.validate(policy);
     return errors.isEmpty() ? ResponseEntity.ok("Policy is valid") :
         ResponseEntity.badRequest().body(
             errors.stream().reduce("", (a, b) -> a + "\n" + b));
  }
}
