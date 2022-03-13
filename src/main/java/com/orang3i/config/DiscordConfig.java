package com.orang3i.config;

import com.orang3i.util.YamlUtils;

import java.util.Map;

public class DiscordConfig {

    private final Map<String,Object> config = YamlUtils.loadFile("discord.yml");

    public String getBotToken() {
        final String botToken = getOrThrow("bot-token");
        if(botToken.equals("your-bot-token")) {
            throw new IllegalArgumentException("Setup a valid bot-token in discord.yml.");
        }
        return botToken;
    }


    private String getOrThrow(final String path) {
        if(config.containsKey(path)) return (String) config.get(path);
        throw new IllegalArgumentException("DiscordConfig option " + path + " not set");
    }

}
