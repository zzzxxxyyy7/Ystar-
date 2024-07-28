package com.ystar.user.interfaces;

import com.ystar.user.dto.UserDTO;

public interface IUserRpc {
    /**
     * 根据用户id进行查询
     *
     * @param userId
     * @return
     */
    UserDTO getUserById(Long userId);

    /**
     * 更新用户信息
     * @param userDTO
     * @return
     */
    boolean updateUserInfo(UserDTO userDTO);

    /**
     * 插入用户
     * @param userDTO
     * @return
     */
    boolean insertOne(UserDTO userDTO);
}