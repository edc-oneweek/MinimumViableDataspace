package org.eclipse.dataspaceconnector.dataverse;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.eclipse.dataspaceconnector.dataplane.spi.pipeline.DataSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

public class DataverseDataSource implements DataSource {

    private OkHttpClient httpClient;

    private String url;

    public DataverseDataSource(String url) {
        this.url = url;
    }

    @Override
    public Stream<Part> openPartStream() {
        try {
            return Stream.of(getPart());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpPart getPart() throws IOException {
        var request = new Request.Builder().url(url).build();
        try (var response = httpClient.newCall(request).execute()) {
            return new HttpPart("dataversePart", response.body().bytes());
        }
    }

    private static class HttpPart implements Part {
        private final String name;
        private final byte[] content;

        HttpPart(String name, byte[] content) {
            this.name = name;
            this.content = content;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public long size() {
            return content.length;
        }

        @Override
        public InputStream openStream() {
            return new ByteArrayInputStream(content);
        }

    }
}
