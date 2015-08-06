package com.ceres.controller.v1.chart;

import com.ceres.business.chart.ChartEntry;
import com.ceres.controller.RestController;
import com.ceres.controller.v1.human.PatientController;
import com.ceres.domain.entity.human.Patient;
import com.ceres.service.human.FeatureService;
import com.ceres.service.human.PatientService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.springframework.stereotype.Controller;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by leonardo on 03/05/15.
 */
@Named("PatientChartController")
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Gráficos do Paciente", basePath = "v1")
public class PatientChartController {

    private static final String RESOURCE_URL = "grafico";

    private PatientService patientService;

    private FeatureService featureService;

    @Inject
    public PatientChartController(PatientService patientService, FeatureService featureService) {
        this.patientService = patientService;
        this.featureService = featureService;
    }

    @GET
    @Path(PatientController.RESOURCE_URL + "/{id}/" + RESOURCE_URL + "/parametros")
    @ApiOperation(value = "recuperar o JSON para gráfico de parâmetros", notes = "Este gráfico representa a evolução por " +
            "DATA dos parâmetros básicos como PESO, ALTURA, CINTURA, TAXA DE ATIVIDADE, entre outros")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "dados para montar o gráfico", response = ChartEntry.class),
            @ApiResponse(code = 404, message = "paciente não encontrado")
    })
    public Response getPatientParametersChart(@PathParam("id") Long id) {
        if (!patientService.exists(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Patient p = patientService.getByIdWithCalculatedFeatures(id);

        List<ChartEntry> chartData = featureService.getUsualChartData(p);

        return Response.ok().entity(chartData).build();


    }

    @GET
    @Path(PatientController.RESOURCE_URL + "/{id}/" + RESOURCE_URL + "/calculos")
    @ApiOperation(value = "recuperar o JSON para gráfico de cálculos", notes = "Este gráfico representa a evolução por " +
            "DATA dos cálculos como IMC, METABOLISMO BASAL, GORDURA TOTAL, entre outros")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "dados para montar o gráfico", response = ChartEntry.class),
            @ApiResponse(code = 404, message = "paciente não encontrado")
    })
    public Response getPatientCalculedParametersChart(@PathParam("id") Long id) {
        if (!patientService.exists(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Patient p = patientService.getByIdWithCalculatedFeatures(id);

        List<ChartEntry> chartData = featureService.getCalcChartData(p);

        return Response.ok().entity(chartData).build();


    }

    @GET
    @Path(PatientController.RESOURCE_URL + "/{id}/" + RESOURCE_URL + "/completo")
    @ApiOperation(value = "recuperar o JSON para gráfico completo", notes = "Este gráfico representa a evolução por " +
            "DATA contendo todos os dados do paciente.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "dados para montar o gráfico", response = ChartEntry.class),
            @ApiResponse(code = 404, message = "paciente não encontrado")
    })
    public Response getPatientFullChart(@PathParam("id") Long id) {
        if (!patientService.exists(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Patient p = patientService.getByIdWithCalculatedFeatures(id);

        List<ChartEntry> chartData = featureService.getFullChartData(p);

        return Response.ok().entity(chartData).build();


    }

}
