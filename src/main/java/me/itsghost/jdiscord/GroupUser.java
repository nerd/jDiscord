package me.itsghost.jdiscord;

import lombok.Getter;

/**
 * Created by Ghost on 14/10/2015.
 */
public class GroupUser {
    @Getter private User user;
    @Getter private Role role;
    @Getter private String discriminator;
    public GroupUser(User user, Role role, String discriminator){
        this.user = user;
        this.role = role;
        this.discriminator = discriminator;
    }

    public String toString(){
        return user.getUsername();
    }
    public enum Role{
        ADMIN, USER
    }

}
