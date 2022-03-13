package com.orang3i.discord;

import com.orang3i.ProeBot;
import com.orang3i.config.DiscordConfig;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.util.List;

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
        jda.getPresence().setActivity(Activity.watching("how to become proe by ChongJF"));
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
        String[] words = msg.getContentRaw().toString().split("\\s+");
        int length = words.length;
        try {
            if (((msg.getContentRaw().substring(0, msg.getContentRaw().indexOf(' '))).equals(prefix + "whois"))&& length > 1) {




                if (length > 1) {

                    String i = msg.getMentionedUsers().get(0).getName();

                    if(i != null) {
                        msg.getChannel().sendMessage("they are " + i + " they created their account on: " + msg.getMentionedUsers().get(0).getTimeCreated().toString().substring(0, msg.getMentionedUsers().get(0).getTimeCreated().toString().length() - 14)).queue();
                    }
                }
            }
        }catch (Exception e){}

        if(msg.getContentRaw().equals(prefix+"whois")){

            String i = msg.getAuthor().getName();
            //get message author join date



            msg.getChannel().sendMessage("you are " + i + " you created your account on: " + msg.getAuthor().getTimeCreated().toString().substring(0, msg.getAuthor().getTimeCreated().toString().length() - 14)).queue();
        }
        try {
            if (((msg.getContentRaw().substring(0, msg.getContentRaw().indexOf(' '))).equals(prefix + "whoid")) && length > 1) {
                String str = msg.getContentRaw();
                String[] sp = str.split(" ");
                Long id = Long.parseLong(sp[1]);
                msg.getChannel().sendMessage("the id is " + id).queue();
            }
        }catch (Exception e){}
    }
}