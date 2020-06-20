package main;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Main {

    private static final String TOKEN = "";

    public static void main(String[] args) throws LoginException {
        JDA jda = JDABuilder.createDefault(TOKEN).build();
    }

}
