package org.eclipse.dataspaceconnector.dataverse;

import org.eclipse.dataspaceconnector.dataplane.spi.pipeline.DataSource;
import org.eclipse.dataspaceconnector.dataplane.spi.pipeline.DataSourceFactory;
import org.eclipse.dataspaceconnector.spi.result.Result;
import org.eclipse.dataspaceconnector.spi.types.domain.transfer.DataFlowRequest;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class DataverseSourceFactory implements DataSourceFactory {
    @Override
    public boolean canHandle(DataFlowRequest dataRequest) {
        return dataRequest.getSourceDataAddress().getType().toLowerCase(Locale.ROOT).equals("dataverse");
    }

    @Override
    public @NotNull Result<Boolean> validate(DataFlowRequest dataFlowRequest) {
        // TODO: Implement validation.
        return Result.success(true);
    }

    @Override
    public DataSource createSource(DataFlowRequest dataFlowRequest) {
        // dataFlowRequest.getSourceDataAddress().getProperty("url")
        var url = "https://org47579008.crm.dynamics.com/api/data/v9.2/products?$filter=productid%20eq%2067ddb4c0-88ca-ec11-a7b5-000d3a5d6728";
        return new DataverseDataSource(url);
    }
}
