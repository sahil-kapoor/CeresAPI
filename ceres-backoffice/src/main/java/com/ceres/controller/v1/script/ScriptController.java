package com.ceres.controller.v1.script;

import com.ceres.controller.RestController;
import com.ceres.domain.entity.script.Script;
import com.ceres.service.script.ScriptService;
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
import java.util.Set;

/**
 * Created by Leonardo on 25/03/15.
 */
@Named("ScriptController")
@Singleton
@Path(RestController.V1)
@Produces(MediaType.APPLICATION_JSON)
@Api(value = "Scripts de Cálculo", basePath = "v1", description = "manipular scripts de cálculo")
public final class ScriptController {

    private static final String RESOURCE_URL = "script";

    private ScriptService scriptService;

    private UriInfo uriInfo;

    private static final Logger logger = LoggerFactory.getLogger(ScriptController.class);

    @Inject
    public ScriptController(ScriptService scriptService) {
        this.scriptService = scriptService;
        logger.info("ScriptController ready...");
    }

    @GET
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "recuperar um script de cálculo dado seu ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "o script", response = Script.class),
            @ApiResponse(code = 404, message = "script não encontrado")
    })
    public Response getScript(@PathParam("id") Long id) {
        Script script = scriptService.getScriptById(id);
        return Response.ok(script).build();
    }

    @GET
    @Path(RESOURCE_URL)
    @ApiOperation(value = "recuperar todos os scripts de cálculo", responseContainer = "Array")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "os scripts", response = Script.class),
    })
    public Response getScripts() {
        Set<Script> script = scriptService.getAllScripts();
        return Response.ok(script).build();
    }

    @PUT
    @Path(RESOURCE_URL + "/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "atualizar um script de cálculo")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "script atualizado", response = Script.class),
            @ApiResponse(code = 404, message = "script não encontrado"),
            @ApiResponse(code = 500, message = "erro ao atualizar o script")
    })
    public Response updateScript(@PathParam("id") Long id, @ApiParam Script script) {
        if (!scriptService.exists(id)) {
            logger.error("Script " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        scriptService.createOrUpdate(script);

        return Response.ok().build();
    }

    @POST
    @Path(RESOURCE_URL)
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "criar um script de cálculo")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "script criado", response = Script.class),
            @ApiResponse(code = 500, message = "erro ao criar o script")
    })
    public Response createScript(@ApiParam Script script) {
        Script s = scriptService.createOrUpdate(script);

        URI createdURI = URI.create(uriInfo.getRequestUri() + "/" + s.getId());
        return Response.created(createdURI).entity(s).build();
    }

    @DELETE
    @Path(RESOURCE_URL + "/{id}")
    @ApiOperation(value = "deletar um script de cálculo", notes = "Caso o script possua dependências as mesmas também serão removidas")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "script removido"),
            @ApiResponse(code = 500, message = "erro ao remover o script")
    })
    public Response deleteScript(@PathParam("id") Long id) {
        if (!scriptService.exists(id)) {
            logger.error("Script " + id + " not found");
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        }

        scriptService.remove(id);

        return Response.noContent().build();

    }

    @Context
    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }
}


