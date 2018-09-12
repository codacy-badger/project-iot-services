package com.ibm.crl.mv.model;

import java.util.ArrayList;
import java.util.List;

public class FabricReqDescriptor extends Descriptor {
	

	private List<FabricReqDoc> docs = new ArrayList<>();
	

	

	public List<FabricReqDoc> getDocs() {
		return docs;
	}

	public void setDocs(List<FabricReqDoc> docs) {
		this.docs = docs;
	}
	
	
}
