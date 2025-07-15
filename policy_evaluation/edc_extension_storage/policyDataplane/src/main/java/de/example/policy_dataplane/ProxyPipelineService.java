package de.example.policy_dataplane;

import org.eclipse.edc.connector.dataplane.spi.DataFlow;
import org.eclipse.edc.connector.dataplane.spi.pipeline.*;
import org.eclipse.edc.spi.result.Result;
import org.eclipse.edc.spi.types.domain.transfer.DataFlowStartMessage;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ProxyPipelineService implements PipelineService {

    private final PipelineService pipelineService;

    private final Set<PolicyEvaluator> policyEvaluators = new HashSet<>();

    public ProxyPipelineService(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    public void addPolicyValidator(PolicyEvaluator policyEvaluator) {
        policyEvaluators.add(policyEvaluator);
    }

    @Override
    public void registerFactory(DataSourceFactory factory) {
        pipelineService.registerFactory(factory);
    }

    @Override
    public void registerFactory(DataSinkFactory factory) {
        pipelineService.registerFactory(factory);
    }

    @Override
    public Set<String> supportedSourceTypes() {
        return pipelineService.supportedSourceTypes();
    }

    @Override
    public Set<String> supportedSinkTypes() {
        return pipelineService.supportedSinkTypes();
    }

    @Override
    public boolean canHandle(DataFlowStartMessage request) {
        return pipelineService.canHandle(request);
    }

    @Override
    public Result<Void> validate(DataFlowStartMessage request) {
        return pipelineService.validate(request);
    }

    @Override
    public CompletableFuture<StreamResult<Object>> transfer(DataFlowStartMessage request) {
        boolean validPolicy;
        for (PolicyEvaluator policyEvaluator : policyEvaluators) {
            validPolicy = policyEvaluator.evaluate(request);
            if (!validPolicy) {
                return CompletableFuture.completedFuture(StreamResult.error("Policy invalid source type %s for flow id: %s.".formatted(
                        request.getSourceDataAddress().getType(), request.getProcessId())));
            }
        }
        return pipelineService.transfer(request);
    }

    @Override
    public CompletableFuture<StreamResult<Object>> transfer(DataFlowStartMessage request, DataSink sink) {
        boolean validPolicy;
        for (PolicyEvaluator policyEvaluator : policyEvaluators) {
            validPolicy = policyEvaluator.evaluate(request, sink);
            if (!validPolicy) {
                return CompletableFuture.completedFuture(StreamResult.error("Policy invalid source type %s for flow id: %s.".formatted(
                        request.getSourceDataAddress().getType(), request.getProcessId())));
            }
        }
        return pipelineService.transfer(request, sink);
    }

    @Override
    public StreamResult<Void> terminate(DataFlow dataFlow) {
        return pipelineService.terminate(dataFlow);
    }

    @Override
    public void closeAll() {
        pipelineService.closeAll();
    }
}
