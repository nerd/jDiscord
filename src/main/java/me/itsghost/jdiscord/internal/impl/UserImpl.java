package me.itsghost.jdiscord.internal.impl;

import lombok.Getter;
import lombok.Setter;
import me.itsghost.jdiscord.DiscordAPI;
import me.itsghost.jdiscord.User;
import me.itsghost.jdiscord.internal.httprequestbuilders.PacketBuilder;
import me.itsghost.jdiscord.internal.httprequestbuilders.RequestType;
import me.itsghost.jdiscord.message.Message;
import me.itsghost.jdiscord.message.MessageHistory;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;


public class UserImpl implements User{
    @Getter private String username;
    @Getter private String id;
    @Getter private String cid;
    @Getter @Setter private String avatar;
    @Getter @Setter private String avatarId;
    private DiscordAPI api;
    public UserImpl(String username, String id, String cid,DiscordAPI api){
        this.api = api;
        this.id = id;
        this.cid = cid;
        this.username = username;
    }

    @Override
    public String toString(){
        return username;
    }

    @Override
    public Message sendMessage(String message) {
        return sendMessage(new MessageImpl(message));
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
        pb.setUrl(new String("https://discordapp.com/api/channels/" + cid + "/messages")); //ignore the new string; just to hide the intellij warning
        String a = pb.makeRequest();
        System.out.println(a);
        if (a != null)
            return new MessageImpl(message.getMessage(), new JSONObject(a).getString("id"), cid, api);
        return message;
    }

    @Override
    public MessageHistory getMessageHistory() {
        return null;
    }
}
