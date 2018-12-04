package kr.co.kyobo.vora.repository.mysql;

import kr.co.kyobo.vora.model.database.mysql.ActionTagsCultureExhibition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActionTagCultureExhibitionRepository extends JpaRepository<ActionTagsCultureExhibition, Integer> {

}
