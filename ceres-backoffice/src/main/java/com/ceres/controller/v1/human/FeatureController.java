package com.ceres.controller.v1.human;

import com.ceres.controller.RestController;
import com.ceres.domain.entity.human.Feature;
import com.ceres.domain.entity.human.Patient;
import com.ceres.service.human.FeatureService;
import com.ceres.service.human.PatientService;
import com.ceres.service.human.exception.FeatureException;
import com.ceres.service.human.exception.HumanException;
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
 * Created by leonardo on 30/03/15.
 */
@Named("FeatureController")
@Singleton
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Consultas", basePath = "v1", description = "manipular consultas", produces = MediaType.APPLICATION_JSON)
public final class FeatureController {

    protected static final String RESOURCE_URL = "consulta";

    private PatientService patientService;

    private UriInfo uriInfo;

    private FeatureService featureService;

    private static final Logger logger = LoggerFactory.getLogger(FeatureController.class);

    @Inject
    public FeatureController(PatientService patientService, FeatureService featureService) {
        this.patientService = patientService;
        this.featureService = featureService;
        logger.info("FeatureController ready...");
    }

    @GET
    @Path(PatientController.RESOURCE_URL + "/" + RESOURCE_URL + "/{id}")
    @ApiOperation(value = "recuperar a consulta de um paciente dado o ID da consulta", notes = "Executa todos os cálculos e os retorna junto com as medidas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "a consulta", response = Feature.class),
            @ApiResponse(code = 404, message = "consulta não encontrada")
    })
    public Response getPatientsFeature(@PathParam("id") Long featureId) {
        if (!featureService.exists(featureId)) {
            logger.error("Feature " + featureId + "not found.");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Feature feature = featureService.getFeatureById(featureId);

        return Response.ok(feature).build();

    }

    @GET
    @Path(PatientController.RESOURCE_URL + "/{id}/" + RESOURCE_URL)
    @ApiOperation(value = "recuperar a consulta de um paciente dado o ID do paciente",
            responseContainer = "Array")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "as consultas do paciente", response = Feature.class),
            @ApiResponse(code = 404, message = "paciente não encontrado"),
            @ApiResponse(code = 500, message = "não foi possível calcular as medidas")
    })
    public Response getAllPatientsFeature(@PathParam("id") Long patientId) {
        if (!patientService.exists(patientId)) {
            logger.error("Patient " + patientId + "not found.");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Patient p = patientService.getById(patientId);
        featureService.calculateFeatures(p);

        return Response.ok(p.getFeatures()).build();
    }

    @PUT
    @Path(PatientController.RESOURCE_URL + "/" + RESOURCE_URL + "/{id}")
    @ApiOperation(value = "atualizar um consulta dado o ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "a consulta atualizada", response = Feature.class),
            @ApiResponse(code = 404, message = "consulta não encontrada"),
            @ApiResponse(code = 500, message = "não foi possível atualizar a consulta")
    })
    public Response updateFeature(@PathParam("id") Long featureId, @ApiParam Feature feature) {
        if (!featureService.exists(featureId)) {
            logger.error("Feature " + featureId + "not found.");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        feature.setId(featureId);

        try {
            feature = featureService.createOrUpdate(feature);
        } catch (FeatureException e) {
            logger.error("Cannot update feature " + featureId, e.getMessage(), e.getStackTrace());
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }

        return Response.ok().entity(feature).build();
    }

    @POST
    @Path(PatientController.RESOURCE_URL + "/{id}/" + RESOURCE_URL)
    @ApiOperation(value = "registrar uma consulta para o paciente informado")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "a consulta foi registrada", response = Feature.class),
            @ApiResponse(code = 404, message = "paciente não encontrado"),
            @ApiResponse(code = 500, message = "não foi possível registrar a consulta")
    })
    public Response createPatientFeature(@PathParam("id") Long patientId, Feature feature) {
        if (!patientService.exists(patientId)) {
            logger.error("Patient " + patientId + "not found.");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Patient p = patientService.getById(patientId);
        try {
            feature = patientService.registryFeature(p, feature);
        } catch (HumanException | FeatureException e) {
            logger.error("Cannot create feature for patient: " + patientId, e.getMessage(), e.getStackTrace());
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }

        URI createdURI = URI.create(uriInfo.getRequestUri() + "/" + feature.getId());
        return Response.created(createdURI).entity(feature).build();
    }

    @DELETE
    @Path(PatientController.RESOURCE_URL + "/{patientId}/" + RESOURCE_URL + "/{featureId}")
    @ApiOperation(value = "remover consulta")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "consulta não encontrada || paciente não encontrado"),
            @ApiResponse(code = 204, message = "consulta removida")
    })
    public Response deleteFeature(@PathParam("pid") Long patientId, @PathParam("id") Long featureId) {
        if (!featureService.exists(featureId) || !patientService.exists(patientId)) {
            logger.error("Feature or Patient not found.");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Patient p = patientService.getById(patientId);
        Feature f = featureService.getFeatureById(featureId);
        patientService.removeFeatureFromPatient(p, f);

        return Response.noContent().build();
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}