package com.caravan.caravan.DynamoDB;

import  android.util.Log;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.lang.reflect.Modifier;
import java.util.Map;
import static android.os.SystemClock.sleep;

@DynamoDBTable(tableName = "caravan-mobilehub-2012693532-Locations")
public class LocationsDO extends DynamoCRUD {


    public String location_results;
    public String _locationName;
    public String _locationCity;
    public String _address;
    public Map<String, String> _blueprintList;
    public String _description;
    public String _email;
    public String _foodDrinkRecommendation;
    public String _locationId;
    public Map<String, String> _neighborhoodList;
    public String _phoneNumber;
    public String _pricePoint;
    public String _timeOfDay;
    public String _website;

    public LocationsDO(String key, String range, String _address, String _description, String _email,
                       String _foodDrinkRecommendation, String _locationId, String _phoneNumber,
                       String _pricePoint, String _timeOfDay, String _website) {
        this.setLocationName(key);
        this.setLocationCity(range);
        this._address = _address;
        this._description = _description;
        this._email = _email;
        this._foodDrinkRecommendation = _foodDrinkRecommendation;
        this._locationId = _locationId;
        this._phoneNumber = _phoneNumber;
        this._pricePoint = _pricePoint;
        this._timeOfDay = _timeOfDay;
        this._website = _website;
    }

    public LocationsDO(String name, String city) {
        this.setLocationName(name);
        this.setLocationCity(city);
    }

    public LocationsDO() {
    }



    @DynamoDBHashKey(attributeName = "locationName")
    @DynamoDBAttribute(attributeName = "locationName")
    public String getLocationName() {
        return _locationName;
    }

    public void setLocationName(final String _locationName) {
        this._locationName = _locationName;
    }

    @DynamoDBRangeKey(attributeName = "locationCity")
    @DynamoDBAttribute(attributeName = "locationCity")
    public String getLocationCity() {
        return _locationCity;
    }

    public void setLocationCity(final String _locationCity) {
        this._locationCity = _locationCity;
    }

    @DynamoDBAttribute(attributeName = "address")
    public String getAddress() {
        return _address;
    }

    public void setAddress(final String _address) {
        this._address = _address;
    }

    @DynamoDBAttribute(attributeName = "blueprintList")
    public Map<String, String> getBlueprintList() {
        return _blueprintList;
    }

    public void setBlueprintList(final Map<String, String> _blueprintList) {
        this._blueprintList = _blueprintList;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return _description;
    }

    public void setDescription(final String _description) {
        this._description = _description;
    }

    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return _email;
    }

    public void setEmail(final String _email) {
        this._email = _email;
    }

    @DynamoDBAttribute(attributeName = "foodDrinkRecommendation")
    public String getFoodDrinkRecommendation() {
        return _foodDrinkRecommendation;
    }

    public void setFoodDrinkRecommendation(final String _foodDrinkRecommendation) {
        this._foodDrinkRecommendation = _foodDrinkRecommendation;
    }

    @DynamoDBAttribute(attributeName = "locationId")
    public String getLocationId() {
        return _locationId;
    }

    public void setLocationId(final String _locationId) {
        this._locationId = _locationId;
    }

    @DynamoDBAttribute(attributeName = "neighborhoodList")
    public Map<String, String> getNeighborhoodList() {
        return _neighborhoodList;
    }

    public void setNeighborhoodList(final Map<String, String> _neighborhoodList) {
        this._neighborhoodList = _neighborhoodList;
    }

    @DynamoDBAttribute(attributeName = "phoneNumber")
    public String getPhoneNumber() {
        return _phoneNumber;
    }

    public void setPhoneNumber(final String _phoneNumber) {
        this._phoneNumber = _phoneNumber;
    }

    @DynamoDBAttribute(attributeName = "pricePoint")
    public String getPricePoint() {
        return _pricePoint;
    }

    public void setPricePoint(final String _pricePoint) {
        this._pricePoint = _pricePoint;
    }

    @DynamoDBAttribute(attributeName = "timeOfDay")
    public String getTimeOfDay() {
        return _timeOfDay;
    }

    public void setTimeOfDay(final String _timeOfDay) {
        this._timeOfDay = _timeOfDay;
    }

    @DynamoDBAttribute(attributeName = "website")
    public String getWebsite() {
        return _website;
    }

    public void setWebsite(final String _website) {
        this._website = _website;
    }

    public void read(String name, String city) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                LocationsDO locationItem = dynamoDBMapper.load(
                        LocationsDO.class,
                        name,       // Partition key (hash key)
                        city);    // Sort key (range key)

                // Item read
                Log.d("Location Item:", locationItem.toString());
            }
        }).start();
    }
/*
    public void updateLocation(String locId, String name) {
        final LocationsDO locationItem = new LocationsDO();

        locationItem.setLocationId(locId);
        locationItem.setLocationName(name);

        //locationItem.setContent("This is the updated content.");

        new Thread(new Runnable() {
            @Override
            public void run() {

                dynamoDBMapper.save(locationItem);

                // Item updated
            }
        }).start();
    }

    public void deleteLocation(String locId, String name) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                LocationsDO locationItem = new LocationsDO();

                locationItem.setLocationId(locId);    //partition key
                locationItem.setLocationName(name);  //range (sort) key

                dynamoDBMapper.delete(locationItem);

                // Item deleted
            }
        }).start();
    }*/

    public String queryLocations(DynamoDBMapper dynamoDBMapper, String name) {
        Log.d("query", name);
        new Thread(new Runnable() {
            @Override
            public void run() {
                LocationsDO obj = new LocationsDO();
                obj.setLocationName(name);

                com.amazonaws.services.dynamodbv2.model.Condition rangeKeyCondition = new com.amazonaws.services.dynamodbv2.model.Condition()
                        .withComparisonOperator(ComparisonOperator.BEGINS_WITH)
                        .withAttributeValueList(new AttributeValue().withS("Nashville"));

                DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                        .withHashKeyValues(obj)
                        .withRangeKeyCondition("locationCity", (com.amazonaws.services.dynamodbv2.model.Condition) rangeKeyCondition)
                        .withConsistentRead(false);

                PaginatedList<LocationsDO> result = dynamoDBMapper.query(LocationsDO.class, queryExpression);
                if (result.isEmpty()) {
                    Log.d("result", "is empty");
                } else {
                    Log.d("result", "not empty");
                }
                GsonBuilder builder = new GsonBuilder();
                builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
                Gson gson = builder.create();
                String jsonFormOfItem = gson.toJson(result);
                location_results = "{\"location\":" + jsonFormOfItem + "}";
                Log.d("Query result: ",location_results);

                if (result.isEmpty()) {
                    Log.d("Query result: ", "No Results!\n");
                    // There were no items matching your query.
                }
            }

        }).start();
        sleep(1000);
        Log.d("Query result: ",location_results);
        return location_results;
    }
}
