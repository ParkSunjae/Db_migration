package kr.co.kyobo.vora.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.SortedSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalRecommendationPost{
	private int userId;
	private SortedSet<Integer> delivered;
}
