package treasurehunt.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Accounts {
	
	@JsonProperty
	public List<Account> list;
	
	// constructeur public sans arguments nécéssaire à jackson
	public Accounts() {
		list = new ArrayList<Account>();
	}
	
}
