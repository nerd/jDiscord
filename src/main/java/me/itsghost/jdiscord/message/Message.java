package me.itsghost.jdiscord.message;

import me.itsghost.jdiscord.Group;

/**
 * Created by Ghost on 15/10/2015.
 */
public interface Message {
    String getMessage();
    void setMessage(String message);
    String getId();
    String getGroupId();
    boolean isEdited();
    void applyUserTag(String username, Group server);
    void deleteMessage();
    void editMessage(String message);
}
