package me.itsghost.jdiscord;

import lombok.Getter;
import lombok.Setter;
import me.itsghost.jdiscord.event.EventManager;
import me.itsghost.jdiscord.exception.BadUsernamePasswordException;
import me.itsghost.jdiscord.exception.DiscordFailedToConnectException;
import me.itsghost.jdiscord.exception.NoLoginDetailsException;
import me.itsghost.jdiscord.internal.Login;
import me.itsghost.jdiscord.internal.impl.UserImpl;
import me.itsghost.jdiscord.internal.request.RequestManager;
import me.itsghost.jdiscord.message.MessageHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscordAPI {
    @Getter private Login loginTokens = new Login();
    @Getter private List<User> availableDms = new ArrayList<User>();
    @Getter private List<User> cachedUsers = new ArrayList<User>();
    @Getter private List<Server> availableServers = new ArrayList<Server>();
    @Getter @Setter private boolean debugMode = true;
    @Getter @Setter private boolean allowLogMessages = true;
    @Getter private EventManager eventManager = new EventManager();
    @Getter @Setter private RequestManager requestManager;
    @Getter @Setter private SelfData selfInfo;
    @Getter @Setter private Map<String, MessageHistory> historyContainer = new HashMap<String, MessageHistory>();
    @Getter @Setter private boolean loaded = false;

    public DiscordAPI(String email, String password){
        loginTokens.setUsername(email);
        loginTokens.setPassword(password);
    }

    public DiscordAPI(){}

    public DiscordAPI login(String email, String password) throws BadUsernamePasswordException, DiscordFailedToConnectException{
        loginTokens.setUsername(email);
        loginTokens.setPassword(password);
        try {
            login();
        } catch(NoLoginDetailsException e){
        } catch(BadUsernamePasswordException | DiscordFailedToConnectException e){
            throw e;
        }
        return this;
    }

    public DiscordAPI login() throws NoLoginDetailsException, BadUsernamePasswordException, DiscordFailedToConnectException{
        if ((loginTokens.getUsername() == null) || (loginTokens.getPassword() == null))
            throw new NoLoginDetailsException();
        loginTokens.process(this);
        return this;
    }

    public void log(String log){
        if (allowLogMessages)
            System.out.println("DiscordAPI: " + log);
    }

    public Group getGroupById(String id){
        for (Server server : availableServers)
            for (Group channel : server.getGroups())
                if (channel.getId().equals(id))
                    return channel;
        return null;
    }

    public Server getServerById(String id){
        for (Server server : availableServers)
            if (server.equals(id))
                return server;
        return null;
    }

    public User getUserByUsername(String id){
        for (User user : availableDms)
            if (user.equals(id))
                return user;
        for (User user : cachedUsers)
            if (user.equals(id))
                return user;
        return new UserImpl(id, id, id, this);
    }

    public void stop(){
        log("Shutting down!");
        requestManager.getSocketClient().stop();
    }
}
