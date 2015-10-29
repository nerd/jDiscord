package me.itsghost.jdiscord.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import me.itsghost.jdiscord.talkable.Group;
import me.itsghost.jdiscord.talkable.GroupUser;

@Getter
@AllArgsConstructor
public class UserTypingEvent {
    private final Group chat;
    private final GroupUser user;
}
