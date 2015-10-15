#jDiscord
(Very early in development -- currently concept code)

#Download

We're just doing testing some stuff right now!

#Getting a discord api instance

The api doesn't have a builder, just create a new instance of the DiscordAPI class, and optionally use #login like a builder method.

Examples:
```java
DiscordAPI api = new DiscordAPI("email", "pass").login();

DiscordAPI api = new DiscordAPI("email", "pass");
api.login();
```

#Events

- APILoadedEvent
- UserBannedEvent
- UserChatEvent
- UserJoinedEvent
- UserKickedEvent
- UserTypingEvent

#Using the event manager
In order to listen for an event, create a class that implements EventListener, and register it by calling "api.getEventManager().registerListener(new YourListener(api));" All event's can be found the "me.itsghost.jdiscord.events" package and in the event section of this readme file. 

```java
public class ExampleListener implements EventListener {
    DiscordAPI api;
    public ExampleListener(DiscordAPI api){
        this.api = api;
    }
    public void UserChat(UserChatEvent e){
        if (e.getMsg().getMessage().equals("#ping")){
            e.getGroup().sendMessage(new MessageBuilder()
                    .addString("Yes, ")
                    .addUserTag(e.getGroupUser(), e.getGroup())
                    .addString("?")
                    .build());
        }
        System.out.println((e.getMsg().isEdited() ? "# " : "") + "[" + e.getGroup().getName() + "] " + e.getGroupUser() + " > " + e.getMsg().getMessage());
    }
    public void Typing(UserTypingEvent e){
        System.out.println(e.getGroupUser() + " is typing in " + e.getGroup());
    }
}

public class Test {
    public static void main(String[] args) {
        DiscordAPI api = new DiscordAPI("email", "pass").login();
        api.getEventManager().registerListener(new ExampleListener(api)); //Register listener
    }
}
```

#Why doesn't 'X' work?
Simple, either you're doing something before the api has loaded fully (APILoadedEvent / DiscordAPI#isLoaded), or it's because this is still a concept.

#Dependencies
- Apache commons lang 3
- lombok
- org.json
- org.java-websocket
