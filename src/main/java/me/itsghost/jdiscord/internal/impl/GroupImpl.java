package me.itsghost.jdiscord.internal.impl;

import lombok.Getter;
import lombok.Setter;
import me.itsghost.jdiscord.DiscordAPI;
import me.itsghost.jdiscord.Group;
import me.itsghost.jdiscord.Server;
import me.itsghost.jdiscord.internal.httprequestbuilders.PacketBuilder;
import me.itsghost.jdiscord.internal.httprequestbuilders.RequestType;
import me.itsghost.jdiscord.message.Message;
import me.itsghost.jdiscord.message.MessageHistory;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;


public class GroupImpl implements Group{
    @Getter @Setter private String id;
    @Getter @Setter private String name;
    private DiscordAPI api;
    private Server server;

    public GroupImpl(String id, Server server, DiscordAPI api){
        this.api = api;
        this.id = id;
        this.server = server;
    }

    @Override
    public String toString(){
        return id;
    }


    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public MessageHistory getMessageHistory() {
        if (api.getHistoryContainer().containsKey(id))
            api.getHistoryContainer().put(id, new MessageHistory());
        return api.getHistoryContainer().get(id);
    }

    @Override
    public Message sendMessage(String message) {
        return sendMessage(new MessageImpl(message, id, id, api));
    }

    @Override
    public Message sendMessage(Message messageInterface) {
        MessageImpl message = (MessageImpl) messageInterface;
        message.setId(String.valueOf(System.currentTimeMillis()));
        PacketBuilder pb = new PacketBuilder(api);
        pb.setType(RequestType.POST);
        pb.setData(new JSONObject().put("content", StringEscapeUtils.escapeJson(message.getMessage()))
                .put("mentions", message.getMentions())
                .put("nonce", message.getId())
                .put("tts", false).toString());
        pb.setUrl("https://discordapp.com/api/channels/" + id + "/messages");
        String a = pb.makeRequest();
        if (a != null)
            return new MessageImpl(message.getMessage(), new JSONObject(a).getString("id"), id, api);
        return message;
    }

    private JSONObject getLocalAuthor(){
        JSONObject a = new JSONObject()
                .put("username", api.getSelfInfo().getUsername())
                .put("discriminator", server.getGroupUserByUsername(api.getSelfInfo().getUsername()).getDiscriminator())
                .put("avatar", api.getSelfInfo().getAvatarId());
        return a;
    }

    private String getTimestamp(){
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "T" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + ".135000+00:00";
    }

}
