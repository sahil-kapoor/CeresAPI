package com.ceres.controller.v1.util;

import com.ceres.controller.RestController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

/**
 * Created by Leonardo on 20/01/2015.
 */
@Named("ResourceController")
@Singleton
@Path(RestController.V1)
@Api(value = "Imagens", basePath = "v1", description = "recursos em geral")
@Produces(MediaType.TEXT_HTML)
public final class ResourceController {

    protected static final String RESOURCE_URL = "recursos";

    private static final String RESOURCE_PATH = "/images/";

    private static final String RESOURCE_TYPE = ".png";

    private static final Logger logger = LoggerFactory.getLogger(ResourceController.class);

    @GET
    @Path(RESOURCE_URL + "/{logo}")
    @ApiOperation(value = "buscar logo", notes = "Esse serviço não produz um attachment, a imagem será renderizada na tela.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "o logo"),
            @ApiResponse(code = 400, message = "arquivo não encontrado")
    })
    public Response getLogo(@PathParam("logo") String requiredLogo) {

        File f = new File(getClass().getResource(RESOURCE_PATH + requiredLogo + RESOURCE_TYPE).getFile());

        if (!f.exists()) {
            logger.error("Resource not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(toByteArray(f)).build();
    }

    private byte[] toByteArray(File f) {
        byte[] imageData = new byte[0];
        try {
            BufferedImage image = ImageIO.read(f);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "png", outputStream);
            imageData = outputStream.toByteArray();
        } catch (IOException e) {
            logger.error("Cannot get bytearray of file");
            return imageData;
        }
        return imageData;
    }

}