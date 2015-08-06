package com.ceres.controller.v1.menu;

import com.ceres.controller.RestController;
import com.ceres.domain.entity.menu.Menu;
import com.ceres.domain.entity.menu.MenuContent;
import com.ceres.service.menu.MenuContentService;
import com.ceres.service.menu.MenuService;
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
import java.util.Set;

/**
 * Created by Leonardo on 25/03/15.
 */
@Named("MenuContentController")
@Singleton
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Alimentos do Cardápio", basePath = "v1")
public final class MenuContentController {

    protected static final String RESOURCE_URL = "conteudo";

    private MenuService menuService;

    private UriInfo uriInfo;

    private MenuContentService menuContentService;

    private static final Logger logger = LoggerFactory.getLogger(MenuContentController.class);

    @Inject
    public MenuContentController(MenuService menuService, MenuContentService menuContentService) {
        this.menuService = menuService;
        this.menuContentService = menuContentService;
        logger.info("MenuContentController ready...");
    }

    @GET
    @Path(NutritionistMenuController.RESOURCE_URL + "/{id}/" + RESOURCE_URL)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "recuperar períodos e alimentos de um cardápio dado seu ID", responseContainer = "Set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "os períodos e alimentos do cardápio", response = MenuContent.class),
            @ApiResponse(code = 404, message = "cardápio não encontrado")
    })
    public Response getAllFromMenu(@PathParam("id") Long menuId) {
        if (!menuService.exists(menuId)) {
            logger.error("Menu " + menuId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Menu m = menuService.getMenuById(menuId);

        return Response.ok(m.getMeals()).build();
    }

    @GET
    @Path(NutritionistMenuController.RESOURCE_URL + "/" + RESOURCE_URL + "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "recuperar alimentos de um período de alimentação específico")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "o período de alimentação", response = MenuContent.class),
            @ApiResponse(code = 404, message = "período de alimentação não encontrado")
    })
    public Response getMenuContent(@PathParam("id") Long contentId) {
        if (!menuContentService.exists(contentId)) {
            logger.error("MenuContent " + contentId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        MenuContent menuContent = menuContentService.getById(contentId);

        return Response.ok(menuContent).build();
    }

    @PUT
    @Path(NutritionistMenuController.RESOURCE_URL + "/" + RESOURCE_URL + "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "atualizar alimentos de um período de alimentação específico")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "período de alimentação atualizado", response = MenuContent.class),
            @ApiResponse(code = 404, message = "período de alimentação não encontrado"),
            @ApiResponse(code = 500, message = "erro ao atualizar período de alimentação")
    })
    public Response updateMenuContent(@PathParam("id") Long contentId, @ApiParam MenuContent content) {
        if (!menuContentService.exists(contentId)) {
            logger.error("MenuContent " + contentId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        content.setId(contentId);
        content = menuContentService.createOrUpdate(content);

        return Response.ok().entity(content).build();
    }

    @POST
    @Path(NutritionistMenuController.RESOURCE_URL + "/{id}/" + RESOURCE_URL)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "criar um período de alimentação para um cardápio específico")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "período de alimentação criado"),
            @ApiResponse(code = 404, message = "cardápio não encontrado"),
            @ApiResponse(code = 500, message = "erro ao criar período de alimentação")
    })
    public Response addFoods(@PathParam("id") Long menuId, @ApiParam Set<MenuContent> contents) {
        if (!menuService.exists(menuId)) {
            logger.error("Menu " + menuId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Menu m = menuService.getMenuById(menuId);
        contents.forEach(k -> k.setMenu(m));
        menuContentService.createOrUpdate(contents);

        return Response.accepted().build();
    }

    @DELETE
    @Path(NutritionistMenuController.RESOURCE_URL + "/" + RESOURCE_URL + "/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "período de alimentação removido"),
            @ApiResponse(code = 404, message = "período de alimentação não encontrado"),
            @ApiResponse(code = 500, message = "erro ao remover período de alimentação")
    })
    public Response removeContent(@PathParam("id") Long contentId) {
        if (!menuContentService.exists(contentId)) {
            logger.error("MenuContent " + contentId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        menuContentService.remove(contentId);

        return Response.noContent().build();
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
