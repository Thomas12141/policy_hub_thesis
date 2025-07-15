package de.example.policy_dataplane;

import org.eclipse.edc.connector.dataplane.framework.pipeline.PipelineServiceImpl;
import org.eclipse.edc.connector.dataplane.spi.pipeline.PipelineService;
import org.eclipse.edc.runtime.metamodel.annotation.Provider;
import org.eclipse.edc.spi.system.ServiceExtension;
import org.eclipse.edc.spi.system.ServiceExtensionContext;

public class ProxyPipelineServiceExtension implements ServiceExtension {

    @Provider
    public PipelineService proxyPipelineService(ServiceExtensionContext context) {
        return new ProxyPipelineService(new PipelineServiceImpl(context.getMonitor()));
    }
}
