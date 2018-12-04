package kr.co.kyobo.vora.model.api;

import kr.co.kyobo.vora.model.database.Tags;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalRecommendationTag{
	private int userId;
	private List<List<Tags>> delivered;
}
