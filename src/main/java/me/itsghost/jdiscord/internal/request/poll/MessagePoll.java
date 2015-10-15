package me.itsghost.jdiscord.internal.request.poll;

import me.itsghost.jdiscord.DiscordAPI;
import me.itsghost.jdiscord.Group;
import me.itsghost.jdiscord.Server;
import me.itsghost.jdiscord.events.UserChatEvent;
import me.itsghost.jdiscord.internal.impl.MessageImpl;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

public class MessagePoll implements Poll{
    private DiscordAPI api;
    public MessagePoll(DiscordAPI api){
        this.api = api;
    }

    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server) {
        Group group = api.getGroupById(content.getString("channel_id"));
        String msgContent = StringEscapeUtils.unescapeJson(content.getString("content"));
        String msgId = content.getString("id");
        MessageImpl msg = new MessageImpl(msgContent, msgId, group.getId(), api);

        if (!content.isNull("edited_timestamp"))
            msg.setEdited(true);

        msg.setSender(group.getServer().getGroupUserById(content.getJSONObject("author").getString("id")));

        api.getEventManager().executeEvent(new UserChatEvent(group, msg.getSender(), msg));
    }
}
