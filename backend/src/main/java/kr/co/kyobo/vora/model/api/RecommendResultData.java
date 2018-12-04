package kr.co.kyobo.vora.model.api;

import kr.co.kyobo.vora.model.database.Feeds;
import kr.co.kyobo.vora.model.database.Member;
import kr.co.kyobo.vora.model.database.Tags;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendResultData {
	private int error;
	private List<Tags> tags;
	private List<Integer> users;
	private List<Integer> posts;
}
