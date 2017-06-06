package com.toda.consultant.util;

import com.toda.consultant.R;
import com.toda.consultant.fragment.ClientListFragment;
import com.toda.consultant.fragment.MineFragment;
import com.toda.consultant.fragment.SecondHouseListFragment;


public class TabDb {
	public static String[] getTabsTxt(){
		String[] tabs={"消息","二手房源","客户","我的"};
		return tabs;
	}
	public static int[] getTabsImg(){
		int[] ids={R.mipmap.ic_message_nor,R.mipmap.ic_house_nor,R.mipmap.ic_client_nor,R.mipmap.ic_mine_nor};
		return ids;
	}
	public static int[] getTabsImgLight(){
		int[] ids={R.mipmap.ic_message_selected,R.mipmap.ic_house_selected,R.mipmap.ic_client_selected, R.mipmap.ic_mine_selected};
		return ids;
	}
	public static Class[] getFragments(){
		Class[] clz={io.rong.imkit.fragment.ConversationListFragment.class,SecondHouseListFragment.class,ClientListFragment.class,MineFragment.class};
		return clz;
	}
}
