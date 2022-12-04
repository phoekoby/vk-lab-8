package org.example.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.ext.Providers;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonMessageBodyWriter<T> implements MessageBodyWriter<T> {
    private static final String PRETTY_PRINT_PARAMETER_NAME = "pretty";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ObjectMapper prettyObjectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    @Context
    private UriInfo ui;
    @Context
    private Providers providers;

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return true;
    }

    @Override
    public void writeTo(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        try (final PrintWriter printWriter =
                     new PrintWriter(new OutputStreamWriter(entityStream, StandardCharsets.UTF_8), true)) {
            final String json = ui.getQueryParameters().containsKey(PRETTY_PRINT_PARAMETER_NAME)
                    ? prettyObjectMapper.writeValueAsString(t)
                    : objectMapper.writeValueAsString(t);
            printWriter.write(json);
        } catch (JsonProcessingException e) {
            throw new WebApplicationException(e);
        }
    }
}
