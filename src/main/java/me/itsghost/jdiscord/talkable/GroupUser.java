package me.itsghost.jdiscord.talkable;

import lombok.Getter;

public class GroupUser {
    @Getter private User user;
    @Getter private String role;
    @Getter private String discriminator;

    public GroupUser(User user, String role, String discriminator) {
        this.user = user;
        this.role = role;
        this.discriminator = discriminator;
    }

    public String toString() {
        return user.getUsername();
    }
}
