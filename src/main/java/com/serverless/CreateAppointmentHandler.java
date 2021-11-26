package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreateAppointmentHandler implements RequestHandler<ApiGatewayRequest,ApiGatewayResponse> {
    private appointmentdao appointmentdao = new appointmentdao();
    @Override
    public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            appointmentmodel appointment = mapper.readValue((String) input.getBody(), appointmentmodel.class);
            appointmentdao.insert(appointment);
            return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(appointment).build();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ApiGatewayResponse.builder().setStatusCode(500).setObjectBody(input).build();
    }
}
