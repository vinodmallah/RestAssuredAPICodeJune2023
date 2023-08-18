package pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GoREST_AddNewUser_POJO_Lombok {	
	
	private Integer id;
	private String name;
	private String email;
	private String gender;
	private String status;
	
	public GoREST_AddNewUser_POJO_Lombok(String name, String email, String gender, String status) {
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.status = status;
	}		
}
