package com.orang3i.discord;

import com.orang3i.ProeBot;
import com.orang3i.config.DiscordConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class MessageListener extends ListenerAdapter {

    private JDA jda;
   private JDABuilder builder;

    public static MessageReceivedEvent event;

    private static final ProeBot main = ProeBot.getInstance();

    public void discordLogin() throws NullPointerException{


        final DiscordConfig discordConfig = new DiscordConfig();
        try {
            jda = JDABuilder.createDefault(discordConfig.getBotToken()).build();

            jda.awaitReady();
        } catch (final LoginException | InterruptedException e) {
            throw new IllegalStateException("Could not login to Discord, check your bot-token in discord.yml",e);
        }
        jda.getPresence().setActivity(Activity.playing("Type !ping"));
        jda.addEventListener(new MessageListener());
    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event) throws NullPointerException {

        String  prefix = "!";
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals(prefix+"ping"))
        {
            MessageChannel channel = event.getChannel();
            long time = System.currentTimeMillis();
            channel.sendMessage("Pong!") /* => RestAction<Message> */
                    .queue(response /* => Message */ -> {
                        response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
                    });
        }

        if(msg.getContentRaw().equals(prefix+"whoami")){
            String i = msg.getAuthor().getName();
            msg.getChannel().sendMessage("you are "+i).queue();
        }
    }
}