package com.orang3i;

import ch.qos.logback.classic.Level;
import com.orang3i.discord.MessageListener;
import lombok.Getter;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.orang3i.discord.MessageListener.event;

public class ProeBot {

    @Getter private static final Logger logger = (Logger) LoggerFactory.getLogger(ProeBot.class);
    @Getter private static ProeBot instance;

    @Getter private final MessageListener messageListener;

    private static final Properties PROPERTIES = new Properties();
    private static final DumperOptions DUMPER_OPTIONS = new DumperOptions();

    static {

        try(final InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("project.properties")) {
            PROPERTIES.load(inputStream);
        } catch (final IOException e) {
            throw new IllegalStateException("Could not load project.properties");
        }

        DUMPER_OPTIONS.setIndent(2);
        DUMPER_OPTIONS.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        DUMPER_OPTIONS.setPrettyFlow(true);
    }

    public ProeBot(final String[] args) {

        if(instance != null) {
            throw new IllegalStateException("Already initialized");
        }
        instance = this;

        for(final String arg : args) {
            //noinspection SwitchStatementWithTooFewBranches
            switch (arg) {
                case "--debug":
                    logger.setLevel(Level.ALL);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown argument: " + arg);
            }
        }

        logger.info("Loading " + PROPERTIES.getProperty("name") + " v" + PROPERTIES.getProperty("version") + " by ORANG3I");
        logger.info("GitHub: https://github.com/orang3i/ProeBot");
        logger.info("");

        MessageListener object = new MessageListener();
        object.discordLogin();
        object.onMessageReceived(event);

        messageListener = new MessageListener();

    }
}
