package me.itsghost.jdiscord;

import me.itsghost.jdiscord.talkable.Group;
import me.itsghost.jdiscord.talkable.GroupUser;

import java.util.List;

public interface Server {
    String getId();

    String getTopic();

    String getLocation();

    String getCreatorId();

    String getAvatar();

    GroupUser getGroupUserById(String id);

    GroupUser getGroupUserByUsername(String username);

    List<GroupUser> getConnectedClients();

    List<Group> getGroups();

    void kick(String user);

    void ban(String user);

    void bc(String message);

    Group getGroupById(String id);
}
