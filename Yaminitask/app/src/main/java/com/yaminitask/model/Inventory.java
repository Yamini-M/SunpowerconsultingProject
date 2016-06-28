package com.yaminitask.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * Created by manasal on 28/06/16.
 */
@Getter
public class Inventory {

    private String id;
    private String type;
    private String vin;
    private String stockNumber;
    private int mileage;
    private Make make;
    private Model model;
    private Year year;
    private Style style;
    private Media media;
    private Prices prices;
    private Dealer dealer;


    @Getter
    public class Address {
        private String street;
        private String city;
        private String stateCode;
        private String stateName;
        private String county;
        private String country;
        private Double latitude;
        private Double longitude;
        private String zipcode;
    }

    @Getter
    public class Dealer {
        private String dealerId;
        private String name;
        private Address address;
        private String franchiseId;
        private Boolean premier;
    }

    @Getter
    public class Make {
        private String name;
        private String niceName;
    }

    @Getter
    public class Media {
        private Photos photos;
    }

    @Getter
    public class Model {
        private String name;
        private String niceName;
    }

    @Getter
    public class Photos {
        private Thumbnails thumbnails;
    }

    @Getter
    public class Prices {
        private int msrp;
        private int tmv;
        private int invoice;
    }

    @Getter
    public class Style {
        private int id;
        private String name;
        private Submodel submodel;
        private String trim;
    }

    @Getter
    public class Submodel {
        private String body;
        private String modelName;
        private String niceName;
    }

    @Getter
    public class Thumbnails {
        private int count;
        private List<Link> links = new ArrayList<Link>();
    }

    @Getter
    public class Year {
        private int id;
        private int year;
    }
}
