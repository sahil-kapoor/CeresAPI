package com.ceres.controller.json;

import com.ceres.domain.entity.human.SystemUser;
import com.ceres.domain.entity.human.UserCategory;
import org.junit.Before;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static com.ceres.domain.entity.human.SystemUser.createUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by Leonardo on 25/01/2015.
 */
public class UserJsonTest extends JsonTester {

    private static final String USER_JSON = "{jsonID:1,id:null,username:leo.rog,password:leoleo,role:ADMIN}";

    SystemUser user;

    @Before
    public void prepare() throws JAXBException {
        initializeJsonMapper();

        user = createUser("leo.rog", "leoleo", UserCategory.ADMIN);
    }

    @Override
    public void shouldSerializeToJson() throws IOException {
        String json = mapper.writeValueAsString(user).replace("\"", "");

        assertEquals(USER_JSON, json);
    }

    @Override
    public void shouldDeserializeFromJson() throws IOException {
        String json = mapper.writeValueAsString(user);
        SystemUser userDeserialized = mapper.readValue(json, SystemUser.class);

        assertNotNull(userDeserialized);
        assertTrue(user.equals(userDeserialized));
    }
}
