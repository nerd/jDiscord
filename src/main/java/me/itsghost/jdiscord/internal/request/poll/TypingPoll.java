package me.itsghost.jdiscord.internal.request.poll;

import me.itsghost.jdiscord.DiscordAPI;
import me.itsghost.jdiscord.Group;
import me.itsghost.jdiscord.GroupUser;
import me.itsghost.jdiscord.Server;
import me.itsghost.jdiscord.events.UserChatEvent;
import me.itsghost.jdiscord.events.UserTypingEvent;
import me.itsghost.jdiscord.internal.impl.MessageImpl;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

public class TypingPoll implements Poll{
    private DiscordAPI api;
    public TypingPoll(DiscordAPI api){
        this.api = api;
    }

    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server) {
        Group group = api.getGroupById(content.getString("channel_id"));
        GroupUser user = group.getServer().getGroupUserById(content.getString("user_id"));
        api.getEventManager().executeEvent(new UserTypingEvent(group, user));
    }
}
