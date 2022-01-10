package com.example.pristineseed.model.item;

import java.util.List;

public class BankMaserModel {

       public boolean condition;
        public List<Data> data;

       public class Data {
                public String Customer_No;
                public String Code;
                public String Name;
                public String Bank_Account_No;
                public String SWIFT_Code;
                public String IBAN;

       }
}
