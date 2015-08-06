package com.ceres.controller.v1.human;

import com.ceres.controller.RestController;
import com.ceres.controller.v1.views.UserView;
import com.ceres.domain.entity.human.Human;
import com.ceres.domain.entity.human.SystemUser;
import com.ceres.domain.entity.human.UserCategory;
import com.ceres.service.user.UserService;
import com.ceres.service.user.exception.AuthenticationException;
import com.wordnik.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

/**
 * Created by leonardo on 18/04/15.
 */
@Named("UserController")
@Singleton
@Path(RestController.V1)
@Api(value = "Usuários", basePath = "v1", description = "manipular usuários")
public final class UserController {

    protected static final String RESOURCE_URL = "usuario";

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String AUTHENTICATION_HEADER = "Authentication";

    private UserService userService;

    private UriInfo uriInfo;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Inject
    public UserController(UserService userService) {
        this.userService = userService;
        logger.info("UserController ready...");
    }

    @POST
    @Path(RESOURCE_URL + "/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "realizar login",
            consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "usuário nao encontrado"),
            @ApiResponse(code = 401, message = "usuário não tem permissão"),
            @ApiResponse(code = 200, message = "login realizado")
    })
    public Response doLogin(@ApiParam UserView userVO) {
        if (!userService.existsByUsername(userVO.username)) {
            logger.error("User " + userVO.username + " not found");
            Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }
        String token = "";
        try {
            token = userService.validateAndGetToken(userVO.username, userVO.password);
        } catch (AuthenticationException e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(e.getMessage())
                    .build();
        }

        SystemUser user = userService.findUserByToken(token);
        UserCategory role = userService.findUserByToken(token).getRole();
        return Response
                .ok()
                .entity(user)
                .header(AUTHENTICATION_HEADER, token)
                .header(AUTHORIZATION_HEADER, role.name())
                .build();
    }

    @GET
    @Path(RESOURCE_URL + "/login")
    @ApiOperation(value = "Iniciar fluxo de login")
    @ApiResponses(value = {
            @ApiResponse(code = 401, message = "usuário nao autorizado"),
    })
    public Response challenge() {

        return Response
                .status(Response.Status.UNAUTHORIZED)
                .header("WWW-Authenticate", "Basic")
                .build();
    }

    @GET
    @Path(RESOURCE_URL)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "recuperar todos os usuários", response = SystemUser.class, responseContainer = "Array")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "todos os usuários")
    })
    public Response getAllUser() {
        return Response
                .ok(userService.getAll())
                .build();
    }

    @GET
    @Path(RESOURCE_URL + "/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "recuperar um usuário dado um ID", response = SystemUser.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "usuário encontrado"),
            @ApiResponse(code = 404, message = "usuário nao encontrado")
    })
    public Response getUser(@PathParam("id") Long id) {
        if (!userService.exists(id)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        SystemUser user = userService.getById(id);

        return Response
                .ok(user)
                .build();
    }

    @PUT
    @Path(RESOURCE_URL + "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "atualizar o usuário dado um ID e uma representação em JSON",
            response = SystemUser.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "usuário nao encontrado"),
            @ApiResponse(code = 500, message = "erro ao atualizar o usuário"),
            @ApiResponse(code = 200, message = "usuário atualizado", response = SystemUser.class)
    })
    public Response updateUser(@PathParam("id") Long id, @ApiParam SystemUser user) {
        if (!userService.exists(id)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        user.setId(id);
        SystemUser u = userService.update(user);

        return Response
                .ok(u)
                .build();
    }

    @POST
    @Path(RESOURCE_URL)
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "criar um usuário dado um ID de seu respectivo dono",
            consumes = MediaType.TEXT_PLAIN
            , produces = MediaType.APPLICATION_JSON, response = SystemUser.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "entidade dona nao encontrada"),
            @ApiResponse(code = 500, message = "erro ao criar o usuário"),
            @ApiResponse(code = 200, message = "usuário criado", response = SystemUser.class)
    })
    public Response createUser(@QueryParam("pessoa") Long id, @ApiParam String password) {
        if (!userService.humanExists(id)) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Human human = userService.getHumanById(id);
        if (human.getUser() != null) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }

        SystemUser user = SystemUser.createUser(human.getContact().getEmail(), password);
        user.setHuman(human);

        SystemUser u = userService.create(user);

        URI createdURI = URI.create(uriInfo.getRequestUri() + "/" + u.getId());
        return Response
                .created(createdURI)
                .entity(u)
                .build();
    }

    @DELETE
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "remover um usuário")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "usuário nao encontrado"),
            @ApiResponse(code = 204, message = "usuário removido")
    })
    public Response deleteUser(@PathParam("id") Long id) {
        if (!userService.exists(id)) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

        userService.remove(id);

        return Response
                .noContent()
                .build();
    }


    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

}
