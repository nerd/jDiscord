package me.itsghost.jdiscord.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.itsghost.jdiscord.Group;
import me.itsghost.jdiscord.GroupUser;
import me.itsghost.jdiscord.event.Event;
import me.itsghost.jdiscord.internal.impl.MessageImpl;

/**
 * Created by Ghost on 14/10/2015.
 */
@Getter
@AllArgsConstructor
public class UserChatEvent extends Event {
    private final Group group;
    private final GroupUser groupUser;
    private MessageImpl msg;
}
