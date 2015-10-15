package me.itsghost.jdiscord.internal.request;


import me.itsghost.jdiscord.*;
import me.itsghost.jdiscord.internal.request.poll.*;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;

import java.net.URI;

public class WebSocketClient extends org.java_websocket.client.WebSocketClient {
    public boolean loaded = false;
    protected Thread thread;
    private DiscordAPI api;
    private String server;
    //Hackky stuff
    private ReadyPoll readyPollProcessor;
    private BanPoll banPollProcessor;
    private AddUserPoll addUserPollProcessor;
    private MessagePoll messagePollProcessor;
    private KickPoll kickedPollProcessor;
    private TypingPoll typingPollProcessor;

    public WebSocketClient(DiscordAPI api, String url) {
        super(URI.create(url.replace("wss", "ws"))); //this api doesn't like wss
        this.api = api;
        readyPollProcessor = new ReadyPoll(api);
        banPollProcessor = new BanPoll(api);
        addUserPollProcessor = new AddUserPoll(api);
        messagePollProcessor = new MessagePoll(api);
        kickedPollProcessor = new KickPoll(api);
        typingPollProcessor = new TypingPoll(api);
        this.connect();
    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {
        loaded = true;
        api.log("Logged in and loaded!");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        if((code == 1000) && (server != null)){
            api.log("Your data is on a different server");
            api.log("This error is deprecated... if you're seening this, report it.");
        }
    }

    @Override
    public void onMessage(String message) {
        try {
            JSONObject obj = new JSONObject(message);
            if (obj.getInt("op") == 7)
                return;
            JSONObject key = obj.getJSONObject("d");
            String type = obj.getString("t");
            Server server = key.isNull("guild_id") ? null : api.getGroupById(key.getString("guild_id")).getServer();
            switch (type) {
                case "READY":
                    readyPollProcessor.process(key, obj, server);
                    api.log("Successfully loaded user data!");
                    break;
                case "GUILD_MEMBER_ADD":
                    addUserPollProcessor.process(key, obj, server);
                    break;
                case "GUILD_MEMBER_REMOVE":
                    kickedPollProcessor.process(key, obj, server);
                    break;
                case "GUILD_BAN_ADD":
                    banPollProcessor.process(key, obj, server);
                    break;
                case "GUILD_BAN_REMOVE":
                    //processBan(key, server);
                    //Unban?
                    break;
                case "MESSAGE_CREATE":
                    messagePollProcessor.process(key, obj, server);
                    break;
                case "MESSAGE_UPDATE":
                    messagePollProcessor.process(key, obj, server);
                    break;
                case "TYPING_START":
                    typingPollProcessor.process(key, obj, server);
                    break;
                default:
                    api.log("Unknown type " + type);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("Internal client error!");
        api.log("Attempting go log in (again?)!");
        api.stop();
        try {
            api.login();
        }catch(Exception e){}
    }

    public void stop(){
        this.close();
        readyPollProcessor.stop();
    }

}


