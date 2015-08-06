package com.ceres.controller.v1.util;

import com.ceres.controller.RestController;
import com.ceres.controller.v1.human.NutritionistController;
import com.ceres.controller.v1.human.PatientController;
import com.ceres.service.human.NutritionistService;
import com.ceres.service.human.PatientService;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;

/**
 * Created by leonardo on 19/04/15.
 */
@Named("ExportController")
@Singleton
@Path(RestController.V1)
@Api(value = "Exportaçao", basePath = "v1", description = "exportar recursos", produces = MediaType.MULTIPART_FORM_DATA)
public final class ExportController {

    protected static final String RESOURCE_URL = "exportar";

    private UriInfo uriInfo;

    private PatientService patientService;

    private NutritionistService nutritionistService;

    private static final Logger logger = LoggerFactory.getLogger(ExportController.class);

    @Inject
    public ExportController(PatientService patientService, NutritionistService nutritionistService) {
        this.patientService = patientService;
        this.nutritionistService = nutritionistService;
        logger.info("ExportController ready...");
    }

    @GET
    @Path(PatientController.RESOURCE_URL + "/{id}/" + RESOURCE_URL)
    @ApiOperation(value = "exportar o paciente referente ao ID informado", notes = "Esse serviço gera o .xml e retorna como ATTACHMENT")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "paciente não encontrado"),
            @ApiResponse(code = 200, message = "xml do paciente"),
            @ApiResponse(code = 500, message = "erro durante exportação")

    })
    public Response exportPatient(@PathParam("id") Long id) {
        if (!patientService.exists(id)) {
            logger.info("Patient " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        File file = patientService.exportToXmlFile(id);

        return Response
                .ok(file)
                .header("Content-Disposition", "attachment; filename=" + file.getName())
                .build();
    }

    @GET
    @Path(NutritionistController.RESOURCE_URL + "/{id}/" + RESOURCE_URL)
    @ApiOperation(value = "exportar o nutricionista referente ao ID informado", notes = "Esse serviço gera o .xml e retorna como ATTACHMENT")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "nutricionista não encontrado"),
            @ApiResponse(code = 200, message = "xml do nutricionista"),
            @ApiResponse(code = 500, message = "erro durante exportação")
    })
    public Response exportNutritionist(@PathParam("id") Long id) {
        if (!nutritionistService.exists(id)) {
            logger.info("Nutritionist " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        File file = nutritionistService.exportToXmlFile(id);

        return Response
                .ok(file)
                .header("Content-Disposition", "attachment; filename=" + file.getName())
                .build();
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
