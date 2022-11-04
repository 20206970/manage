package com.db.bms.mapper;

import com.db.bms.model.User;

import java.util.List;

public interface TenantInfoMapper {
    List<User> getAllTenants(Integer identify);

    User getTenantByUserName(String username);

    int modifyTenantById(Integer id);

    void deleteTenantById(Integer id);

    void addTenant(User user);

    void deleteTenantByUserName(String username);

    int modifyTenant(User user);
}
