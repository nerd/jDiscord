package me.itsghost.jdiscord;

import me.itsghost.jdiscord.event.EventManager;
import me.itsghost.jdiscord.exception.BadUsernamePasswordException;
import me.itsghost.jdiscord.exception.DiscordFailedToConnectException;
import me.itsghost.jdiscord.exception.NoLoginDetailsException;
import me.itsghost.jdiscord.talkable.Group;
import me.itsghost.jdiscord.talkable.User;

import java.util.List;
import java.util.Map;

public interface DiscordAPI {
    void stop();
    User getUserById(String id);
    User getUserByUsername(String id);
    Server getServerById(String id);
    Group getGroupById(String id);
    DiscordAPI login() throws NoLoginDetailsException, BadUsernamePasswordException, DiscordFailedToConnectException;
    DiscordAPI login(String email, String password) throws BadUsernamePasswordException, DiscordFailedToConnectException;
    boolean isLoaded();
    EventManager getEventManager();
    List<User> getAvailableDms();
    List<Server> getAvailableServers();
    Map<String, Group> getUserGroups();
}
