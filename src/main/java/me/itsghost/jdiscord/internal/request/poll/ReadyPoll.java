package me.itsghost.jdiscord.internal.request.poll;

import me.itsghost.jdiscord.DiscordAPI;
import me.itsghost.jdiscord.GroupUser;
import me.itsghost.jdiscord.SelfData;
import me.itsghost.jdiscord.Server;
import me.itsghost.jdiscord.events.APILoadedEvent;
import me.itsghost.jdiscord.internal.impl.GroupImpl;
import me.itsghost.jdiscord.internal.impl.ServerImpl;
import me.itsghost.jdiscord.internal.impl.UserImpl;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadyPoll implements Poll{
    private Thread thread;
    private DiscordAPI api;
    public ReadyPoll(DiscordAPI api){
       this.api = api;
    }

    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server) {
        SelfData data = new SelfData();
        JSONObject userDataJson = content.getJSONObject("user");
        data.setUsername(userDataJson.getString("username"));
        data.setEmail(userDataJson.getString("email"));
        data.setId(userDataJson.getString("id"));
        data.setAvatar("https://cdn.discordapp.com/avatars/" + data.getId() + "/" + userDataJson.getString("avatar") + ".jpg");
        data.setAvatarId(userDataJson.getString("avatar"));
        api.setSelfInfo(data);

        thread = new Thread(() -> {
            while (!api.getRequestManager().getSocketClient().getConnection().isClosed()) {
                api.getRequestManager().getSocketClient().send(new JSONObject().put("op", 1).put("d", System.currentTimeMillis()).toString());
                try {
                    Thread.sleep(content.getLong("heartbeat_interval"));
                } catch (Exception e) {
                    api.stop();
                }
            }
        });
        thread.start();

        setupContacts(content);
        setupServers(content);

        api.getEventManager().executeEvent(new APILoadedEvent());
        api.setLoaded(true);
    }

    public void setupContacts(JSONObject key) {
        JSONArray array = key.getJSONArray("private_channels");
        for (int i = 0; i < array.length(); i++) {
            JSONObject contact = array.getJSONObject(i).getJSONObject("recipient");
            JSONObject item = array.getJSONObject(i);
            String id = contact.getString("id");

            UserImpl userImpl = new UserImpl(contact.getString("username"), id, item.getString("id"), api);
            userImpl.setAvatar(contact.isNull("avatar") ? "" : "https://cdn.discordapp.com/avatars/" + id + "/" + contact.getString("avatar") + ".jpg");
            userImpl.setAvatarId(contact.isNull("avatar") ? "" : userImpl.getId());
            api.getAvailableDms().add(userImpl);
        }
    }

    public List<GroupUser> getGroupUsersFromJson(JSONObject obj, Map<String, Boolean> roles){
        JSONArray members = obj.getJSONArray("members");
        List<GroupUser> guList = new ArrayList<GroupUser>();
        for (int i = 0; i < members.length(); i++) {
            JSONObject item = members.getJSONObject(i);
            JSONObject user = item.getJSONObject("user");

            String username = user.getString("username");
            String id = user.getString("id");
            String dis = String.valueOf(user.get("discriminator")); //Sometimes returns an int or string... just cast the obj to string

            GroupUser.Role role = GroupUser.Role.USER;
            UserImpl userImpl = new UserImpl(username, id, id, api);
            userImpl.setAvatar(user.isNull("avatar") ? "" : "https://cdn.discordapp.com/avatars/" + id + "/" + user.getString("avatar") + ".jpg");

            if (item.getJSONArray("roles").length() > 0)
                if (roles.get(item.getJSONArray("roles").opt(0)))
                    role = GroupUser.Role.ADMIN;

            guList.add(new GroupUser(userImpl, role, dis));
        }
        return guList;
    }
    public void setupServers(JSONObject key) {
        JSONArray guids = key.getJSONArray("guilds");
        for (int i = 0; i <  guids.length(); i++) {
            JSONObject item = guids.getJSONObject(i);

            ServerImpl server = new ServerImpl(item.getString("id"), api);
            server.setTopic(item.getString("name"));
            server.setLocation(item.getString("region"));
            server.setCreatorId(item.getString("owner_id"));
            server.setAvatar(item.isNull("icon") ? "" : "https://cdn.discordapp.com/icons/" + server.getId() + "/" + item.getString("icon") + ".jpg");

            HashMap<String, Boolean> roles = new HashMap<String, Boolean>();
            JSONArray rolesArray = item.getJSONArray("roles");

            for (int ia = 0; ia < rolesArray.length(); ia++) {
                JSONObject roleObj = rolesArray.getJSONObject(ia);
                roles.put(roleObj.getString("id"), roleObj.getString("name").contains("admin") ? true : false);
            }

            server.getConnectedClients().addAll(getGroupUsersFromJson(item, roles));

            JSONArray channels = item.getJSONArray("channels");
            for (int ia = 0; ia < channels.length(); ia++) {
                JSONObject channel = channels.getJSONObject(ia);

                if (!channel.getString("type").equals("text"))
                    continue;

                GroupImpl group = new GroupImpl(channel.getString("id"), server, api);
                group.setName(channel.getString("name"));
                server.getGroups().add(group);
            }
            api.getAvailableServers().add(server);
        }
    }
    public void stop(){
        if (thread != null)
            thread.stop();
    }
}
