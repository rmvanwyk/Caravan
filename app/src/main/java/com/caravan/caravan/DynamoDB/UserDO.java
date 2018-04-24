package com.caravan.caravan.DynamoDB;

import android.arch.persistence.room.Entity;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;

import java.util.List;

@DynamoDBTable(tableName = "caravan-mobilehub-2012693532-User")
@Entity
public class UserDO {
    private String _userId;
    private String _name;
    private String _address;
    private String _city;
    private String _description;
    private String _foodDrinkRecommendation;
    private String _id;
    private List<String> _locationList;
    private String _phoneNumber;
    private String _pricePoint;
    private String _timeOfDay;
    private String _type;
    private String _website;
    private int _followerCount;
    private List<String> _imageList;

    public UserDO(){}

    public UserDO(String _userId, String _name, String _address, String _city, String _description, String _foodDrinkRecommendation, String _id,
                  List<String> _locationList, String _phoneNumber, String _pricePoint, String _timeOfDay, String _type, String _website, int _followerCount, List<String> _imageList) {
        this._userId=_userId;
        this._name=_name;
        this._address=_address;
        this._city=_city;
        this._description=_description;
        this._foodDrinkRecommendation=_foodDrinkRecommendation;
        this._id=_id;
        this._locationList=_locationList;
        this._phoneNumber=_phoneNumber;
        this._pricePoint=_pricePoint;
        this._timeOfDay=_timeOfDay;
        this._type=_type;
        this._website=_website;
        this._followerCount=_followerCount;
        this._imageList=_imageList;
    }

    @DynamoDBHashKey(attributeName = "userId")
    @DynamoDBIndexHashKey(attributeName = "userId", globalSecondaryIndexName = "uIDplusType")
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return _userId;
    }

    public void setUserId(final String _userId) {
        this._userId = _userId;
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
    @DynamoDBAttribute(attributeName = "type")
    @DynamoDBIndexRangeKey(attributeName = "type", globalSecondaryIndexName = "uIDplusType")
    public String getType() {
        return _type;
    }

    public void setType(final String _type) {
        this._type = _type;
    }
    @DynamoDBAttribute(attributeName = "website")
    public String getWebsite() {
        return _website;
    }

    public void setWebsite(final String _website) {
        this._website = _website;
    }
    @DynamoDBAttribute(attributeName = "followerCount")
    public int getFollowerCount() { return _followerCount; }

    public void setFollowerCount(final int _followerCount) {
        this._followerCount = _followerCount;
    }
    @DynamoDBAttribute(attributeName = "imageList")
    public List<String> getImageList() { return _imageList; }

    public void setImageList(List<String> _imageList) { this._imageList = _imageList; }

}
