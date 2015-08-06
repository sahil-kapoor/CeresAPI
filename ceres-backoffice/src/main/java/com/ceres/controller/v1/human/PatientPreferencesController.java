package com.ceres.controller.v1.human;

import com.ceres.controller.RestController;
import com.ceres.domain.entity.human.Patient;
import com.ceres.domain.entity.taco.Food;
import com.ceres.service.human.PatientService;
import com.ceres.service.human.exception.HumanException;
import com.ceres.service.taco.FoodService;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Leonardo on 25/03/15.
 */
@Named("PatientPreferencesController")
@Singleton
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Pacientes", basePath = "v1", description = "manipular preferências do paciente", produces = MediaType.APPLICATION_JSON)
public final class PatientPreferencesController {

    protected static final String RESOURCE_URL = "preferencia";

    private PatientService patientService;

    private UriInfo uriInfo;

    private FoodService foodService;

    private static final Logger logger = LoggerFactory.getLogger(PatientPreferencesController.class);

    @Inject
    public PatientPreferencesController(PatientService patientService, FoodService foodService) {
        this.patientService = patientService;
        this.foodService = foodService;
        logger.info("PatientPreferencesController ready...");
    }

    @GET
    @Path(PatientController.RESOURCE_URL + "/{id}/" + RESOURCE_URL)
    @ApiOperation(value = "recuperar preferências de um paciente", response = Map.class, notes = "retorna mapa com ID e Nome do alimento")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "mapa com as preferências"),
            @ApiResponse(code = 404, message = "paciente não encontrado")
    })
    public Response getAllPreferences(@PathParam("id") Long id) {
        if (!patientService.exists(id)) {
            logger.error("Patient " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Patient p = patientService.getById(id);

        Map<Long, String> preferences = new HashMap<>();
        p.getPreferences().forEach(k -> preferences.put(k.getId(), k.getName()));

        return Response
                .ok(preferences)
                .build();
    }

    @POST
    @Path(PatientController.RESOURCE_URL + "/{id}/" + RESOURCE_URL)
    @ApiOperation(value = "atribuir uma preferência a um paciente", consumes = MediaType.APPLICATION_JSON)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "paciente que a preferência foi atribuída", response = Patient.class),
            @ApiResponse(code = 404, message = "paciente não encontrado")
    })
    public Response addPreference(@PathParam("id") Long id, @ApiParam List<Long> foods) {
        if (!patientService.exists(id)) {
            logger.error("Patient or food not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Patient p = patientService.getById(id);

        final List<Food> foodsToAdd = new ArrayList<>();
        foods.stream().forEach(k -> foodsToAdd.add(foodService.getById(k)));
        p.addAllPreference(foodsToAdd);

        try {
            p = patientService.update(p);
        } catch (HumanException e) {
            logger.error("Cannot add food to patient's food preferences");
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }

        URI createdURI = URI.create(uriInfo.getRequestUri() + "/" + p.getId());
        return Response
                .created(createdURI)
                .entity(p)
                .build();
    }

    @DELETE
    @Path(PatientController.RESOURCE_URL + "/{id}/" + RESOURCE_URL + "/{foodId}")
    @ApiOperation(value = "remover uma preferência de um paciente")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "preferência foi removida"),
            @ApiResponse(code = 404, message = "paciente não encontrado || restrição não encontrada")
    })
    public Response removePreference(@PathParam("id") Long id, @PathParam("foodId") Long preferenceId) {
        if (!patientService.exists(id) || !foodService.exists(preferenceId)) {
            logger.error("Patient or food not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Patient p = patientService.getById(id);
        Food f = foodService.getById(preferenceId);

        p.removePreference(f);

        try {
            patientService.update(p);
        } catch (HumanException e) {
            logger.error("Cannot delete food from patient's food preferences");
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }

        return Response
                .noContent()
                .build();
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

}
