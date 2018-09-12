package com.ibm.crl.mv.model;

import java.util.ArrayList;
import java.util.List;


public class BodyDescriptor extends Descriptor{
	
	
	private List<DocDescriptor> docs = new ArrayList<>();
	
	

	public List<DocDescriptor> getDocs() {
		return docs;
	}

	public void setDocs(List<DocDescriptor> docs) {
		this.docs = docs;
	}




	

	
	
	
}
