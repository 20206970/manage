package com.db.bms.service.impl;

import com.db.bms.mapper.TenantInfoMapper;
import com.db.bms.mapper.UserMapper;
import com.db.bms.model.User;
import com.db.bms.service.TenantInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class TenantInfoServiceImpl implements TenantInfoService {

    @Resource
    private TenantInfoMapper tenantInfoMapper;

    @Override
    public List<User> getAllTenants() {
        return tenantInfoMapper.getAllTenants(1);
    }

    @Override
    public User getTenantByUserName(String username) {
        return tenantInfoMapper.getTenantByUserName(username);
    }

    @Override
    public int modifyTenant(User user) {
        if(getTenantByUserName(user.getUserName()) != null){
            return 0;
        }
        tenantInfoMapper.modifyTenant(user);
        return 1;
    }

    @Override
    public void deleteTenantByUserName(String username) {
        tenantInfoMapper.deleteTenantByUserName(username);
    }


    @Override
    public int addTenant(User user) {
        if(getTenantByUserName(user.getUserName()) != null){
            return 0;
        }
        tenantInfoMapper.addTenant(user);
        return 1;
    }
}
