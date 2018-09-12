package com.ibm.crl.mv.db.model;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class Mes {
	


	public  Map<String, Object> describe() {

		final Map<String, Object> description = new HashMap<String, Object>();

		Field[] fields = this.getClass().getDeclaredFields();

		for (Field field : fields) {

			try {
				
				String name = field.getName();

				field.setAccessible(true);

				Object val = field.get(this);

				if (val == null) {
					continue;
				}

				description.put(name, val);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return description;

	}
	
	
}
