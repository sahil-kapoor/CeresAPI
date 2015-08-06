package com.ceres.controller.v1.menu;

import com.ceres.controller.RestController;
import com.ceres.controller.v1.human.PatientController;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.entity.menu.Menu;
import com.ceres.service.human.PatientService;
import com.ceres.service.menu.MenuService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Set;

/**
 * Created by leonardo on 18/03/15.
 */
@Named("PatientMenuController")
@Singleton
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Cardápios", basePath = "v1")
public final class PatientMenuController {

    protected static final String RESOURCE_URL = "cardapio";

    private MenuService menuService;

    private UriInfo uriInfo;

    private PatientService patientService;

    private static final Logger logger = LoggerFactory.getLogger(PatientMenuController.class);

    @Inject
    public PatientMenuController(MenuService menuService, PatientService patientService) {
        this.menuService = menuService;
        this.patientService = patientService;
        logger.info("PatientMenuController ready...");
    }

    @GET
    @Path(PatientController.RESOURCE_URL + "/" + RESOURCE_URL + "/{id}")
    @ApiOperation(value = "recuperar um cardápio dado seu ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "o cardápio", response = Menu.class),
            @ApiResponse(code = 404, message = "cardápio não encontrado")
    })
    public Response getPatientsMenu(@PathParam("id") Long menuId) {
        if (!menuService.exists(menuId)) {
            logger.error("Menu " + menuId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Menu m = menuService.getMenuById(menuId);

        return Response.ok(m).build();
    }

    @GET
    @Path(PatientController.RESOURCE_URL + "/{id}/" + RESOURCE_URL)
    @ApiOperation(value = "recuperar todos os cardápios de um paciente", responseContainer = "Set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "os cardápios do paciente", response = Menu.class),
            @ApiResponse(code = 404, message = "paciente não encontrado")
    })
    public Response getAllPatientsMenu(@PathParam("id") Long patientId) {
        if (!patientService.exists(patientId)) {
            logger.error("Patient " + patientId + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Patient p = patientService.getById(patientId);
        Set<Menu> menus = menuService.getAllByPatient(p);

        return Response.ok(menus).build();

    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
