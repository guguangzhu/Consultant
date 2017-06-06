package com.toda.consultant.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.toda.consultant.R;
import com.toda.consultant.statics.ErrorTips;
import com.toda.consultant.view.pullview.PullToRefreshBase;
import com.toda.consultant.view.pullview.PullToRefreshBase.Mode;
import com.toda.consultant.view.pullview.PullToRefreshBase.OnRefreshListener2;
import com.toda.consultant.view.pullview.PullToRefreshListView;

import java.lang.reflect.Field;
import java.util.List;

public class CommonListView extends LinearLayout implements OnClickListener,
		OnRefreshListener2<ListView> {
	private Context context;
	public PullToRefreshListView listView;
	private NormalEmptyView emptyView;
	private ProgressDialog netDialog;

	private BaseAdapter adapter;
	private OnRefreshListener listener;

	private List list;

	private int currentPage = 1;
	private int pageSize = 8;
	protected boolean isRefresh;
	/** 刷新，或者点击emptyview时是否现实网络等待框，否则显示内置网络加载框 **/
	private boolean showDialog;;
	private boolean canLoadMore=true;;

	public CommonListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init() {
		LayoutInflater.from(context).inflate(R.layout.solarbao_listview_layout,
				this);
		listView = (PullToRefreshListView) findViewById(R.id.lv_refresh);
		emptyView = (NormalEmptyView) findViewById(R.id.empty_view);
		listView.setOnRefreshListener(this);
		emptyView.setOnClickListener(this);

		setShowModel(false);
	}

	/***
	 * 刷新并现实网络显示框
	 */
	public void refresh() {
		refresh(true);
	}

	/*** 
	 * 刷新
	 * @param isshow 是否显示dialog
	 */
	public void refresh(boolean isshow) {
//		if(listView.isRefreshing()){
//			return;
//		}
		if (isshow) {
			showDialog(context);
		}
//		else if(emptyView.isShown()){
//			emptyView.setEmptyType(emptyView.EMPTY_TYPE_LOADING);
//		}
		onPullDownToRefresh(null);
	}

	/***
	 * 带下拉动作的刷新
	 */
	public void refreshUpAnim() {
//		listView.getRefreshableView().setSelection(0);
		try {
			Field field=PullToRefreshBase.class.getDeclaredField("mCurrentMode");
			field.setAccessible(true);
			field.set(listView, Mode.PULL_FROM_START);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		listView.setRefreshing();
	}

	public void onRefreshComplete() {
		listView.onRefreshComplete();
		dismissDialog();
	}

	/***
	 * 更新列表，如果传入为空会有相应为空提示或显示
	 * @param list
	 */
	public void notifyDataSetChanged(List list) {
		if (this.list == null) {
			return;
		}
		if (isRefresh) {
			currentPage = 1;
			this.list.clear();
		}
		if (list != null && list.size() != 0) {
			this.list.addAll(list);
		}
		
		if (adapter != null) {
			adapter.notifyDataSetChanged();
			if(isRefresh){
				this.getListView().setSelection(0);
			}
		}

		if (this.list.size() != 0) {
			listView.setVisibility(View.VISIBLE);
			emptyView.setVisibility(View.GONE);
			//如果整体数据不为空，传入为空，则提示
			if (list == null || list.size() == 0) {
				if(footerView==null){
					Toast.makeText(context,ErrorTips.NO_MORE_DATA,Toast.LENGTH_SHORT).show();
				}
			} else {
				currentPage++;
			}
		} else {
			//如果整体数据为空，则显示空提示
			if(footerView==null){
				setEmptyType(NormalEmptyView.EMPTY_TYPE_NOCONTENT);
			}
		}

		//更改下拉状态
		if (pageSize == -1 || this.list.size() < pageSize) {
			listView.setMode(Mode.PULL_FROM_START);
		} else if(canLoadMore){
			listView.setMode(Mode.BOTH);
		}else{
			listView.setMode(Mode.PULL_FROM_START);
		}
		if(footerView!=null&&(list==null||list.size()<pageSize)){
			listView.setVisibility(View.VISIBLE);
			emptyView.setVisibility(View.GONE);
			showFooter();
			listView.setMode(Mode.PULL_FROM_START);
		}else{
			hideFooter();
		}
	}

	public void notifyDataSetChanged() {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

	/**
	 * 显示网络进度提示
	 * 
	 * @param context
	 *            上下文环境
	 */
	public void showDialog(Context context) {
		try {
			if (netDialog == null) {
				netDialog = new ProgressDialog(context);
				netDialog.setMessage("正在获得数据...");
				netDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				netDialog.setCancelable(true);
				netDialog.setCanceledOnTouchOutside(false);
				netDialog.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
					}
				});
			}
			if (context instanceof Activity
					&& !((Activity) context).isFinishing()) {
				netDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 将进度提示框消失
	 */
	public void dismissDialog() {
		try {
			if (netDialog != null) {
				netDialog.dismiss();
			}
		} catch (Exception e) {

		}
	}

	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.empty_view){
			if (!showDialog) {
				emptyView.setEmptyType(emptyView.EMPTY_TYPE_LOADING);
				refresh(false);
			} else {
				refresh();
			}
		}
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (listener != null) {
			//联网判断
//			if (!UtilTools.isOpenNetwork(context)) {
//				if (!listView.isShown()) {
//					emptyView
//							.setEmptyType(NormalEmptyView.EMPTY_TYPE_NONET);
//				}
////				UtilTools.createDialogToSettingNetWork(context, null);
//				this.post(new Runnable() {
//
//					@Override
//					public void run() {
//						onRefreshComplete();
//					}
//				});
//				return;
//			}
			isRefresh = true;
			listener.onPullDownToRefresh(1);
		}
	}

	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		if (listener != null) {
//			if (!UtilTools.isOpenNetwork(context)) {
////				UtilTools.createDialogToSettingNetWork(context, null);
//				this.post(new Runnable() {
//
//					@Override
//					public void run() {
//						onRefreshComplete();
//					}
//				});
//				return;
//			}
			isRefresh = false;
			listener.onPullDownToRefresh(currentPage);
		}
	}

	public void setEmptyType(int type) {
		listView.setVisibility(View.GONE);
		emptyView.setVisibility(View.VISIBLE);
		emptyView.setEmptyType(type);
	}

	/***
	 * 设置网络框显示方式
	 * @param showDialog true为显示dialog网络等待框 ，false显示内置
	 */
	public void setShowModel(boolean showDialog) {
		this.showDialog = showDialog;
		if (!showDialog) {
			setEmptyType(NormalEmptyView.EMPTY_TYPE_LOADING);
		}else{
			listView.setVisibility(View.VISIBLE);
			emptyView.setVisibility(View.GONE);
		}
	}

	public void setListener(OnRefreshListener listener) {
		this.listener = listener;
	}

	private View footerView;
	public void addFooterView(View view){
		footerView=view;
		LinearLayout layout=new LinearLayout(getContext());
		layout.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		layout.addView(footerView);
		listView.getRefreshableView().addFooterView(layout);
	}

	public void hideFooter(){
		if(footerView==null){
			return;
		}
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		params.height=0;
		footerView.setLayoutParams(params);
	}
	public void showFooter(){
		if(footerView==null){
			return;
		}
		LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)footerView.getLayoutParams();
		params.height= LayoutParams.WRAP_CONTENT;
		footerView.setLayoutParams(params);
	}

	public void setAdapter(BaseAdapter adapter) {
		this.adapter = adapter;
		listView.setAdapter(adapter);
		hideFooter();
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setList(List list) {
		this.list = list;
	}
	
	public List getList(){
		return list;
	}

	public interface OnRefreshListener {
		public void onPullDownToRefresh(int currentPage);

		public void onPullUpToRefresh(int currentPage);
	}

	public PullToRefreshListView getRefreshListView() {
		return listView;
	}
	
	public ListView getListView(){
		if(listView!=null){
			return listView.getRefreshableView();
		}
		return null;
	}
	
	/***
	 * 当前操纵是不是刷新列表
	 * @return
	 */
	public boolean isRefresh(){
		return isRefresh;
	}

	public boolean isCanLoadMore() {
		return canLoadMore;
	}

	/***
	 * 设置是否可以上拉加载更多
	 * @param canLoadMore
	 */
	public void setCanLoadMore(boolean canLoadMore) {
		this.canLoadMore = canLoadMore;
	}
	
	public NormalEmptyView getEmptyView(){
		return emptyView;
	}
}
