package com.ceres.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by leonardo on 28/03/15.
 */
public class MessageResolver {

    private static final String REGEX = "\\{[0-9]\\}";

    public static String resolve(String message, List<String> args) {

        Pattern pattern = Pattern.compile(REGEX);
        Matcher m = pattern.matcher(message);

        validateMessage(args, m);

        int i = 0;
        while (m.find()) {
            message = message.replace("{" + i + "}", args.get(i));
            i++;
        }

        return message;

    }

    private static void validateMessage(List<String> args, Matcher m) {
        int argsCount = 0;

        while (m.find()) {
            argsCount++;
        }

        if (argsCount != args.size()) {
            throw new RuntimeException("Argumentos inválidos para criação de mensagem. Esperaco <" + args.size() + "> Encontrado <" + argsCount + ">");
        }

        m.reset();
    }

}
