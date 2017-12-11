package com.blueskykong.auth.entity;

import lombok.Data;

import java.util.UUID;
/**
 * Created by keets on 2016/12/5.
 */
@Data
public class ClientSecret extends BaseEntity {
    private String clientId;
    private String clientSecret;
    private ClientSecretStatus status;
    private String purpose;
    private UUID tenantId;
    private UUID userId;

    public static class ClientSecretBuilder {

        private ClientSecret client = new ClientSecret();

        public ClientSecretBuilder withClientId(String clientId) {
            client.setClientId(clientId);
            return this;
        }

        public ClientSecretBuilder withClientSecret(String clientSecret) {
            client.setClientSecret(clientSecret);
            return this;
        }

        public ClientSecretBuilder withStatus(ClientSecretStatus status) {
            client.setStatus(status);
            return this;
        }

        public ClientSecretBuilder withTenantId(UUID tenantId) {
            client.setTenantId(tenantId);
            return this;
        }

        public ClientSecretBuilder withPurpose(String purpose) {
            client.setPurpose(purpose);
            return this;
        }

        public ClientSecret build() {
            return client;
        }
    }
}
