package com.example.pristineseed.model.item;

import java.util.List;

public class BankAccountModel {
   public boolean  condition;
       public List<Data> data;
       public class Data extends BankAccountModel {
           public String No;
           public String Name;
           public String Post_Code;
           public String Phone_No;
           public String Contact;
           public String Bank_Account_No;
           public String Bank_Acc_Posting_Group;

       }
}
