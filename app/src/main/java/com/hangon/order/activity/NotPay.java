package com.hangon.order.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.fd.ourapplication.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hangon.common.Constants;
import com.hangon.common.VolleyInterface;
import com.hangon.common.VolleyRequest;
import com.hangon.order.util.GasOrderAdapter;
import com.hangon.order.util.GetOrderData;
import com.hangon.order.util.OnItemclick;
import com.hangon.order.util.OrderData;

public class NotPay extends Fragment {
	/**
	 * 获取ListView
	 */
	ListView mOrderNotPayList;
	/**
	 * 接收数据来自 GetOrderData
	 */
	List mOrderList;
	/**
	 * 获取数据类
	 */
	GetOrderData mGetOrderData;
	GasOrderAdapter adapter ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View notpay=inflater.inflate(R.layout.appointment_order, container,false);
		mOrderNotPayList= (ListView) notpay.findViewById(R.id.appointment_order_list);
        getData();
		return notpay;
	}
//	//初始化适配器
//	private void initAdapter(List mOrderList){
//		String from[]={"OrderGasStationName","OrderGasState","OrderGasLitre",
//				"OrderGasSumPrice","OrderGasTime"};
//		int to[]={R.id.list_order_gasname,R.id.list_gasorder_status,
//				R.id.list_gastype,R.id.list_gassumprice,R.id.list_ordertime
//		};
//		//adapter=new GasOrderAdapter(getActivity(),mOrderList, R.layout.orderlist, from, to);
//		mOrderNotPayList.setAdapter(adapter);
//	}
	//装载数据
	public void getData() {
		String url = Constants.GET_WZF_ORDER_INFOS_URL;
		VolleyRequest.RequestGet(getActivity(), url, "getData", new VolleyInterface(getActivity(), VolleyInterface.mListener, VolleyInterface.mErrorListener) {
			@Override
			public void onMySuccess(String result) {
				Gson gson = new Gson();
				Log.e("aaaa", result);

				List<OrderData> list = gson.fromJson(result, new TypeToken<List<OrderData>>() {
				}.getType());
				mOrderNotPayList.setOnItemClickListener(new OnItemclick(getActivity(), list));
				//initAdapter(setData(list));
				adapter=new GasOrderAdapter(getActivity(),list, R.layout.orderlist);
				mOrderNotPayList.setAdapter(adapter);
			//	Toast.makeText(getActivity(), list.get(0).getCusName() + list.get(0).getCusPhoneNum(), Toast.LENGTH_LONG).show();
			}

			@Override
			public void onMyError(VolleyError error) {

			}
		});
	}
//	//装载数据
//	public List setData(List<OrderData> list){
//		List mList=new ArrayList();
//		for (int i = 0; i <list.size(); i++) {
//			Map map = new HashMap();
//			map.put("OrderGasStationName", list.get(i).getGasStationName());
//			map.put("OrderGasStationAddress", list.get(i).getGasStationAddress());
//			//	map.put("OrderGasStationType",getOrderData.get(i).getGasStationType());
//			map.put("OrderGasLitre", list.get(i).getGasLitre());
//			map.put("OrderGasSinglePrice", list.get(i).getGasSinglePrice());
//			map.put("OrderGasSumPrice", list.get(i).getGasSumPrice());
//			map.put("OrderGasType", list.get(i).getGasType());
//			map.put("OrderGasTime", list.get(i).getStrTime());
//			map.put("OrderGasState", "未支付");
//			mList.add(map);
//		}
//		return mList;
//	}
@Override
public void onResume() {
	// TODO 自动生成的方法存根
	super.onResume();
	Log.d("aaaaaaaaaaa", "aaaaaaaaaaaaaa");

	getData();

}
	@Override
	public void onStart() {
		super.onStart();
		getData();
	}

	@Override
	public void onStop() {
		super.onStop();
		getData();
	}

	@Override
	public void onPause() {
		super.onPause();
		getData();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		onDestroy();
	}

}
