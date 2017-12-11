package com.blueskykong.auth.service;


import com.blueskykong.auth.dao.ClientSecretDAO;
import com.blueskykong.auth.dto.ApiClientDTO;
import com.blueskykong.auth.entity.ClientSecret;
import com.blueskykong.auth.entity.ClientSecretStatus;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author keets
 * @date 2017/10/18
 */
@Service
public class ClientSecretService {
    @Autowired
    private ClientSecretDAO clientSecretDao;

    public void createClientSecret(ApiClientDTO apiClient) {
        ClientSecret clientSecret = new ClientSecret.ClientSecretBuilder()
                .withClientId(apiClient.getClientId())
                .withClientSecret(apiClient.getClientSecret())
                .withTenantId(apiClient.getTenantId())
                .withPurpose(apiClient.getPurpose())
                .withStatus(ClientSecretStatus.ACTIVE)
                .build();
        clientSecretDao.create(clientSecret);
    }

    public ApiClientDTO getClientSecretByClientId(String clientId) {
        ClientSecret clientSecret = new ClientSecret.ClientSecretBuilder()
                .withClientId(clientId)
                .build();
        List<ClientSecret> results = clientSecretDao.get(clientSecret);
        System.out.println(results.size());
        if (results.size() >= 1) {
            return convert(results.get(0));
        }
        return null;
    }

    public List<ApiClientDTO> getClientSecretByTenantId(UUID tenantId) {
        ClientSecret clientSecret = new ClientSecret.ClientSecretBuilder()
                .withTenantId(tenantId)
                .build();
        return Lists.transform(clientSecretDao.get(clientSecret), this::convert);
    }

    public int updateClientSecret(ApiClientDTO apiClient) {
        ClientSecret clientSecret = new ClientSecret.ClientSecretBuilder()
                .withClientSecret(apiClient.getClientSecret())
                .withPurpose(apiClient.getPurpose())
                .withStatus(ClientSecretStatus.valueOf(apiClient.getStatus()))
                .withClientId(apiClient.getClientId())
                .withTenantId(apiClient.getTenantId())
                .build();
        return clientSecretDao.update(clientSecret);
    }

    public int updateClientSecretByTenantId(UUID tenantId, ClientSecretStatus status) {
        return clientSecretDao.updateStatusByTenantId(tenantId, status);
    }

    public int updateClientSecretByClientId(String clientId, ApiClientDTO apiClient) {
        ClientSecretStatus status = ClientSecretStatus.valueOf(apiClient.getStatus());
        return clientSecretDao.updateStatusByClientId(clientId, status);
    }

    private ApiClientDTO convert(ClientSecret clientSecret) {
        ApiClientDTO apiClient = new ApiClientDTO();
        apiClient.setClientId(clientSecret.getClientId());
        apiClient.setClientSecret(clientSecret.getClientSecret());
/*        apiClient.setStatus(clientSecret.getStatus().toString());
        apiClient.setPurpose(clientSecret.getPurpose());
        apiClient.setUserId(clientSecret.getUserId());*/
        return apiClient;
    }

}
