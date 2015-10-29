package me.itsghost.jdiscord.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.itsghost.jdiscord.event.Event;
import me.itsghost.jdiscord.message.Message;
import me.itsghost.jdiscord.talkable.Group;
import me.itsghost.jdiscord.talkable.GroupUser;
import me.itsghost.jdiscord.talkable.User;

/**
 * Created by Ghost on 14/10/2015.
 */
@Getter
@AllArgsConstructor
public class UserChatEvent extends Event {
    private final Group group;
    private final GroupUser user;
    private Message msg;
}
