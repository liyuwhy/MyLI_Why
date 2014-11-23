package com.jju.bean;

public final class FeedReadContract {
	
	public  FeedReadContract(){}
	
	public static final class FeedSearchResult{
		 public static final String TABLE_NAME = "entry";
		 public static final String COLUMN_1_index = "index_num";
		 public static final String COLUMN_2_total = "total";
		 public static final String COLUMN_3_num = "result_num";
		 public static final String COLUMN_4_weburl="web_url";
		 public static final String COLUMN_5_wapurl = "wap_url";
		 public static final String COLUMN_6_bizs = "bizs";
		
		public static final class FeedShop{
			public static final String COLUMN_1_id= "id";
			public static final String COLUMN_2_name ="name";
			public static final String COLUMN_3_addr ="addr";
			public static final String COLUMN_4_tel ="tel";
			public static final String COLUMN_5_cate ="cate";
			public static final String COLUMN_6_rate ="rate";
			public static final String COLUMN_7_cost="cost";
			public static final String COLUMN_8_desc="desc";
			public static final String COLUMN_9_dist = "dist";
			public static final String COLUMN_10_lng ="lng";
			public static final String COLUMN_11_lag ="lat";
			public static final String COLUMN_12_imgUrl ="img_url";
		}
	}
	
	

}
