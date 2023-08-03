package me.whitebear.jpastudy.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfo {

    private String username;
    private String password;

    public String getUserInfo() {
        return username + " " + password;
    }

}
