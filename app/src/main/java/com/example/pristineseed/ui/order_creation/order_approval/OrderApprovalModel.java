package com.example.pristineseed.ui.order_creation.order_approval;

import java.util.List;

public class OrderApprovalModel {
    public String order_no;
    public String order_status;
    public String sum_of_qty;
    public String updated_on;
    public List<OrderLineModel> orderline;

    public class OrderLineModel {
        public String order_no;
        public String item_name;
        public String image_url;
        public String qty;
    }
}
