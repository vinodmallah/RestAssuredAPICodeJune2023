package pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FakerProducts_POJO_Lombok {
	private int id;
	private String title;
	private double price;
	private String description;
	private String category;
	private String image;
	public Rating rating;
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Rating {
		private double rate;
		private int count;
	}

}
