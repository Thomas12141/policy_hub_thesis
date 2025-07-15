package de.example.policy_dataplane;

import org.eclipse.edc.connector.dataplane.spi.pipeline.DataSink;
import org.eclipse.edc.spi.types.domain.transfer.DataFlowStartMessage;

public interface PolicyEvaluator {
    boolean evaluate(DataFlowStartMessage dataFlowStartMessage);

    boolean evaluate(DataFlowStartMessage dataFlowStartMessage, DataSink sink);

    void updateValidationStatus(DataFlowStartMessage dataFlowStartMessage);
}
