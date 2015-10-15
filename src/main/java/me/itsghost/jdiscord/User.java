package me.itsghost.jdiscord;

import me.itsghost.jdiscord.internal.impl.MessageImpl;
import me.itsghost.jdiscord.message.Message;
import me.itsghost.jdiscord.message.MessageHistory;

/**
 * Created by Ghost on 14/10/2015.
 */
public interface User {
    Message sendMessage(String message);
    Message sendMessage(Message message);
    String getAvatar();
    String getUsername();
    String getId();
    MessageHistory getMessageHistory();
}
