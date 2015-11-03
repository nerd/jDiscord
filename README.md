#jDiscord

This API thrives to be the best DiscordAPI written in Java, adding features no other api has got, such as Account Management, and VOIP (WIP).

Download: see bottom of the page

#Features
- Profile settings/account settings		(API exclusive)
- Message Building
- Online statuses						(This includes your own status! API exclusive)
- Avatars + Roles 
- DMs
- Group messaging
- User talk (edited) event 
- User join/banned/kicked events
- And a lot more 

#Events
- AddedToServer (AddedToGuildEvent)
- APILoadedEvent      (You might get NPEs if you don't wait for this)
- ChannelCreatedEvent (group/channel)
- ChannelDeletedEvent (group/channel)
- ChannelUpdatedEvent (group/channel)
- UserBannedEvent
- UserChatEvent
- UserJoinedEvent
- UserKickedEvent
- UserTypingEvent
- UserOnlineStatusChangedEvent
- UserDeletedMessageEvent
- MentionEvent (1.3)

#TODO
- Message history (near enough done)
- Set what game you're playing
- VOIP (Java and C# Host) (currently experimental)

#Getting a discord api instance

In order to create the DiscordAPI instance, you'll need to use the DiscordBuilder builder class. 

Examples:
```java
DiscordAPI api = new DiscordBuilder("email", "pass").build().login();

DiscordAPI api = new DiscordBuilder("email", "pass").build();
api.login();
```

#Using the event manager
In order to listen for an event, create a class that implements EventListener, and register it by calling "api.getEventManager().registerListener(new YourListener(api));" All event's can be found the "me.itsghost.jdiscord.events" package and in the event section of this readme file. 

```java
public class ExampleListener implements EventListener {
    DiscordAPI api;
    public ExampleListener(DiscordAPI api){
        this.api = api;
    }
    public void userChat(UserChatEvent e){
        if (e.getMsg().getMessage().equals("#ping")){
            e.getGroup().sendMessage(new MessageBuilder()
                    .addString("Yes, ")
                    .addUserTag(e.getGroupUser(), e.getGroup())
                    .addString("?")
                    .build());
        }
        System.out.println((e.getMsg().isEdited() ? "# " : "") + "[" + e.getGroup().getName() + "] " + e.getGroupUser() + " > " + e.getMsg().getMessage());
    }
    public void typing(UserTypingEvent e){
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
#Shutup and take my money  (Now using shaded jar due to compatibility issues with past builds)

Maven: http://itsghost.me/maven

Repository:
```
 <repository>
  <id>xyz.gghost</id>
  <url>http://gghost.xyz/maven/</url>
</repository>
```
Dependency:
```
<dependency>
  <groupId>xyz.gghost</groupId>
  <artifactId>jdiscord</artifactId>
  <version>1.2</version>
  <scope>compile</scope>
</dependency>
```


#Dependencies
- Apache commons lang 3
- lombok
- org.json
- org.java-websocket
- http://itsghost.me/commons-codec-1.10.jar
