package com.ceres.controller.v1.chat;

import com.ceres.controller.RestController;
import com.ceres.controller.v1.human.NutritionistController;
import com.ceres.controller.v1.human.PatientController;
import com.ceres.controller.v1.views.ChatView;
import com.ceres.domain.entity.human.Nutritionist;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.entity.message.ChatMessage;
import com.ceres.service.chat.ChatService;
import com.ceres.service.human.NutritionistService;
import com.ceres.service.human.PatientService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.Map;

/**
 * Created by leonardo on 25/04/15.
 */
@Named("ChatController")
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Chat", basePath = "v1")
public final class ChatController {

    private static final String RESOURCE_URL = "chat";

    private ChatService chatService;

    private PatientService patientService;

    private NutritionistService nutritionistService;

    private UriInfo uriInfo;

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    @Inject
    public ChatController(ChatService chatService, PatientService patientService, NutritionistService nutritionistService) {
        this.chatService = chatService;
        this.patientService = patientService;
        this.nutritionistService = nutritionistService;
        logger.info("ChatController ready...");
    }

    @GET
    @Path(RESOURCE_URL + "/" + PatientController.RESOURCE_URL + "/{id}")
    @ApiOperation(value = "recuperar os cabeçalhos de conversas do paciente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "cabeçalhos de conversa", response = Map.class),
            @ApiResponse(code = 404, message = "paciente não encontrado")
    })
    public Response getPatientConversations(@PathParam("id") Long id) {
        if (!patientService.exists(id)) {
            logger.error("Patient" + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Patient p = patientService.getById(id);

        Map<Long, String> conversations = chatService.getConversationsOf(p);

        return Response.ok(conversations).build();
    }

    @GET
    @Path(RESOURCE_URL + "/" + NutritionistController.RESOURCE_URL + "/{id}")
    @ApiOperation(value = "recuperar os cabeçalhos de conversas do nutricionista")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "topicos de conversa", response = Map.class),
            @ApiResponse(code = 404, message = "nutricionista não encontrado")
    })
    public Response getNutritionistConversations(@PathParam("id") Long id) {
        if (!nutritionistService.exists(id)) {
            logger.error("Nutritionist" + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Nutritionist n = nutritionistService.getById(id);

        Map<Long, String> conversations = chatService.getConversationsOf(n);

        return Response.ok(conversations).build();
    }

    @GET
    @Path(RESOURCE_URL + "/{patientId}" + "/{nutritionistId}")
    @ApiOperation(value = "recuperar a conversa especificada")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "conversa entre as partes", response = Map.class),
            @ApiResponse(code = 404, message = "nutricionista ou paciente não encontrado")
    })
    public Response getMessage(@PathParam("patientId") Long patientId, @PathParam("nutritionistId") Long nutritionistId) {
        if (!nutritionistService.exists(nutritionistId) || !patientService.exists(patientId)) {
            logger.error("Nutritionist or Patinet not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Nutritionist n = nutritionistService.getById(nutritionistId);
        Patient p = patientService.getById(patientId);

        List<ChatView> chatView = chatService.getChatMessages(p, n);

        return Response.ok(chatView).build();
    }

    @POST
    @Path(RESOURCE_URL + "/{senderId}" + "/{receiverId}")
    @ApiOperation(value = "recuperar os cabeçalhos de conversas do nutricionista")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "conversa registrada", response = ChatMessage.class),
            @ApiResponse(code = 404, message = "nutricionista ou paciente não encontrado")
    })
    public Response sendMessage(@PathParam("senderId") Long senderId, @PathParam("receiverId") Long receiverId, String message) {
        if (!patientService.exists(senderId) && !nutritionistService.exists(senderId)) {
            logger.error("unknown sender");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (!patientService.exists(receiverId) && !nutritionistService.exists(receiverId)) {
            logger.error("unknown receiver");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        ChatMessage chatMessage = chatService.registryMessage(senderId, receiverId, message);

        return Response.accepted().entity(chatMessage).build();
    }


    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
