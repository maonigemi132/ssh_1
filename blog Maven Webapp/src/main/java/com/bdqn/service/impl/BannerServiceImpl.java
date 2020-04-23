package com.bdqn.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdqn.dao.BannerMapper;
import com.bdqn.entity.Banner;
import com.bdqn.entity.BannerWithBLOBs;
import com.bdqn.service.BannerService;
import com.bdqn.untity.Result;
import com.bdqn.untity.ResultType;
import com.bdqn.untity.StatusType;

@Service("bannerService")
public class BannerServiceImpl implements BannerService{

	@Autowired
	private BannerMapper bannerMapper;
	
	
	@Override
	public Result<List<BannerWithBLOBs>> getBannser(String uid,StatusType statusType) {
		List<BannerWithBLOBs> list=bannerMapper.selectListBanner(uid, statusType.getValue());
		if (list!=null&&list.size()>0) {
		  return Result.createSuccess(list);
		}		
		return Result.createFalseResult(ResultType.NODATA,"没有查询到数据");
	}

}
