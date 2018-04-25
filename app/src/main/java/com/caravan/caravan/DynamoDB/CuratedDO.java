package com.caravan.caravan.DynamoDB;

import android.arch.persistence.room.Entity;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;

@DynamoDBTable(tableName = "caravan-mobilehub-2012693532-Curated")
@Entity
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
    private int _followerCount;
    private List<String> _imageList;

    public CuratedDO(){
        this._blueprintList = new ArrayList<>();
        this._locationList = new ArrayList<>();
        this._neighborhoodList = new ArrayList<>();
        //this._imageList = new ArrayList<>();
    }

    public CuratedDO(String _type, String _name, String _address, List<String> _blueprintList, String _city, String _description, String _foodDrinkRecommendation,
                     String _id, List<String> _locationList, List<String> _neighborhoodList, String _pricePoint, String _timeOfDay, String _website, String _phoneNumber, int _followerCount, List<String> _imageList) {
        this._type=_type;
        this._name=_name;
        this._address=_address;
        this._blueprintList=_blueprintList;
        this._city=_city;
        this._description=_description;
        this._foodDrinkRecommendation=_foodDrinkRecommendation;
        this._id=_id;
        this._locationList=_locationList;
        this._neighborhoodList=_neighborhoodList;
        this._pricePoint=_pricePoint;
        this._timeOfDay=_timeOfDay;
        this._website=_website;
        this._phoneNumber=_phoneNumber;
        this._followerCount = _followerCount;
        this._imageList = _imageList;
    }

    @DynamoDBHashKey(attributeName = "type")
    @DynamoDBIndexHashKey(attributeName = "type", globalSecondaryIndexName = "HtypeRcity")
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
    @DynamoDBIndexRangeKey(attributeName = "city", globalSecondaryIndexName = "HtypeRcity")
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
    @DynamoDBAttribute(attributeName = "followerCount")
    public int getFollowerCount() { return _followerCount; }

    public void setFollowerCount(final int _followerCount) {
        this._followerCount = _followerCount;
    }
    @DynamoDBAttribute(attributeName = "imgList")
    public List<String> getImageList() { return _imageList; }

    public void setImageList(List<String> _imageList) { this._imageList = _imageList; }

    @Override
    public boolean equals(final Object obj)
    {
        if ( obj == null || obj == this || !(obj instanceof CuratedDO) )
            return false;

        CuratedDO otherCuratedDO = (CuratedDO) obj;

        if (!otherCuratedDO._type.equals(this._type)) return false;
        if (!otherCuratedDO._name.equals(this._name))   return false;
        if (!otherCuratedDO._address.equals(this._address)) return false;

        if(this._blueprintList != null) {
            if (!otherCuratedDO._blueprintList.equals(this._blueprintList)) return false;
        }
        else {
            if (otherCuratedDO._blueprintList != null) return false;
        }

        if (!otherCuratedDO._city.equals(this._city))   return false;
        if (!otherCuratedDO._description.equals(this._description)) return false;
        if (!otherCuratedDO._foodDrinkRecommendation.equals(this._foodDrinkRecommendation)) return false;
        if (!otherCuratedDO._id.equals(this._id))   return false;

        if(this._locationList != null) {
            if (!otherCuratedDO._locationList.equals(this._locationList)) return false;
        }
        else {
            if(otherCuratedDO._locationList != null) return false;
        }

        if(this._neighborhoodList != null) {
            if (!otherCuratedDO._neighborhoodList.equals(this._neighborhoodList)) return false;
        }
            else {
            if(otherCuratedDO._neighborhoodList != null) return false;
        }
        if (!otherCuratedDO._pricePoint.equals(this._pricePoint))   return false;
        if (!otherCuratedDO._timeOfDay.equals(this._timeOfDay)) return false;
        if (!otherCuratedDO._website.equals(this._website)) return false;
        if (!otherCuratedDO._phoneNumber.equals(this._phoneNumber))   return false;
        if (otherCuratedDO._followerCount != this._followerCount) return false;

        return true;
    }
}

