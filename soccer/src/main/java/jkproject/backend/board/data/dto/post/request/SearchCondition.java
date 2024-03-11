package jkproject.backend.board.data.dto.post.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchCondition {
	private String type;
	private String keyword;
}
