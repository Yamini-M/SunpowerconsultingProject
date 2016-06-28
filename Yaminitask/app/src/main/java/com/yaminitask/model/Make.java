package com.yaminitask.model;

import java.util.List;

import lombok.Getter;

/**
 * Created by manasal on 28/06/16.
 */
@Getter
public class Make {
    private long id;
    private String name;
    private String niceName;
    private List<Model> models;

    @Getter
    public class Model{
        private String id;
        private String name;
        private String niceName;
        private List<Inventory.Year> years;
    }
}
