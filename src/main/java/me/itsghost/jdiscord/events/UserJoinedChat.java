package me.itsghost.jdiscord.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.itsghost.jdiscord.Server;
import me.itsghost.jdiscord.event.Event;
import me.itsghost.jdiscord.talkable.GroupUser;

/**
 * Created by Ghost on 14/10/2015.
 */
@Getter
@AllArgsConstructor
public class UserJoinedChat extends Event {
    private final Server server;
    private final GroupUser groupUser;
}
