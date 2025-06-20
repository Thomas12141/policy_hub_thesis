package org.example.location_policy;


import java.util.Collection;
import java.util.Objects;
import org.eclipse.edc.connector.controlplane.contract.spi.policy.ContractNegotiationPolicyContext;
import org.eclipse.edc.policy.engine.spi.AtomicConstraintRuleFunction;
import org.eclipse.edc.policy.model.Operator;
import org.eclipse.edc.policy.model.Permission;
import org.eclipse.edc.spi.monitor.Monitor;

public class LocationConstraintFunction implements AtomicConstraintRuleFunction<Permission, ContractNegotiationPolicyContext> {

  private final Monitor monitor;

  public LocationConstraintFunction(Monitor monitor) {
    this.monitor = monitor;
  }

  @Override
  public boolean evaluate(Operator operator, Object rightValue, Permission rule,
      ContractNegotiationPolicyContext context) {
    var region = context.participantAgent().getClaims().get("region");

    return switch (operator) {
      case EQ -> Objects.equals(region, rightValue);
      case NEQ -> !Objects.equals(region, rightValue);
      case IN -> ((Collection<?>) rightValue).contains(region);
      default -> false;
    };
  }
}
