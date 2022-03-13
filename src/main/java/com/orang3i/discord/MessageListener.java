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
       if(((msg.getContentRaw().substring(0, msg.getContentRaw().indexOf(' '))).equals(prefix + "ban"))&& length > 1){

           Guild guild = event.getGuild();
           guild.ban(msg.getMentionedUsers().get(0), 1).queue();

           msg.getChannel().sendMessage("banned " + msg.getMentionedUsers().get(0).getName()).queue();
       }
       if(((msg.getContentRaw().substring(0, msg.getContentRaw().indexOf(' '))).equals(prefix + "banid"))&& length > 1){

           Guild guild = event.getGuild();
           String[] sp = msg.getContentRaw().split(" ");
           String id = sp[1];
           guild.ban(User.fromId(id) ,1).queue();
           msg.getChannel().sendMessage("banned " + User.fromId(id).getName()).queue();
       }
        if(((msg.getContentRaw().substring(0, msg.getContentRaw().indexOf(' '))).equals(prefix + "unban"))&& length > 1){
            String[] sp = msg.getContentRaw().split(" ");
            String id = sp[1];
            Guild guild = event.getGuild();
            guild.unban(User.fromId(id)).queue();
            msg.getChannel().sendMessage("unbanned" + User.fromId(id).getName()).queue();
        }

        if(((msg.getContentRaw().substring(0, msg.getContentRaw().indexOf(' '))).equals(prefix + "whoid"))&& length > 1){
            String[] sp = msg.getContentRaw().split(" ");
            String id = sp[1];
            Guild guild = event.getGuild();

            msg.getChannel().sendMessage("they are" + User.fromId(id).getName()).queue();
        }

        if(((msg.getContentRaw().substring(0, msg.getContentRaw().indexOf(' '))).equals(prefix + "kick"))&& length > 1){

            Guild guild = event.getGuild();
            Member kickeed = msg.getMentionedMembers().get(0);
            guild.kick(kickeed).queue();

            msg.getChannel().sendMessage("kicked" + msg.getMentionedUsers().get(0).getName()).queue();
        }
    }

}