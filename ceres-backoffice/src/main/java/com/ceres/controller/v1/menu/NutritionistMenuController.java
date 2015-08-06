package com.ceres.controller.v1.menu;

import com.ceres.controller.RestController;
import com.ceres.controller.v1.human.NutritionistController;
import com.ceres.controller.v1.human.PatientController;
import com.ceres.domain.entity.human.Nutritionist;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.entity.menu.Menu;
import com.ceres.service.human.NutritionistService;
import com.ceres.service.human.PatientService;
import com.ceres.service.menu.MenuService;
import com.ceres.service.menu.exception.MenuException;
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
import java.time.LocalDate;
import java.util.Set;

/**
 * Created by Leonardo on 25/03/15.
 */
@Named("NutritionistMenuController")
@Singleton
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Cardápios", basePath = "v1")
public final class NutritionistMenuController {

    protected static final String RESOURCE_URL = "cardapio";

    private NutritionistService nutritionistService;

    private UriInfo uriInfo;

    private PatientService patientService;

    private MenuService menuService;

    private static final Logger logger = LoggerFactory.getLogger(NutritionistMenuController.class);

    @Inject
    public NutritionistMenuController(NutritionistService nutritionistService, PatientService patientService, MenuService menuService) {
        this.nutritionistService = nutritionistService;
        this.patientService = patientService;
        this.menuService = menuService;
        logger.info("NutritionistMenuController ready...");
    }

    @GET
    @Path(NutritionistController.RESOURCE_URL + "/" + RESOURCE_URL + "/{id}")
    @ApiOperation(value = "recuperar um cardápio dado seu ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "o cardápio", response = Menu.class),
            @ApiResponse(code = 404, message = "cardápio não encontrado")
    })
    public Response getMenu(@PathParam("id") Long menuId) {
        if (!menuService.exists(menuId)) {
            logger.error("Menu " + menuId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Menu menu = menuService.getMenuById(menuId);

        return Response.ok(menu).build();
    }

    @GET
    @Path(NutritionistController.RESOURCE_URL + "/{id}/" + RESOURCE_URL)
    @ApiOperation(value = "recuperar todos os cardápios de um nutricionista", responseContainer = "Set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "os cardápios do nutricionista", response = Menu.class),
            @ApiResponse(code = 404, message = "paciente não encontrado")
    })
    public Response getAllNutritionistMenu(@PathParam("id") Long nutritionistId) {
        if (!nutritionistService.exists(nutritionistId)) {
            logger.error("Nutritionist " + nutritionistId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Nutritionist n = nutritionistService.getById(nutritionistId);
        Set<Menu> menus = menuService.getAllByNutritionist(n);

        return Response.ok(menus).build();

    }

    @POST
    @Path(NutritionistController.RESOURCE_URL + "/{id}/" + PatientController.RESOURCE_URL + "/{menuId}/" + RESOURCE_URL)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "criar novo cardápio")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "cardápio criado", response = Menu.class),
            @ApiResponse(code = 404, message = "nutricionista não encontrado"),
            @ApiResponse(code = 500, message = "erro ao criar cardápio"),

    })
    public Response createMenu(@PathParam("id") Long nutritionistId, @PathParam("pid") Long patientId, @ApiParam Menu menu) {
        if (!nutritionistService.exists(nutritionistId) || !patientService.exists(patientId)) {
            logger.error("Nutritionist or Patient not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Patient p = patientService.getById(patientId);
        menu.setPatient(p);

        Nutritionist n = nutritionistService.getById(nutritionistId);
        menu.setNutritionist(n);

        menu.setCreationDate(LocalDate.now());

        try {
            menu = menuService.createOrUpdate(menu);
        } catch (MenuException e) {
            logger.error("Cannot create new Menu", e.getMessage(), e.getStackTrace());
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }

        URI createdURI = URI.create(uriInfo.getRequestUri() + "/" + menu.getId());
        return Response.created(createdURI).entity(menu).build();
    }

    @PUT
    @Path(NutritionistController.RESOURCE_URL + "/" + RESOURCE_URL + "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "atualizar cardápio existente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "cardápio atualizado", response = Menu.class),
            @ApiResponse(code = 404, message = "cardápio não encontrado"),
            @ApiResponse(code = 500, message = "erro ao atualizar cardápio"),

    })
    public Response updateMenu(@PathParam("id") Long menuId, @ApiParam Menu menu) {
        if (!menuService.exists(menuId)) {
            logger.error("Menu " + menuId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        try {
            menu = menuService.createOrUpdate(menu);
        } catch (MenuException e) {
            logger.error("Cannot update Menu " + menuId, e.getMessage(), e.getStackTrace());
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }

        return Response.ok().entity(menu).build();
    }

    @DELETE
    @Path(NutritionistController.RESOURCE_URL + "/" + RESOURCE_URL + "/{id}")
    @ApiOperation(value = "remover cardápio")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "cardápio removido"),
            @ApiResponse(code = 404, message = "cardápio não encontrado"),
            @ApiResponse(code = 500, message = "erro ao remover cardápio"),
    })
    public Response deleteMenu(@PathParam("id") Long menuId) {
        if (!menuService.exists(menuId)) {
            logger.error("Menu " + menuId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        menuService.remove(menuId);

        return Response.noContent().build();
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
