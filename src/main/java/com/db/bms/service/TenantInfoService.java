package com.db.bms.service;

import com.db.bms.model.User;

import java.util.List;

public interface TenantInfoService {
    List<User> getAllTenants();

    User getTenantByUserName(String username);

    int modifyTenant(User user);

    void deleteTenantByUserName(String username);

    int addTenant(User user);

}
