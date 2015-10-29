package me.itsghost.jdiscord.talkable;

/**
 * Created by Ghost on 14/10/2015.
 */
public interface User {
    Group getGroup();

    String getAvatar();

    String getUsername();

    String getId();
}
