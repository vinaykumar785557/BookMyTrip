package com.example.myapplication.models;

public class RecommendedPlacesModel
{
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    String placeName;
    int imageId;
    public RecommendedPlacesModel(String placeName, int imageId)
    {
        this.placeName=placeName;
        this.imageId = imageId;
    }
}
