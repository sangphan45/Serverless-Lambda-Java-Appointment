package com.serverless;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import connection.DynamoDBAdapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class appointmentdao {
    private final DynamoDBMapper mapper;

    public appointmentdao() {
        this.mapper = DynamoDBAdapter.getInstance()
                .createDbMapper(DynamoDBMapperConfig.builder().build());
    }

    public void insert(appointmentmodel appointment) {
        mapper.save(appointment);
    }

    public void update(appointmentmodel appointment, String id) {
        mapper.save(appointment, buildExpression(id));
    }

    private DynamoDBSaveExpression buildExpression(String id) {
        DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
        Map<String, ExpectedAttributeValue> expectedAttributeValueMap = new HashMap<>();
        expectedAttributeValueMap.put("id",
                new ExpectedAttributeValue(new AttributeValue().withS(id)));
        dynamoDBSaveExpression.setExpected(expectedAttributeValueMap);
        return dynamoDBSaveExpression;
    }

    public void delete(appointmentmodel appointment) {
        mapper.delete(appointment);
    }

    public List<appointmentmodel> findAll() {
        DynamoDBScanExpression scanExp = new DynamoDBScanExpression();
        List<appointmentmodel> results = this.mapper.scan(appointmentmodel.class, scanExp);
        return results;
    }

    public appointmentmodel findById(String id) {
        appointmentmodel appointmentmodel = null;
        HashMap<String, AttributeValue> av = new HashMap<>();
        av.put(":v1", new AttributeValue().withS(id));
        DynamoDBQueryExpression<appointmentmodel> queryExp = new DynamoDBQueryExpression<appointmentmodel>()
                .withKeyConditionExpression("id = :v1")
                .withExpressionAttributeValues(av);
        PaginatedQueryList<appointmentmodel> results = this.mapper.query(appointmentmodel.class, queryExp);
        if (results.size() > 0) {
            appointmentmodel = results.get(0);
        }
        return results.get(0);
    }
}
