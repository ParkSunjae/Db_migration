package kr.co.kyobo.vora.model.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalRecommendationUser{
	private int userId;
	private List<Integer> following;
	private List<List<Integer>> delivered;
}
