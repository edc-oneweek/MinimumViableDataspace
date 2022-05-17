package org.eclipse.dataspaceconnector.dataverse;

import org.eclipse.dataspaceconnector.dataplane.spi.pipeline.PipelineService;
import org.eclipse.dataspaceconnector.spi.system.Inject;
import org.eclipse.dataspaceconnector.spi.system.ServiceExtension;
import org.eclipse.dataspaceconnector.spi.system.ServiceExtensionContext;

public class DataverseExtension implements ServiceExtension {

    @Inject
    private PipelineService pipelineService;

    public void initialize(ServiceExtensionContext context) {
        var sourceFactory = new DataverseSourceFactory();
        pipelineService.registerFactory(sourceFactory);
    }
}
