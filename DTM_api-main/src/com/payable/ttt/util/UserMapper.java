package com.payable.ttt.util;

import com.payable.ttt.dto.UserDTO;
import com.payable.ttt.model.ORMUser;

public class UserMapper {

    public static ORMUser toEntity(UserDTO dto) {
        ORMUser user = new ORMUser();
        user.setId(dto.getId());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(dto.getPassword());
        user.setSalt(dto.getSalt());
        user.setEmail(dto.getEmail());
        user.setStatus(dto.getStatus());
        user.setLast_ip(dto.getLastIp());
        user.setCreatedBy(dto.getCreatedBy());
        user.setUpdatedBy(dto.getUpdatedBy());
        user.setAuth(dto.getAuth());
        user.setIsDefaultPassword(dto.getIsDefaultPassword());
        user.setLastLoggedTs(dto.getLastLoggedTs());
        user.setExpiry(dto.getExpiry());
        user.setUserRoleId(dto.getUserRoleId());
        user.setUserGroupId(dto.getUserGroupId());
        return user;
    }

    public static UserDTO toDTO(ORMUser user) {
        return new UserDTO(
            user.getId(),
            user.getFirstName(),
            user.getLastName(),
            user.getPassword(),
            user.getSalt(),
            user.getEmail(),
            user.getStatus(),
            user.getLast_ip(),
            user.getCreatedBy(),
            user.getUpdatedBy(),
            user.getAuth(),
            user.getIsDefaultPassword(),
            user.getLastLoggedTs(),
            user.getExpiry(),
            user.getUserRoleId(),
            user.getUserGroupId()
        );
    }
}
