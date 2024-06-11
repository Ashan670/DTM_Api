package com.payable.ttt.dto.usergroup;

import com.payable.ttt.model.ORMUserGroup;
import com.google.gson.Gson;

public class UserGroupDTO {
    private String id;
    private String groupName;
    private Boolean status;

    public UserGroupDTO(ORMUserGroup userGroup) {
        this.id = userGroup.getId();
        this.groupName = userGroup.getGroupName();
        this.status = userGroup.getStatus();
    }

    public String getJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
