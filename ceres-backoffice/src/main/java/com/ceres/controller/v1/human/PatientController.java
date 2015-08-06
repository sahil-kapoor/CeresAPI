package com.ceres.controller.v1.human;

import com.ceres.controller.RestController;
import com.ceres.domain.entity.human.Patient;
import com.ceres.service.human.PatientService;
import com.ceres.service.human.exception.HumanException;
import com.ceres.service.user.UserService;
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
import java.util.List;

/**
 * Created by Leonardo on 25/03/15.
 */
@Named("PatientController")
@Singleton
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Pacientes", basePath = "v1", description = "manipular pacientes", produces = MediaType.APPLICATION_JSON)
public final class PatientController {

    public static final String RESOURCE_URL = "paciente";

    private UserService userService;

    private PatientService patientService;

    private UriInfo uriInfo;

    private static final Logger logger = LoggerFactory.getLogger(PatientController.class);

    @Inject
    public PatientController(PatientService patientService, UserService userService) {
        this.userService = userService;
        this.patientService = patientService;
        logger.info("PatientController ready...");
    }

    @GET
    @Path(RESOURCE_URL)
    @ApiOperation(value = "recuperar todos os pacientes",
            responseContainer = "Array")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "pacientes do sistema", response = Patient.class)
    })
    public Response getAllPatients() {

        List<Patient> patients = patientService.getAll();

        return Response
                .ok(patients)
                .build();
    }

    @GET
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "recuperar paciente dado um ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "paciente do sistema", response = Patient.class),
            @ApiResponse(code = 404, message = "paciente não encontrado")

    })
    public Response getPatient(@PathParam("id") Long id) {
        if (!patientService.exists(id)) {
            logger.error("Patient " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        Patient patient = patientService.getById(id);

        return Response
                .ok(patient)
                .build();
    }

    @POST
    @Path(RESOURCE_URL)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "cadastrar paciente",
            consumes = MediaType.APPLICATION_JSON
    )
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "erro ao criar paciente"),
            @ApiResponse(code = 201, message = "paciente criado", response = Patient.class)
    })
    public Response createPatient(@ApiParam Patient patient) {

        Patient p = null;
        try {
            p = patientService.create(patient);
        } catch (HumanException e) {
            logger.error("Cannot create new patient", e.getMessage(), e.getStackTrace());
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }

        URI createdURI = URI.create(uriInfo.getRequestUri() + "/" + p.getId());
        return Response
                .created(createdURI)
                .entity(p)
                .build();
    }

    @PUT
    @Path(RESOURCE_URL + "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "atualizar paciente",
            consumes = MediaType.APPLICATION_JSON
    )
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "paciente não encontrado"),
            @ApiResponse(code = 500, message = "erro ao atualizar paciente"),
            @ApiResponse(code = 200, message = "paciente atualizado", response = Patient.class)
    })
    public Response updatePatient(@PathParam("id") Long id, @ApiParam Patient patient) {
        if (!patientService.exists(id)) {
            logger.error("Patient " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        try {
            patient.setId(id);
            patient = patientService.update(patient);
        } catch (HumanException e) {
            logger.error("Cannot update patient", e.getMessage(), e.getStackTrace());
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }

        return Response
                .ok()
                .entity(patient)
                .build();
    }

    @DELETE
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "remover paciente")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "paciente não encontrado"),
            @ApiResponse(code = 204, message = "paciente removido")
    })
    public Response deletePatient(@PathParam("id") Long id) {
        if (!patientService.exists(id)) {
            logger.error("Patient " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        patientService.remove(id);

        return Response
                .noContent()
                .build();
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
