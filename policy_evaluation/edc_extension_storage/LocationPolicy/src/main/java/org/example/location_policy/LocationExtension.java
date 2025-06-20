package org.example.location_policy;

import static org.eclipse.edc.jsonld.spi.PropertyAndTypeNames.ODRL_USE_ACTION_ATTRIBUTE;
import static org.eclipse.edc.policy.engine.spi.PolicyEngine.ALL_SCOPES;
import static org.eclipse.edc.spi.constants.CoreConstants.EDC_NAMESPACE;

import org.eclipse.edc.connector.controlplane.contract.spi.policy.ContractNegotiationPolicyContext;
import org.eclipse.edc.policy.engine.spi.PolicyEngine;
import org.eclipse.edc.policy.engine.spi.RuleBindingRegistry;
import org.eclipse.edc.policy.model.Permission;
import org.eclipse.edc.runtime.metamodel.annotation.Inject;
import org.eclipse.edc.spi.monitor.Monitor;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;

public class LocationExtension implements ServiceExtension {

  private static final String LOCATION_CONSTRAINT_KEY = EDC_NAMESPACE + "Location";

  @Inject
  private PolicyEngine policyEngine;

  @Inject
  private RuleBindingRegistry bindingRegistry;


  @Override
  public void initialize(ServiceExtensionContext context) {
    Monitor monitor = context.getMonitor();

    var function = new LocationConstraintFunction(monitor);

    bindingRegistry.bind(ODRL_USE_ACTION_ATTRIBUTE, ALL_SCOPES);
    bindingRegistry.bind(LOCATION_CONSTRAINT_KEY, ContractNegotiationPolicyContext.NEGOTIATION_SCOPE);

    policyEngine.registerFunction(ContractNegotiationPolicyContext.class, Permission.class,
        LOCATION_CONSTRAINT_KEY, function);
  }
}
