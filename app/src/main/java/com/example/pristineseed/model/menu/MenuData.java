package com.example.pristineseed.model.menu;

import java.util.List;

public class MenuData {
         public String id;
         public String title;
         public String translate;
         public String type;
    public List<SubMenu> children;

    public class SubMenu {
                public String id;
                public String title;
                public String type;
                public String url;
    }
}
