package me.itsghost.jdiscord.internal.impl;

import lombok.Getter;
import lombok.Setter;
import me.itsghost.jdiscord.DiscordAPIImpl;
import me.itsghost.jdiscord.Server;
import me.itsghost.jdiscord.internal.httprequestbuilders.PacketBuilder;
import me.itsghost.jdiscord.internal.httprequestbuilders.RequestType;
import me.itsghost.jdiscord.talkable.Group;
import me.itsghost.jdiscord.talkable.GroupUser;

import java.util.ArrayList;
import java.util.List;

public class ServerImpl implements Server {
    @Getter @Setter private String id;
    @Getter @Setter private String topic;
    @Getter @Setter private String location;
    @Getter @Setter private String creatorId;
    @Getter @Setter private String avatar;
    @Getter @Setter private List<GroupUser> connectedClients = new ArrayList<GroupUser>();
    @Getter @Setter private List<Group> groups = new ArrayList<Group>();

    private DiscordAPIImpl api;

    public ServerImpl(String id, DiscordAPIImpl api) {
        this.api = api;
        this.id = id;
    }

    public String toString() {
        return id;
    }

    @Override
    public GroupUser getGroupUserById(String id) {
        for (GroupUser user : connectedClients)
            if (user.getUser().getId().equals(id))
                return user;
        return null;
    }

    @Override
    public Group getGroupById(String id) {
        for (Group group : getGroups())
            if (group.getId().equals(id))
                return group;
        return null;
    }

    @Override
    public GroupUser getGroupUserByUsername(String id) {
        for (GroupUser user : connectedClients)
            if (user.getUser().getId().equals(id))
                return user;
        return null;

    }

    @Override
    public void bc(String message) {
        for (Group group : getGroups())
            group.sendMessage(message);
    }

    @Override
    public void kick(String user) {
        PacketBuilder pb = new PacketBuilder(api);
        pb.setUrl("https://discordapp.com/api/guilds/" + id + "/members/" + getGroupUserByUsername(user).getUser().getId());
        pb.setType(RequestType.DELETE);
        pb.makeRequest();
    }

    @Override
    public void ban(String user) {
        PacketBuilder pb = new PacketBuilder(api);
        pb.setUrl("https://discordapp.com/api/guilds/" + id + "/bans/" + getGroupUserByUsername(user).getUser().getId() + "?delete-message-days=0");
        pb.setType(RequestType.PUT);
        pb.makeRequest();
    }
}
