package com.ceres.controller.v1.util;

import com.ceres.controller.RestController;
import com.ceres.controller.v1.human.NutritionistController;
import com.ceres.controller.v1.human.PatientController;
import com.ceres.domain.entity.human.Nutritionist;
import com.ceres.domain.entity.human.Patient;
import com.ceres.service.human.NutritionistService;
import com.ceres.service.human.PatientService;
import com.wordnik.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.net.URI;

/**
 * Created by leonardo on 19/04/15.
 */
@Named("ImportController")
@Singleton
@Path(RestController.V1)
@Api(value = "Importação", basePath = "v1", description = "importar recursos", consumes = MediaType.MULTIPART_FORM_DATA, produces = MediaType.APPLICATION_JSON)
public final class ImportController {

    protected static final String RESOURCE_URL = "importar";

    private UriInfo uriInfo;

    private PatientService patientService;

    private NutritionistService nutritionistService;

    private static final Logger logger = LoggerFactory.getLogger(ImportController.class);

    @Inject
    public ImportController(PatientService patientService, NutritionistService nutritionistService) {
        this.patientService = patientService;
        this.nutritionistService = nutritionistService;
        logger.info("ImportController ready....");
    }

    @POST
    @Path(PatientController.RESOURCE_URL + "/" + RESOURCE_URL)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(value = "importar o paciente", notes = "Esse serviço deve receber um arquivo no formado .xml")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "paciente importado"),
            @ApiResponse(code = 400, message = "arquivo inválido")
    })
    public Response importPatient(@ApiParam File file) {
        if (file == null) {
            logger.error("Invalid file type....");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Patient p = patientService.importFromXml(file);

        URI createdURI = URI.create(uriInfo.getBaseUri().toString().concat(RestController.V1) +
                PatientController.RESOURCE_URL + "-" + p.getId());
        return Response
                .created(createdURI)
                .entity(p)
                .build();
    }

    @POST
    @Path(NutritionistController.RESOURCE_URL + "/" + RESOURCE_URL)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(value = "importar o nutricionista", notes = "Esse serviço deve receber um arquivo no formado .xml")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "nutricionista importado"),
            @ApiResponse(code = 400, message = "arquivo inválido")
    })
    public Response importNutritionist(@ApiParam File file) {
        if (file == null) {
            logger.error("Invalid file type....");
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        Nutritionist n = nutritionistService.importFromXml(file);

        URI createdURI = URI.create(uriInfo.getBaseUri().toString().concat(RestController.V1) +
                NutritionistController.RESOURCE_URL + "-" + n.getId());
        return Response
                .created(createdURI)
                .entity(n)
                .build();
    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}
