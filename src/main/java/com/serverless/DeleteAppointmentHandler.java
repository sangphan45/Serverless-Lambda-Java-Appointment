package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class DeleteAppointmentHandler implements RequestHandler<ApiGatewayRequest,ApiGatewayResponse> {
    private appointmentdao appointmentdao = new appointmentdao();
    @Override
    public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map<String, String> pathParameters = input.getPathParameters();
            String id = pathParameters.get("id");
            appointmentmodel appointment = appointmentdao.findById(id);
            appointmentdao.delete(appointment);
            return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(true).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiGatewayResponse.builder().setStatusCode(500).setObjectBody(false).build();
    }
}
