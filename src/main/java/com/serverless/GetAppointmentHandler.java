package com.serverless;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.util.List;

public class GetAppointmentHandler implements RequestHandler<ApiGatewayRequest,ApiGatewayResponse> {
    private appointmentdao appointmentdao = new appointmentdao();
    @Override
    public ApiGatewayResponse handleRequest(ApiGatewayRequest input, Context context) {
        List<appointmentmodel> appointmentmodels = appointmentdao.findAll();

        return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(appointmentmodels).build();
    }
}
