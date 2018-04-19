package com.caravan.caravan.DynamoDB;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;

@DynamoDBTable(tableName = "caravan-mobilehub-2012693532-Curated")

public class CuratedDO {
    private String _type;
    private String _name;
    private String _address;
    private List<String> _blueprintList;
    private String _city;
    private String _description;
    private String _foodDrinkRecommendation;
    private String _id;
    private List<String> _locationList;
    private List<String> _neighborhoodList;
    private String _pricePoint;
    private String _timeOfDay;
    private String _website;
    private String _phoneNumber;

    @DynamoDBHashKey(attributeName = "type")
    @DynamoDBAttribute(attributeName = "type")
    public String getType() {
        return _type;
    }

    public void setType(final String _type) {
        this._type = _type;
    }
    @DynamoDBRangeKey(attributeName = "name")
    @DynamoDBAttribute(attributeName = "name")
    public String getName() {
        return _name;
    }

    public void setName(final String _name) {
        this._name = _name;
    }
    @DynamoDBAttribute(attributeName = "address")
    public String getAddress() {
        return _address;
    }

    public void setAddress(final String _address) {
        this._address = _address;
    }
    @DynamoDBAttribute(attributeName = "blueprintList")
    public List<String> getBlueprintList() {
        return _blueprintList;
    }

    public void setBlueprintList(final List<String> _blueprintList) {
        this._blueprintList = _blueprintList;
    }
    @DynamoDBAttribute(attributeName = "city")
    public String getCity() {
        return _city;
    }

    public void setCity(final String _city) {
        this._city = _city;
    }
    @DynamoDBAttribute(attributeName = "description")
    public String getDescription() {
        return _description;
    }

    public void setDescription(final String _description) {
        this._description = _description;
    }
    @DynamoDBAttribute(attributeName = "foodDrinkRecommendation")
    public String getFoodDrinkRecommendation() {
        return _foodDrinkRecommendation;
    }

    public void setFoodDrinkRecommendation(final String _foodDrinkRecommendation) {
        this._foodDrinkRecommendation = _foodDrinkRecommendation;
    }
    @DynamoDBAttribute(attributeName = "id")
    public String getId() {
        return _id;
    }

    public void setId(final String _id) {
        this._id = _id;
    }
    @DynamoDBAttribute(attributeName = "locationList")
    public List<String> getLocationList() {
        return _locationList;
    }

    public void setLocationList(final List<String> _locationList) {
        this._locationList = _locationList;
    }
    @DynamoDBAttribute(attributeName = "neighborhoodList")
    public List<String> getNeighborhoodList() {
        return _neighborhoodList;
    }

    public void setNeighborhoodList(final List<String> _neighborhoodList) {
        this._neighborhoodList = _neighborhoodList;
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
    @DynamoDBAttribute(attributeName = "phoneNumber")
    public String getPhoneNumber() {
        return _phoneNumber;
    }

    public void setPhoneNumber(final String _phoneNumber) {
        this._phoneNumber = _phoneNumber;
    }

}

