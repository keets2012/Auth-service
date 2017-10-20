package com.blueskykong.auth.dao;

import com.blueskykong.auth.entity.ClientSecret;
import com.blueskykong.auth.entity.ClientSecretStatus;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.UUID;


@Mapper
public interface ClientSecretDAO {
    int create(ClientSecret clientSecret);

    String getScope(String clientId,String clientSecret);

    List<ClientSecret> get(ClientSecret clientSecret);

    int updateStatusByTenantId(UUID tenantId, ClientSecretStatus status);

    int updateStatusByClientId(String clientId, ClientSecretStatus status);

    int update(ClientSecret clientSecret);
}
