package cn.edu.cup.hilly.dataSource.mapper;

import cn.edu.cup.hilly.dataSource.model.User;

/**
 * @author CodeChap
 * @date 2021-07-24 9:51
 * @description UserMapper
 */
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User loadUserByUsername(String username);   //根据用户名查询数据库中的用户信息
}
