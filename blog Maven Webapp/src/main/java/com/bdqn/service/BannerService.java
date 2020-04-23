package com.bdqn.service;

import java.util.List;

import org.springframework.stereotype.Service;


import com.bdqn.entity.BannerWithBLOBs;
import com.bdqn.untity.Result;
import com.bdqn.untity.StatusType;

/**
 * 广告
 * @author RP
 *
 */
public interface BannerService {

	//查询banner
	public Result<List<BannerWithBLOBs>> getBannser(String uid,StatusType statusType);
}
