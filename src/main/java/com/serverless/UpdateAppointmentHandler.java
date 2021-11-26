package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UpdateAppointmentHandler implements RequestHandler<ApiGatewayRequest,ApiGatewayResponse> {
    private appointmentdao appointmentdao = new appointmentdao();
    @Override
    public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            appointmentmodel appointment = mapper.readValue(input.getBody(), appointmentmodel.class);

            appointmentdao.update(appointment, appointment.getId());
            return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(appointment).build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ApiGatewayResponse.builder().setStatusCode(500).setObjectBody(false).build();
    }
}
