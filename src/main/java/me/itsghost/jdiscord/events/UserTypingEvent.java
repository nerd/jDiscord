package me.itsghost.jdiscord.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.itsghost.jdiscord.event.Event;
import me.itsghost.jdiscord.talkable.Group;
import me.itsghost.jdiscord.talkable.GroupUser;
import me.itsghost.jdiscord.talkable.User;

@Getter
@AllArgsConstructor
public class UserTypingEvent extends Event {
    private final Group chat;
    private final GroupUser user;
}
