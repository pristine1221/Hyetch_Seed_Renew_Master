package com.example.pristineseed.model.item;

import com.example.pristineseed.model.seed_dispatch_note.SeedDispatchHeaderModel;

import java.util.ArrayList;
import java.util.List;

public class UnitOfMeasureModel {
    public boolean condition;
    public List<UnitOfMeasureModelList> data;

    public class UnitOfMeasureModelList{
        public String Code;
        public String Description;
    }
}
