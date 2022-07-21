package com.example.pristineseed.model.PlantingModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class PlantingLineDocNoDetails {
    @SerializedName("Condition")
    @Expose
    private String condition;
    @SerializedName("Sell-to Customer No.")
    @Expose
    private String sellToCustomerNo;
    @SerializedName("No")
    @Expose
    private String no;
    @SerializedName("Order Date")
    @Expose
    private String orderDate;
    @SerializedName("Posting Date")
    @Expose
    private String postingDate;
    @SerializedName("Location Code")
    @Expose
    private String locationCode;
    @SerializedName("Customer Posting Group")
    @Expose
    private String customerPostingGroup;
    @SerializedName("Order No.")
    @Expose
    private String orderNo;
    @SerializedName("Sell-to Customer Name")
    @Expose
    private String sellToCustomerName;
    @SerializedName("Sell-to Address")
    @Expose
    private String sellToAddress;
    @SerializedName("Sell-to City")
    @Expose
    private String sellToCity;
    @SerializedName("User ID")
    @Expose
    private String userID;
    @SerializedName("Document SubType")
    @Expose
    private String documentSubType;
    @SerializedName("Child Seed")
    @Expose
    private String childSeed;
    @SerializedName("Crop Code")
    @Expose
    private String cropCode;
    @SerializedName("Bussiness Type")
    @Expose
    private String bussinessType;
    @SerializedName("Zone Code")
    @Expose
    private String zoneCode;
    @SerializedName("Region Code")
    @Expose
    private String regionCode;
    @SerializedName("Child Seed Type")
    @Expose
    private String childSeedType;
    @SerializedName("Location Name")
    @Expose
    private String locationName;
    @SerializedName("Zone Name")
    @Expose
    private String zoneName;
    @SerializedName("State Name")
    @Expose
    private String stateName;
    @SerializedName("Territory Name")
    @Expose
    private String territoryName;
    @SerializedName("Region Name")
    @Expose
    private String regionName;
    @SerializedName("District Name")
    @Expose
    private String districtName;
    @SerializedName("Land in Acres")
    @Expose
    private String landInAcres;
    @SerializedName("Child Seed Name")
    @Expose
    private String childSeedName;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Lines")
    @Expose
    private List<Line> lines = null;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getSellToCustomerNo() {
        return sellToCustomerNo;
    }

    public void setSellToCustomerNo(String sellToCustomerNo) {
        this.sellToCustomerNo = sellToCustomerNo;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getCustomerPostingGroup() {
        return customerPostingGroup;
    }

    public void setCustomerPostingGroup(String customerPostingGroup) {
        this.customerPostingGroup = customerPostingGroup;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSellToCustomerName() {
        return sellToCustomerName;
    }

    public void setSellToCustomerName(String sellToCustomerName) {
        this.sellToCustomerName = sellToCustomerName;
    }

    public String getSellToAddress() {
        return sellToAddress;
    }

    public void setSellToAddress(String sellToAddress) {
        this.sellToAddress = sellToAddress;
    }

    public String getSellToCity() {
        return sellToCity;
    }

    public void setSellToCity(String sellToCity) {
        this.sellToCity = sellToCity;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDocumentSubType() {
        return documentSubType;
    }

    public void setDocumentSubType(String documentSubType) {
        this.documentSubType = documentSubType;
    }

    public String getChildSeed() {
        return childSeed;
    }

    public void setChildSeed(String childSeed) {
        this.childSeed = childSeed;
    }

    public String getCropCode() {
        return cropCode;
    }

    public void setCropCode(String cropCode) {
        this.cropCode = cropCode;
    }

    public String getBussinessType() {
        return bussinessType;
    }

    public void setBussinessType(String bussinessType) {
        this.bussinessType = bussinessType;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getChildSeedType() {
        return childSeedType;
    }

    public void setChildSeedType(String childSeedType) {
        this.childSeedType = childSeedType;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getTerritoryName() {
        return territoryName;
    }

    public void setTerritoryName(String territoryName) {
        this.territoryName = territoryName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getLandInAcres() {
        return landInAcres;
    }

    public void setLandInAcres(String landInAcres) {
        this.landInAcres = landInAcres;
    }

    public String getChildSeedName() {
        return childSeedName;
    }

    public void setChildSeedName(String childSeedName) {
        this.childSeedName = childSeedName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }
    public class Line {
        @SerializedName("Condition")
        @Expose
        private String condition;
        @SerializedName("Sell-to Customer No.")
        @Expose
        private String sellToCustomerNo;
        @SerializedName("Document No")
        @Expose
        private String documentNo;
        @SerializedName("Line No.")
        @Expose
        private String lineNo;
        @SerializedName("Type")
        @Expose
        private String type;
        @SerializedName("No.")
        @Expose
        private String no;
        @SerializedName("Location Code")
        @Expose
        private String locationCode;
        @SerializedName("Description")
        @Expose
        private String description;
        @SerializedName("Unit of Measure")
        @Expose
        private String unitOfMeasure;
        @SerializedName("Quantity")
        @Expose
        private String quantity;
        @SerializedName("Order No.")
        @Expose
        private String orderNo;
        @SerializedName("Variant Code")
        @Expose
        private String variantCode;
        @SerializedName("Bin Code")
        @Expose
        private String binCode;
        @SerializedName("Unit of Measure Code")
        @Expose
        private String unitOfMeasureCode;
        @SerializedName("Item Class of Seeds")
        @Expose
        private String itemClassOfSeeds;
        @SerializedName("Location Name")
        @Expose
        private String locationName;
        @SerializedName("Lot No.")
        @Expose
        private String lotNo;
        @SerializedName("Receipt No.")
        @Expose
        private String receiptNo;
        @SerializedName("Land in Acre")
        @Expose
        private String landInAcre;
        @SerializedName("No. of Bags")
        @Expose
        private String noOfBags;
        @SerializedName("Message")
        @Expose
        private String message;

        public String getCondition() {
            return condition;
        }

        public void setCondition(String condition) {
            this.condition = condition;
        }

        public String getSellToCustomerNo() {
            return sellToCustomerNo;
        }

        public void setSellToCustomerNo(String sellToCustomerNo) {
            this.sellToCustomerNo = sellToCustomerNo;
        }

        public String getDocumentNo() {
            return documentNo;
        }

        public void setDocumentNo(String documentNo) {
            this.documentNo = documentNo;
        }

        public String getLineNo() {
            return lineNo;
        }

        public void setLineNo(String lineNo) {
            this.lineNo = lineNo;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getLocationCode() {
            return locationCode;
        }

        public void setLocationCode(String locationCode) {
            this.locationCode = locationCode;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUnitOfMeasure() {
            return unitOfMeasure;
        }

        public void setUnitOfMeasure(String unitOfMeasure) {
            this.unitOfMeasure = unitOfMeasure;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getVariantCode() {
            return variantCode;
        }

        public void setVariantCode(String variantCode) {
            this.variantCode = variantCode;
        }

        public String getBinCode() {
            return binCode;
        }

        public void setBinCode(String binCode) {
            this.binCode = binCode;
        }

        public String getUnitOfMeasureCode() {
            return unitOfMeasureCode;
        }

        public void setUnitOfMeasureCode(String unitOfMeasureCode) {
            this.unitOfMeasureCode = unitOfMeasureCode;
        }

        public String getItemClassOfSeeds() {
            return itemClassOfSeeds;
        }

        public void setItemClassOfSeeds(String itemClassOfSeeds) {
            this.itemClassOfSeeds = itemClassOfSeeds;
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public String getLotNo() {
            return lotNo;
        }

        public void setLotNo(String lotNo) {
            this.lotNo = lotNo;
        }

        public String getReceiptNo() {
            return receiptNo;
        }

        public void setReceiptNo(String receiptNo) {
            this.receiptNo = receiptNo;
        }

        public String getLandInAcre() {
            return landInAcre;
        }

        public void setLandInAcre(String landInAcre) {
            this.landInAcre = landInAcre;
        }

        public String getNoOfBags() {
            return noOfBags;
        }

        public void setNoOfBags(String noOfBags) {
            this.noOfBags = noOfBags;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
