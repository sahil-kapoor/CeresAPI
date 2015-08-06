package com.ceres.util;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by leonardo on 28/03/15.
 */
public class MessageResolverTest {

    private String message = "Mensagem de {0} com {1} {2}";

    private final String correctMessage = "Mensagem de erro com parâmetro dinâmico";


    @Test
    public void shouldReplaceWithParameters() {

        String resultMessage = MessageResolver.resolve(message, Arrays.asList("erro", "parâmetro", "dinâmico"));

        assertEquals(correctMessage, resultMessage);

    }

}
