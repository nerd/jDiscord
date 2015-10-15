package me.itsghost.jdiscord.internal.request.poll;

import me.itsghost.jdiscord.DiscordAPI;
import me.itsghost.jdiscord.GroupUser;
import me.itsghost.jdiscord.Server;
import me.itsghost.jdiscord.events.UserBannedEvent;
import org.json.JSONObject;


public class BanPoll implements Poll {
    private DiscordAPI api;
    public BanPoll(DiscordAPI api){
        this.api = api;
    }
    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server) {
        JSONObject user = content.getJSONObject("user");
        GroupUser gUser = server.getGroupUserById(user.getString("id"));
        server.getConnectedClients().remove(gUser);
        api.getEventManager().executeEvent(new UserBannedEvent(server, gUser));
    }
}
