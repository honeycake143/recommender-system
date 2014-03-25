package com.pccw.suggest.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;


@XmlRootElement
@XStreamAlias("response")
public class RecommandResponse {
	
	
	private String status;
	
	private String count;
	
	private String cki;
	
	@XStreamAlias("itemlist")
	private List<RecommandItem> recommandlist;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCki() {
		return cki;
	}

	public void setCki(String cki) {
		this.cki = cki;
	}

	public List<RecommandItem> getRecommandlist() {
		return recommandlist;
	}

	public void setRecommandlist(List<RecommandItem> recommandlist) {
		this.recommandlist = recommandlist;
	}
	
	
}
