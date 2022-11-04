package com.db.bms.mapper;

import com.db.bms.model.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    Integer modifyUser(User user);

    int updateByPrimaryKey(User record);

    User selectByUsernameAndPasswordAndIdentify(@Param("userName") String username,
                                  @Param("userPassword") String password,
                                  @Param("identify") Integer identify);

    User selectByPhoneNumberAndPasswordAndIdentify(@Param("phoneNumber") String phoneNumber,
                                                @Param("userPassword") String password,
                                                @Param("identify") Integer identify);

    User selectByUsername(String username);

    List<User> selectAllByLimit(@Param("begin") Integer begin, @Param("size") Integer size);

    Integer selectCount(Integer identify);

    List<User> selectAll();

    int selectCountBySearch(Map<String, Object> params);

    List<User> selectBySearch(Map<String, Object> params);

    List<User> selectAllTenants();

    User getUserByUserName(String username);

    void deleteUserByUserName(String username);
    List<User> selectUserByIdentify(Integer identify);
}
