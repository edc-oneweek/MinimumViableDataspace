package org.eclipse.dataspaceconnector.dataverse;

import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.UsernamePasswordCredential;
import com.azure.identity.UsernamePasswordCredentialBuilder;
import org.apache.commons.lang3.StringUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.eclipse.dataspaceconnector.dataplane.spi.pipeline.DataSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.stream.Stream;

public class DataverseDataSource implements DataSource {

    private OkHttpClient httpClient = new OkHttpClient();
    private static String tenantId = getEnv("DYNAMICS_TENANT_ID");
    private static String clientId = getEnv("DYNAMICS_CLIENT_ID");
    private static String userName = getEnv("DYNAMICS_USERNAME");
    private static String password = getEnv("DYNAMICS_PASSWORD");

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
        System.out.println("tenantID:" + tenantId);
        System.out.println("clientID:" + clientId);
        System.out.println("userName:" + userName);
        UsernamePasswordCredential cred = new UsernamePasswordCredentialBuilder()
                .tenantId(tenantId)
                .clientId(clientId)
                .username(userName)
                .password(password)
                .build();

        var tokenRequestContext = new TokenRequestContext().addScopes("https://org47579008.crm.dynamics.com/user_impersonation");
        var token = cred.getToken(tokenRequestContext).block().getToken();


        var request = new Request.Builder()
                .url(url)
                .header("Authorization", "Bearer " + token)
                .build();
        try (var response = httpClient.newCall(request).execute()) {
            return new HttpPart("dataversePart", response.body().bytes());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
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

    private static String getEnv(String key) {
        return Objects.requireNonNull(StringUtils.trimToNull(System.getenv(key)), key);
    }
}
